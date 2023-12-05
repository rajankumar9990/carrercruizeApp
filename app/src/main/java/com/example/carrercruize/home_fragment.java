package com.example.carrercruize;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class home_fragment extends Fragment {
    private RecyclerView recyclerView;
    private joblistadapter jobAdapter;
    private static final String API_URL = "https://careercruzefunction.azurewebsites.net/api/HttpTrigger1?";


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

        //working upon api....
        // Execute the AsyncTask to make the HTTP request
        new FetchDataTask().execute(API_URL);


        return view;

    }
    private static class FetchDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return fetchData(params[0]);
            } catch (IOException e) {
                return "Network error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the result on the UI thread
            handleResult(result);
        }

        private String fetchData(String apiUrl) throws IOException {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader (in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } finally {
                urlConnection.disconnect();
            }
        }

        private void handleResult(String result) {
            try {
                JSONObject jsonResult = new JSONObject(result);

                // Extract individual parameters
                JSONArray dateArray = jsonResult.getJSONArray("Date");
                JSONArray descArray = jsonResult.getJSONArray("Description");
                JSONArray locationArray = jsonResult.getJSONArray("Location");
                JSONArray salaryArray = jsonResult.getJSONArray("Salary");

                // Process each item
                for (int i = 0; i < dateArray.length(); i++) {
                    String date = dateArray.optString(i, "N/A");
                    String desc = descArray.optString(i, "N/A");
                    String location = locationArray.optString(i, "N/A");
                    String salary = salaryArray.optString(i, "N/A");

                    // Perform actions with individual parameters (e.g., display in UI, log, etc.)
                    Log.d("API Result", "Date: " + date);
                    Log.d("API Result", "Description: " + desc);
                    Log.d("API Result", "Location: " + location);
                    Log.d("API Result", "Salary: " + salary);

                    // You can also store these values in your data structures or use them as needed
                }

            } catch (JSONException e) {
                Log.e("API Result", "Error parsing JSON: " + e.getMessage());
            }
        }
    }
}