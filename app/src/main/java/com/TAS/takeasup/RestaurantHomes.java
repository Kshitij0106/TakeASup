package com.TAS.takeasup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Model.RestaurantHome;
import com.TAS.takeasup.ViewHolder.RestaurantHomeViewHolder;
import com.TAS.takeasup.ViewHolder.ViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RestaurantHomes extends Fragment {
    AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    ViewPager viewPager;
    private SwipeRefreshLayout refreshLayout;
    private DatabaseReference ref;
    private Button menu;
    private RestaurantHomeViewHolder rhvh;
    private String RestaurantName;
    private Bundle rNameBundle = new Bundle();

    public RestaurantHomes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        appBarLayout = view.findViewById(R.id.appBar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        refreshLayout = view.findViewById(R.id.swipeLayoutRestaurantHome);
        menu = view.findViewById(R.id.menuButton);
        rhvh = new RestaurantHomeViewHolder(view);
        //receiving bundle from previous frags
        Bundle ReceivedBundle = this.getArguments();
        RestaurantName = ReceivedBundle.getString("RestaurantName");
        //sending bundle to front frags
        rNameBundle.putString("RestaurantName", RestaurantName);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();

        return view;
    }

    public void load(){
        if (Common.isConnectedToInternet(getActivity())) {
            refreshLayout.setRefreshing(false);
            ref = FirebaseDatabase.getInstance().getReference(RestaurantName).child("Home Details");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        RestaurantHome rh = data.getValue(RestaurantHome.class);
                        Picasso.get().load(rh.getHomePic()).fit().centerCrop().into(rhvh.homePic);
                        rhvh.homeName.setText(rh.getHomeName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuFragment menuFragment = new MenuFragment();
                    menuFragment.setArguments(rNameBundle);
                    getFragmentManager().beginTransaction().replace(R.id.mainPage, menuFragment).addToBackStack("").commit();
                }
            });

            setUpTabs(viewPager);
        } else {
            Toast.makeText(getActivity(), "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUpTabs(ViewPager viewPager) {
        Info_TabView_Fragment info_tabView_fragment = new Info_TabView_Fragment();
        info_tabView_fragment.setArguments(rNameBundle);

        Contact_TabView_Fragment contact_tabView_fragment = new Contact_TabView_Fragment();
        contact_tabView_fragment.setArguments(rNameBundle);

        Gallery_TabView_Fragment gallery_tabView_fragment = new Gallery_TabView_Fragment();
        gallery_tabView_fragment.setArguments(rNameBundle);

        Review_TabView_Fragment review_tabView_fragment = new Review_TabView_Fragment();
        review_tabView_fragment.setArguments(rNameBundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addTabs(info_tabView_fragment, "Info");
        adapter.addTabs(contact_tabView_fragment, "Contact");
        adapter.addTabs(gallery_tabView_fragment, "Gallery");
        adapter.addTabs(review_tabView_fragment, "Review");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
