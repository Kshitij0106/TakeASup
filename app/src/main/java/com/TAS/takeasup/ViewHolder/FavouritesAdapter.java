package com.TAS.takeasup.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TAS.takeasup.Database.FavouritesDatabase;
import com.TAS.takeasup.Model.FavouritesRestaurantsList;
import com.TAS.takeasup.R;

import java.util.List;

class FavouritesViewHolder extends RecyclerView.ViewHolder{

    public TextView favName,favType,favCost;
    public ImageView removeFav;

    public FavouritesViewHolder(@NonNull View itemView) {
        super(itemView);
        favName = itemView.findViewById(R.id.nameInFav);
        favType = itemView.findViewById(R.id.typeInFav);
        favCost = itemView.findViewById(R.id.costInFav);
        removeFav = itemView.findViewById(R.id.removeFromFav);
    }
}
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesViewHolder>{

    private List<FavouritesRestaurantsList> list;
    private Context context;

    public FavouritesAdapter(List<FavouritesRestaurantsList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, final int position) {
        holder.favName.setText(list.get(position).getRName());
        holder.favType.setText(list.get(position).getRType());
        holder.favCost.setText(list.get(position).getRCost());

        holder.removeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = list.get(position).getRName();
                FavouritesDatabase fdb = new FavouritesDatabase(context);
                fdb.removeFromFavourites(name);
                Toast.makeText(context, "Removed From Favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_layout,parent,false);
        FavouritesViewHolder fvh = new FavouritesViewHolder(view);
        return fvh;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
