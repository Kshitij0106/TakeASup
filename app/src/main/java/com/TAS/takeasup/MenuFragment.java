package com.TAS.takeasup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Model.MenuItems;
import com.TAS.takeasup.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class MenuFragment extends Fragment {
    private SwipeRefreshLayout refresh;
    private RecyclerView recyclerView;
    private SearchView searchViewMenu;
    private DatabaseReference reference;
    private DatabaseReference mreference;
    private FirebaseRecyclerOptions<MenuItems> options;
    private FirebaseRecyclerAdapter<MenuItems, MenuViewHolder> adapter;
    private String RestaurantName;
    MenuViewHolder mvh;
    int n = 1;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);
        searchViewMenu = view.findViewById(R.id.searchViewMenu);
        searchViewMenu.setBackgroundResource(R.drawable.searchview_rounded);

        refresh = view.findViewById(R.id.swipeLayoutMenu);
        recyclerView = view.findViewById(R.id.recyclerViewMenu);
        recyclerView.setHasFixedSize(true);

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
        RestaurantName = ReceivedBundle.getString("RestaurantName");

        if (Common.isConnectedToInternet(getActivity())) {

            refresh.setRefreshing(false);

//            String no = String.valueOf(n);
            mreference = FirebaseDatabase.getInstance().getReference(RestaurantName).child("Menu");
            mreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (int no =1; no <= dataSnapshot.getChildrenCount(); no++) {
                        reference = mreference.child(String.valueOf(no));
                        options = new FirebaseRecyclerOptions.Builder<MenuItems>().setQuery(reference, MenuItems.class).build();
                        adapter = new FirebaseRecyclerAdapter<MenuItems, MenuViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, final int i, @NonNull final MenuItems menuItems) {

//                    if (!menuItems.getCategory().equals(" ")) {
//                        menuViewHolder.menuCategory.setVisibility(View.VISIBLE);
//                        menuViewHolder.menuCategory.setText(menuItems.getCategory());
//                    }
                                menuViewHolder.menuName.setText(menuItems.getName());
                                Locale locale = new Locale("en", "IN");
                                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                                int price = Integer.parseInt(menuItems.getPrice());
                                menuViewHolder.menuPrice.setText(fmt.format(price));
                                menuViewHolder.menuType.setText(menuItems.getType());

                                menuViewHolder.addButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String DishName = menuItems.getName();
                                        String DishPrice = menuItems.getPrice();

                                        Bundle cnfrmBundle = new Bundle();
                                        cnfrmBundle.putString("DishName", DishName);
                                        cnfrmBundle.putString("DishPrice", DishPrice);

                                        ConfirmationFragment cnfrmfrag = new ConfirmationFragment();
                                        cnfrmfrag.setArguments(cnfrmBundle);
                                        getFragmentManager().beginTransaction().addToBackStack("").replace(R.id.mainPage, cnfrmfrag).commit();

                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout, parent, false);
                                MenuViewHolder vh = new MenuViewHolder(v);
                                return vh;
                            }

                        };
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter.startListening();
                        recyclerView.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

}


// reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (long i = 1; i >= dataSnapshot.getChildrenCount(); i++) {
//                        mreference = FirebaseDatabase.getInstance().getReference(RestaurantName).child("Menu").child(String.valueOf(i));
//                        mreference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                    String name = data.child("name").getValue().toString();
//                                    String type = data.child("type").getValue().toString();
//                                    mvh.menuName.setText(name);
//                                    mvh.menuType.setText(type);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//                                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });




