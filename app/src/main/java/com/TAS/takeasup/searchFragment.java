package com.TAS.takeasup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

public class searchFragment extends Fragment {
    SearchView searchView;

    public searchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
//        searchView = view.findViewById(R.id.searchView2);
//        searchView.setBackgroundResource(R.drawable.searchview_rounded);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                Adapter adapter = new Adapter();
//                adapter.getFilter().filter(s);
//                return false;
//            }
//        });
        return view;
    }

}
