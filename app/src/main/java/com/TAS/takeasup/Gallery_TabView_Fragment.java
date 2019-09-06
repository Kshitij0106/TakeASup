package com.TAS.takeasup;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Model.GalleryPics;
import com.TAS.takeasup.ViewHolder.GalleryPicsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Gallery_TabView_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private DatabaseReference ref;
    private FirebaseRecyclerOptions<GalleryPics> option;
    private FirebaseRecyclerAdapter<GalleryPics, GalleryPicsViewHolder> adapter;

    public Gallery_TabView_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery__tab_view_, container, false);

        refresh = view.findViewById(R.id.swipeLayoutRestaurantGallery);
        recyclerView = view.findViewById(R.id.recyclerViewGallery);
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
        Bundle bundle = this.getArguments();
        String RestaurantName = bundle.getString("RestaurantName");

        if (Common.isConnectedToInternet(getActivity())) {

            refresh.setRefreshing(false);

            ref = FirebaseDatabase.getInstance().getReference(RestaurantName).child("TypeOfPics");
            option = new FirebaseRecyclerOptions.Builder<GalleryPics>().setQuery(ref, GalleryPics.class).build();
            adapter = new FirebaseRecyclerAdapter<GalleryPics, GalleryPicsViewHolder>(option) {
                @Override
                protected void onBindViewHolder(@NonNull GalleryPicsViewHolder galleryPicsViewHolder, final int i, @NonNull final GalleryPics galleryPics) {
                    Picasso.get().load(galleryPics.getCoverPic()).fit().centerCrop().into(galleryPicsViewHolder.imageGallery);
                    galleryPicsViewHolder.textGallery.setText(galleryPics.getCoverName());

                    galleryPicsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Gallery.class);
                            String name = galleryPics.getCoverName();
                            intent.putExtra("coverName", name);
                            startActivity(intent);
                        }
                    });
                }

                @NonNull
                @Override
                public GalleryPicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_layout, parent, false);
                    GalleryPicsViewHolder gvh = new GalleryPicsViewHolder(view1);
                    return gvh;
                }
            };
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
