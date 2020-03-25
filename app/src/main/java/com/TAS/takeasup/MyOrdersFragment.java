package com.TAS.takeasup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Model.Order;
import com.TAS.takeasup.Model.Request;
import com.TAS.takeasup.Model.UsersPastOrders;
import com.TAS.takeasup.ViewHolder.PastOrdersDishesAdapter;
import com.TAS.takeasup.ViewHolder.UsersPastOrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyOrdersFragment extends Fragment {
    private RecyclerView myOrdersRecyclerView;
    private TextView shopNowMyOrders,emptyMyOrders;
    private DatabaseReference myOrdersRef;
    private FirebaseAuth auth;
    private List<Order> pastOrders;
    private FirebaseRecyclerOptions<UsersPastOrders> myOrdersOptions;
    private FirebaseRecyclerAdapter<UsersPastOrders, UsersPastOrdersViewHolder> myOrdersAdapter;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myorders, container, false);

        shopNowMyOrders = view.findViewById(R.id.shopNowMyOrders);
        emptyMyOrders = view.findViewById(R.id.emptyMyOrders);

        myOrdersRecyclerView = view.findViewById(R.id.myOrdersRecyclerView);
        myOrdersRecyclerView.setHasFixedSize(true);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();

        pastOrders = new ArrayList<>();

        myOrdersRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Past Orders");
        myOrdersOptions = new FirebaseRecyclerOptions.Builder<UsersPastOrders>().setQuery(myOrdersRef, UsersPastOrders.class).build();
        myOrdersAdapter = new FirebaseRecyclerAdapter<UsersPastOrders, UsersPastOrdersViewHolder>(myOrdersOptions) {
            @Override
            protected void onBindViewHolder(@NonNull UsersPastOrdersViewHolder usersPastOrdersViewHolder, int i, @NonNull UsersPastOrders usersPastOrders) {
                usersPastOrdersViewHolder.totalOrder.setText("Total Order : " + usersPastOrders.getTotalOrder());
                usersPastOrdersViewHolder.status.setText("Status : " + usersPastOrders.getStatus());
                usersPastOrdersViewHolder.orderNo.setText("Order No. : " + usersPastOrders.getOrderId());
                pastOrders = usersPastOrders.getPastOrdersList();

                PastOrdersDishesAdapter pdAdapter = new PastOrdersDishesAdapter(pastOrders, getActivity());

                usersPastOrdersViewHolder.dishesRecyclerView.setHasFixedSize(true);
                usersPastOrdersViewHolder.dishesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                usersPastOrdersViewHolder.dishesRecyclerView.setAdapter(pdAdapter);
                pdAdapter.notifyDataSetChanged();
            }

            @NonNull
            @Override
            public UsersPastOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders, parent, false);
                UsersPastOrdersViewHolder upvh = new UsersPastOrdersViewHolder(view1);
                return upvh;
            }
        };

        myOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myOrdersRecyclerView.setAdapter(myOrdersAdapter);
        myOrdersAdapter.startListening();
        myOrdersAdapter.notifyDataSetChanged();

        myOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    shopNowMyOrders.setVisibility(View.VISIBLE);
                    emptyMyOrders.setVisibility(View.VISIBLE);
                    shopNowMyOrders.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getFragmentManager().beginTransaction().replace(R.id.mainPage,new HomePageFragment()).addToBackStack("").commit();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (myOrdersAdapter != null)
            myOrdersAdapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myOrdersAdapter != null)
            myOrdersAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (myOrdersAdapter != null)
            myOrdersAdapter.stopListening();
    }
}