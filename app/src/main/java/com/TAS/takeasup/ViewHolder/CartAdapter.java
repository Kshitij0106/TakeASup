package com.TAS.takeasup.ViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.CartFragment;
import com.TAS.takeasup.Database.Database;
import com.TAS.takeasup.Model.Order;
import com.TAS.takeasup.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView nameInCart, priceInCart;
    public ElegantNumberButton qtyInCart;
    public ImageView removeItem;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        nameInCart = itemView.findViewById(R.id.nameInCart);
        priceInCart = itemView.findViewById(R.id.priceInCart);
        qtyInCart = itemView.findViewById(R.id.qtyInCart);
        removeItem = itemView.findViewById(R.id.removeFromCart);
    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> dishes = new ArrayList<>();
    List<Integer> total = new ArrayList<>();
    private Context context;
    CartFragment cart;

    public CartAdapter(List<Order> dishes, Context context) {
        this.dishes = dishes;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        CartViewHolder cvh = new CartViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        Locale locale = new Locale("en", "IN");
        final NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        final String dishPrice = dishes.get(position).getDishPrice();
        String dishQty = dishes.get(position).getDishQuantity();
        holder.nameInCart.setText(dishes.get(position).getDishName());
        holder.qtyInCart.setNumber(dishes.get(position).getDishQuantity());
        int dishTotal = Integer.parseInt(dishPrice)*Integer.parseInt(dishQty);
        holder.priceInCart.setText(fmt.format(dishTotal));
        holder.qtyInCart.setRange(1,100);
        holder.qtyInCart.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = dishes.get(position);
                String qty = holder.qtyInCart.getNumber();
                int priceOfDish = Integer.parseInt(dishPrice)*Integer.parseInt(qty);
                order.setDishQuantity(qty);
                Database db = new Database(context);
                db.updateCart(order);
                holder.priceInCart.setText(fmt.format(priceOfDish));
            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = dishes.get(position).getDishName();
                AlertDialog.Builder remove = new AlertDialog.Builder(context);
                remove.setMessage("Are you sure you want to remove " + name + " from cart");
                remove.setCancelable(false);
                remove.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Database database = new Database(context);
                        database.deleteFromCart(name);
                        Toast.makeText(context, name + " Removed From Cart", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                remove.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

}