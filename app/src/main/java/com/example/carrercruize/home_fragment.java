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
    private static  joblistadapter jobAdapter;
    private static joblisting jobListings;
    private static final String API_URL = "https://careercruzefunction.azurewebsites.net/api/HttpTrigger1?";
    private static final ArrayList<String> datelist = new ArrayList<> (  );
    private static ArrayList<String> locationlist;
    private static ArrayList<String> descriptionlist;
    private static ArrayList<String> salarylist;
    private static ArrayList<String> jtitlelist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate (R.layout.fragment_home_fragment, container, false);
        //working upon api....
        // Execute the AsyncTask to make the HTTP request
        new FetchDataTask().execute(API_URL);

        // Initialize RecyclerView and set the adapter
//        datelist=new ArrayList<> (  );
        locationlist=new ArrayList<> (  );
        descriptionlist=new ArrayList<> (  );
        salarylist=new ArrayList<> (  );
        jtitlelist=new ArrayList<> (  );

        jobListings=new joblisting ();




        recyclerView = view.findViewById (R.id.recycler_view_jobs);
        if(salarylist.isEmpty ()) {
            jobAdapter = new joblistadapter (jobListings);
            recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( )));
            recyclerView.setAdapter (jobAdapter);
        }




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
                JSONArray joarray=jsonResult.getJSONArray ("Title");

                // Process each item
                for (int i = 0; i < dateArray.length(); i++) {
                    String date = dateArray.optString(i, "N/A");
                    String desc = descArray.optString(i, "N/A");
                    String location = locationArray.optString(i, "N/A");
                    String salary = salaryArray.optString(i, "N/A");
                    String jobtitle=joarray.optString (i,"N/A");
                    datelist.add (date);
                    descriptionlist.add (desc);
                    locationlist.add (location);
                    salarylist.add (salary);
                    jtitlelist.add (jobtitle);

                    if(!salarylist.isEmpty ()){
                        jobListings.setSalarylist (salarylist);
                        jobListings.setDatelist (datelist);
                        jobListings.setJtitlelist (jtitlelist);
                        jobListings.setLocationlist (locationlist);
                        jobAdapter.showshimmer=false;
                        // Notify the adapter that the dataset has changed
                        jobAdapter.notifyDataSetChanged();
                    }

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