package com.TAS.takeasup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.TAS.takeasup.Database.Database;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Model.Order;
import com.TAS.takeasup.Model.Request;
import com.TAS.takeasup.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    RecyclerView recyclerViewCart;
    SwipeRefreshLayout refresh;
    public TextView totalText,clearCartText;
    Button placeOrderButton;
    DatabaseReference requests;
    Database database;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        refresh = view.findViewById(R.id.swipeLayoutCart);
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setHasFixedSize(true);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        totalText = view.findViewById(R.id.totalText);
        clearCartText = view.findViewById(R.id.clearCartText);
        placeOrderButton = view.findViewById(R.id.placeOrderButton);
        database = new Database(getActivity());

        requests = FirebaseDatabase.getInstance().getReference("Requests");

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadListFood();
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.size() > 0) {
                    showAlertDialog();
                } else {
                    Toast.makeText(getActivity(), "Your Cart is Empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadListFood();
        clearCart();

        return view;
    }

    private void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("One Last Step!");
        dialog.setMessage("Enter your Table Number");
        final EditText editTable = new EditText(getActivity());
        editTable.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editTable.setLayoutParams(lp);
        dialog.setView(editTable);  //add editText to alertDialog
        dialog.setIcon(R.drawable.ic_warning_black);
        dialog.setCancelable(false);

        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(editTable.getText().toString(), totalText.getText().toString(), cart);

                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                database.cleanCart();
                Toast.makeText(getActivity(), "Order Placed!\tEnjoy Your Meal", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    private void loadListFood() {
        refresh.setRefreshing(false);

        cart = new Database(getActivity()).getCart();
        adapter = new CartAdapter(cart, getActivity());
        recyclerViewCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        int totalOrder = 0;
        for (Order order : cart) {
            Locale locale = new Locale("en", "IN");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            String dishPrice = order.getDishPrice();
            String dishQty = order.getDishQuantity();
            int total = Integer.parseInt(dishPrice) * Integer.parseInt(dishQty);
            totalOrder += total;
            totalText.setText(fmt.format(totalOrder));
        }
    }

    private void clearCart() {
        clearCartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder clear = new AlertDialog.Builder(getActivity());
                clear.setCancelable(false);
                clear.setTitle("Clear Cart !!");
                clear.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.cleanCart();
                        Toast.makeText(getActivity(), "Your Cart is cleared", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                clear.show();
            }
        });
    }
}
