package com.TAS.takeasup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Database.FavouritesDatabase;
import com.TAS.takeasup.Model.RestaurantList;
import com.TAS.takeasup.ViewHolder.RestaurantsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomePageFragment extends Fragment {

    private RecyclerView recyclerViewHome;
    public SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<RestaurantList> optionHome;
    private FirebaseRecyclerAdapter<RestaurantList, RestaurantsViewHolder> adapterHome;
    private SearchView searchView;
    private FirebaseRecyclerOptions<RestaurantList> searchOptions;
    private FirebaseRecyclerAdapter<RestaurantList, RestaurantsViewHolder> searchAdapter;
    private FavouritesDatabase favDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_home_page2, container, false);

        searchView = view.findViewById(R.id.searchView);
        searchView.setBackgroundResource(R.drawable.searchview_rounded);
        recyclerViewHome = view.findViewById(R.id.recyclerViewHome);
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        recyclerViewHome.setHasFixedSize(true);
        favDatabase = new FavouritesDatabase(getActivity());

        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurant's List");

        bottomNavigationView = view.findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();

        return view;
    }

    public void load() {
        if (Common.isConnectedToInternet(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);

            optionHome = new FirebaseRecyclerOptions.Builder<RestaurantList>().setQuery(databaseReference, RestaurantList.class).build();
            adapterHome = new FirebaseRecyclerAdapter<RestaurantList, RestaurantsViewHolder>(optionHome) {
                @Override
                protected void onBindViewHolder(@NonNull RestaurantsViewHolder restaurantsViewHolder, int i, @NonNull final RestaurantList restaurantList) {
                    Picasso.get().load(restaurantList.getRestPic()).centerCrop().fit().into(restaurantsViewHolder.image);
                    restaurantsViewHolder.name.setText(restaurantList.getRestName());
                    restaurantsViewHolder.cuisine.setText(restaurantList.getRestType());
                    restaurantsViewHolder.cost.setText(restaurantList.getRestCostForTwo());

                    restaurantsViewHolder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String rName = restaurantList.getRestName();
                            Bundle rNameBundle = new Bundle();
                            rNameBundle.putString("RestaurantName", rName);
                            RestaurantHomes restaurantHomes = new RestaurantHomes();
                            restaurantHomes.setArguments(rNameBundle);
                            getFragmentManager().beginTransaction().replace(R.id.mainPage, restaurantHomes).addToBackStack("").commit();
                        }
                    });
                    restaurantsViewHolder.favButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            favDatabase.addToFavourites(restaurantList.getRestName(), restaurantList.getRestType(), restaurantList.getRestCostForTwo());
                            Toast.makeText(getActivity(), "Added to Your Favourites", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @NonNull
                @Override
                public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_layout, parent, false);
                    RestaurantsViewHolder rvh = new RestaurantsViewHolder(view1);
                    return rvh;
                }
            };
            recyclerViewHome.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapterHome.startListening();
            recyclerViewHome.setAdapter(adapterHome);
        } else {
            Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment gotofragment = null;
            switch (menuItem.getItemId()) {
                case R.id.favNav:
                    gotofragment = new FavouritesFragment();
                    break;
                case R.id.ordersNav:
                    gotofragment = new MyOrdersFragment();
                    break;
                case R.id.cartNav:
                    gotofragment = new CartFragment();
                    break;
                case R.id.profileNav:
                    gotofragment = new ProfileFragment();
                    break;
            }
            getFragmentManager().beginTransaction().replace(R.id.mainPage, gotofragment).addToBackStack(" ").commit();
            return true;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        if (adapterHome != null && searchAdapter != null) {
            adapterHome.startListening();
            searchAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapterHome != null && searchAdapter != null) {
            adapterHome.stopListening();
            searchAdapter.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapterHome != null && searchAdapter != null) {
            adapterHome.startListening();
            searchAdapter.startListening();
        }
    }
}

//searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//@Override
//public boolean onQueryTextSubmit(String s) {
//        firebaseSearch(s);
//        return false;
//        }
//
//@Override
//public boolean onQueryTextChange(String s) {
//        firebaseSearch(s);
//        return false;
//        }
//        });


//    private void firebaseSearch(String text) {
//
//        String searchText = text.toLowerCase();
//        Query searchQuery = databaseReference.orderByChild("search").startAt(searchText).endAt(searchText + "\uf8ff");
//
//        searchOptions = new FirebaseRecyclerOptions.Builder<RestaurantList>().setQuery(searchQuery, RestaurantList.class).build();
//
//        searchAdapter = new FirebaseRecyclerAdapter<RestaurantList, RestaurantsViewHolder>(searchOptions) {
//            @Override
//            protected void onBindViewHolder(@NonNull RestaurantsViewHolder restaurantsViewHolder, int i, @NonNull RestaurantList restaurantList) {
//                Picasso.get().load(restaurantList.getRestPic()).centerCrop().fit().into(restaurantsViewHolder.image);
//                restaurantsViewHolder.name.setText(restaurantList.getRestName());
//                restaurantsViewHolder.cuisine.setText(restaurantList.getRestType());
//                restaurantsViewHolder.cost.setText(restaurantList.getRestCostForTwo());
//            }
//
//            @NonNull
//            @Override
//            public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_layout, parent, false);
//                RestaurantsViewHolder rvh = new RestaurantsViewHolder(view3);
//                return rvh;
//            }
//        };
//        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewHome.setAdapter(searchAdapter);
//    }