package com.example.carrercruize;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class home_fragment extends Fragment {
    private RecyclerView recyclerView;
    private joblistadapter jobAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate (R.layout.fragment_home_fragment, container, false);
        // Sample data
        List<joblisting> jobListings = new ArrayList<> ();
        jobListings.add(new joblisting ("Software Engineer", "Developing awesome apps", "City A", "$80,000", "2023-01-01"));
        jobListings.add(new joblisting ("Data Scientist", "Analyzing big data sets", "City B", "$90,000", "2023-01-02"));
        // Add more job listings as needed

        // Initialize RecyclerView and set the adapter
        recyclerView = view.findViewById (R.id.recycler_view_jobs);
        jobAdapter = new joblistadapter (jobListings);
        recyclerView.setLayoutManager(new LinearLayoutManager (getActivity ()));
        recyclerView.setAdapter(jobAdapter);

        return view;
    }
}