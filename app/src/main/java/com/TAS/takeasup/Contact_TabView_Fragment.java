package com.TAS.takeasup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Model.RestaurantDetails;
import com.TAS.takeasup.ViewHolder.RestaurantDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact_TabView_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private DatabaseReference ref;
    private FirebaseRecyclerOptions<RestaurantDetails> options;
    private FirebaseRecyclerAdapter<RestaurantDetails, RestaurantDetailsViewHolder> adapter;


    public Contact_TabView_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact__tab_view_, container, false);

        refresh = view.findViewById(R.id.swipeLayoutRestaurantContact);
        recyclerView = view.findViewById(R.id.recyclerViewContact);

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

        Bundle bundle = this.getArguments();
        String RestaurantName = bundle.getString("RestaurantName");

        if (Common.isConnectedToInternet(getActivity())) {
            refresh.setRefreshing(false);
            ref = FirebaseDatabase.getInstance().getReference(RestaurantName).child("Restaurant Contact");
            options = new FirebaseRecyclerOptions.Builder<RestaurantDetails>().setQuery(ref, RestaurantDetails.class).build();
            adapter = new FirebaseRecyclerAdapter<RestaurantDetails, RestaurantDetailsViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull RestaurantDetailsViewHolder restaurantDetailsViewHolder, int i, @NonNull RestaurantDetails restaurantDetails) {
                    restaurantDetailsViewHolder.restPhoneNo.setText(restaurantDetails.getRestaurantPhoneNo());
                    restaurantDetailsViewHolder.restWebsite.setText(restaurantDetails.getRestaurantWebsite());
                    restaurantDetailsViewHolder.restFacebook.setText(restaurantDetails.getRestaurantFacebook());
                    restaurantDetailsViewHolder.restInstagram.setText(restaurantDetails.getRestaurantInstagram());
                }

                @NonNull
                @Override
                public RestaurantDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
                    RestaurantDetailsViewHolder rdvh = new RestaurantDetailsViewHolder(v);
                    return rdvh;
                }
            };
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter.startListening();
            recyclerView.setAdapter(adapter);
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
