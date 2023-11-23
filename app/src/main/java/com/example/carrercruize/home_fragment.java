package com.example.carrercruize;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

        //location spinner
        Spinner locationspiner=view.findViewById (R.id.spinner_location);
        List<String> locationList = new ArrayList<>();
        locationList.add ("Location");
        locationList.add("Sonipat");
        locationList.add("Mumbai");
        locationList.add ("New Delhi");
        locationList.add("Pune");
        // Add more cities as needed

        // Populate the Spinner with the list of items
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                locationList
        );
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationspiner.setAdapter (locationAdapter);

        //salary spinner
        Spinner salaryspiner=view.findViewById (R.id.spinner_salary);
        List<String> salaryList = new ArrayList<>();
        salaryList.add ("Salary");
        salaryList.add("< 1Lakh>");
        salaryList.add("1Lakh to 10Lakh");
        salaryList.add ("> 10Lakh");
        // Add more cities as needed

        // Populate the Spinner with the list of items
        ArrayAdapter<String> salaryAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                salaryList
        );
        salaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salaryspiner.setAdapter (salaryAdapter);

        //job spinner
        Spinner jobspiner=view.findViewById (R.id.spinner_job_title);
        List<String> jobList = new ArrayList<>();
        jobList.add ("Job Title");
        jobList.add("Data Scientist");
        jobList.add("Android Dev");
        jobList.add ("Web Dev");
        jobList.add("Software Engineer");
        // Add more cities as needed

        // Populate the Spinner with the list of items
        ArrayAdapter<String> jobAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                jobList
        );
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobspiner.setAdapter (jobAdapter);
        return view;
    }
}