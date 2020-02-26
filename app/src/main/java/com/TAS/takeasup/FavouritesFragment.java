package com.TAS.takeasup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.TAS.takeasup.Database.FavouritesDatabase;
import com.TAS.takeasup.Model.FavouritesRestaurantsList;
import com.TAS.takeasup.ViewHolder.FavouritesAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private FavouritesAdapter adapter;
    private FavouritesDatabase favouritesDatabase;
    private List<FavouritesRestaurantsList> list;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerViewFavourites;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        refreshLayout = view.findViewById(R.id.swipeLayoutFavourites);
        recyclerViewFavourites = view.findViewById(R.id.recyclerViewFavourites);
        recyclerViewFavourites.setHasFixedSize(true);
        recyclerViewFavourites.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });

        load();

        return view;
    }

    public void load() {

        refreshLayout.setRefreshing(false);

        favouritesDatabase = new FavouritesDatabase(getActivity());
        list = favouritesDatabase.showFavourites();

        adapter = new FavouritesAdapter(list, getActivity());
        adapter.setOnCardClickedListener(new FavouritesAdapter.onCardClickListener() {
            @Override
            public void onCardClicked(int pos, String restName) {
                Bundle bundle = new Bundle();
                bundle.putString("RestaurantName",restName);

                RestaurantHomes restHome = new RestaurantHomes();
                restHome.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.mainPage,restHome).addToBackStack("").commit();
            }
        });
        recyclerViewFavourites.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
