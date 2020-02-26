package com.TAS.takeasup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Database.Database;
import com.TAS.takeasup.Model.Categories;
import com.TAS.takeasup.Model.MenuItems;
import com.TAS.takeasup.Model.Order;
import com.TAS.takeasup.ViewHolder.CategoriesViewHolder;
import com.TAS.takeasup.ViewHolder.MenuAdapter;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private SearchView searchViewMenu;

    private TextView showCartButton;
    private List<Order> dishes;

    private RecyclerView categoryRecyclerView;
    private DatabaseReference categoryReference;
    private FirebaseRecyclerOptions<Categories> categoryOptions;
    private FirebaseRecyclerAdapter<Categories, CategoriesViewHolder> categoryAdapter;
    private ArrayList<MenuItems> dishesList;

    private Database database;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);
        searchViewMenu = view.findViewById(R.id.searchViewMenu);
        searchViewMenu.setBackgroundResource(R.drawable.searchview_rounded);
        showCartButton = view.findViewById(R.id.showCartButton);
        dishes = new ArrayList<>();
        database = new Database(getActivity());

        refresh = view.findViewById(R.id.swipeLayoutMenu);
        categoryRecyclerView = view.findViewById(R.id.recyclerViewMenu);
        categoryRecyclerView.setHasFixedSize(true);
        dishesList = new ArrayList<>();

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });

        load();
        return view;
    }

    public void load() {

        Bundle ReceivedBundle = this.getArguments();
        final String RestaurantName = ReceivedBundle.getString("RestaurantName");

        if (Common.isConnectedToInternet(getActivity())) {
            refresh.setRefreshing(false);

            categoryReference = FirebaseDatabase.getInstance().getReference(RestaurantName).child("Menu");
            categoryOptions = new FirebaseRecyclerOptions.Builder<Categories>().setQuery(categoryReference, Categories.class).build();
            categoryAdapter = new FirebaseRecyclerAdapter<Categories, CategoriesViewHolder>(categoryOptions) {
                @Override
                protected void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int i, @NonNull Categories categories) {
                    categoriesViewHolder.categoryName.setText(categories.getDishCategory());
                    dishesList = categories.getDishes();

                    MenuAdapter adapter = new MenuAdapter(getActivity(), dishesList);

                    categoriesViewHolder.categoryRecylerView.setHasFixedSize(true);
                    categoriesViewHolder.categoryRecylerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    categoriesViewHolder.categoryRecylerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    adapter.setOnCardClickedListener(new MenuAdapter.onCardClickListener() {
                        @Override
                        public void onCardClicked(int position, String dishName, String dishPrice) {
                            String DishName = dishName;
                            String DishPrice = dishPrice;

                            Dialog qtyDialog = new Dialog(getActivity());
                            openQtyDialog(DishName, DishPrice, qtyDialog, getActivity(),RestaurantName);
                            qtyDialog.show();
                        }
                    });
                }

                @NonNull
                @Override
                public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_dish_category, parent, false);
                    CategoriesViewHolder cvh = new CategoriesViewHolder(view);
                    return cvh;
                }
            };
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            categoryRecyclerView.setAdapter(categoryAdapter);
            categoryAdapter.startListening();
            categoryAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void openQtyDialog(final String dName, final String dPrice, final Dialog qtyDialog, final Context context, final String RestaurantName) {
        final Database database;
        final TextView dishSelected;
        ImageView closeDialog;
        final ElegantNumberButton qtySelected;
        EditText comments;
        final Button addToCartButton;

        qtyDialog.setContentView(R.layout.confirm_dish_layout);
        dishSelected = qtyDialog.findViewById(R.id.dishSelected);
        closeDialog = qtyDialog.findViewById(R.id.closeDialog);
        qtySelected = qtyDialog.findViewById(R.id.qtySelected);
        comments = qtyDialog.findViewById(R.id.comments);
        addToCartButton = qtyDialog.findViewById(R.id.addToCartButton);
        database = new Database(context);

        dishSelected.setText(dName);

        qtySelected.setRange(1, 10);
        final String[] dQty = {"1"};
        qtySelected.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                dQty[0] = String.valueOf(newValue);
            }
        });

        if (!comments.getText().toString().isEmpty()) {
            String additionalCommnets = comments.getText().toString();
        }

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtyDialog.dismiss();
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=1;
                boolean res = database.addToCart(new Order(dName, dPrice, dQty[0], i));
                if (res == true) {
                    showCartButton.setVisibility(View.VISIBLE);
                    showCartButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle restNameBundle = new Bundle();
                            restNameBundle.putString("restNameCart",RestaurantName);
                            CartFragment cartFragment = new CartFragment();
                            cartFragment.setArguments(restNameBundle);
                            getFragmentManager().beginTransaction().replace(R.id.mainPage,cartFragment).addToBackStack("").commit();
                        }
                    });
                    Toast.makeText(getActivity(), "Added To Cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Selected Dish is already in Cart", Toast.LENGTH_SHORT).show();
                }
                qtyDialog.dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (categoryAdapter != null)
            categoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (categoryAdapter != null)
            categoryAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (categoryAdapter != null)
            categoryAdapter.startListening();
    }

}


