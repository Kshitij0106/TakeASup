package com.TAS.takeasup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.TAS.takeasup.Common.Common;
import com.TAS.takeasup.Model.Pics;
import com.TAS.takeasup.ViewHolder.PicsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Gallery extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions<Pics> options;
    private FirebaseRecyclerAdapter<Pics, PicsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        refresh = findViewById(R.id.swipeLayoutGallery);
        recyclerView = findViewById(R.id.recyclerViewGalleryPics);
        recyclerView.setHasFixedSize(true);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();
    }

    public void load() {
        String covername = getIntent().getStringExtra("coverName");
        String restName = getIntent().getStringExtra("restName");

        if (Common.isConnectedToInternet(this)) {

            refresh.setRefreshing(false);

            databaseReference = FirebaseDatabase.getInstance().getReference(restName).child(covername);
            options = new FirebaseRecyclerOptions.Builder<Pics>().setQuery(databaseReference, Pics.class).build();
            adapter = new FirebaseRecyclerAdapter<Pics, PicsViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull PicsViewHolder picsViewHolder, int i, @NonNull Pics pics) {
                    Picasso.get().load(pics.getUrl()).fit().centerCrop().into(picsViewHolder.activityPics);
                }

                @NonNull
                @Override
                public PicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_pics, parent, false);
                    PicsViewHolder pvh = new PicsViewHolder(view);
                    return pvh;
                }
            };
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Please Check Your Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }
}
