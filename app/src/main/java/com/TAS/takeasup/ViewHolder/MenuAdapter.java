package com.TAS.takeasup.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.Model.MenuItems;
import com.TAS.takeasup.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context context;
    private ArrayList<MenuItems> dishList;
    private onCardClickListener adapterListener;

    public interface onCardClickListener{
        void onCardClicked(int position, String dishName, String dishPrice);
    }

    public void setOnCardClickedListener(onCardClickListener onCardClickedListener){
        adapterListener = onCardClickedListener;
    }

    public MenuAdapter(Context context, ArrayList<MenuItems> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_layout, parent, false);
        MenuViewHolder mvh = new MenuViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.nameText.setText(dishList.get(position).getDishName());

        Locale locale = new Locale("en", "IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = Integer.parseInt(dishList.get(position).getDishPrice());
        holder.priceText.setText(fmt.format(price));
        holder.typeText.setText(dishList.get(position).getDishType());
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView priceText, nameText, typeText;
        public ImageView addDish;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            priceText = itemView.findViewById(R.id.priceText);
            nameText = itemView.findViewById(R.id.nameText);
            typeText = itemView.findViewById(R.id.typeText);
            addDish = itemView.findViewById(R.id.addDish);

            addDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adapterListener != null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            adapterListener.onCardClicked(pos,nameText.getText().toString(),priceText.getText().toString());
                        }
                    }
                }
            });
        }
    }
}
