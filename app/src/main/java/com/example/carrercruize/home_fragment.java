package com.example.carrercruize;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.concurrent.TimeUnit;

public class home_fragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private static  joblistadapter jobAdapter;
    private static joblisting jobListings;
    private static final String API_URL = "https://careercruzefunction.azurewebsites.net/api/HttpTrigger1?";
    private String currentUrl="url=https://www.naukri.com&jtitle=";
    private static final ArrayList<String> datelist = new ArrayList<> (  );
    private static ArrayList<String> locationlist;
    private static ArrayList<String> descriptionlist;
    private static ArrayList<String> salarylist;
    private static ArrayList<String> jtitlelist;
    private static  ArrayList<String> companylist=new ArrayList<> (  );
    private static ArrayList<String> linklists=new ArrayList<> (  );
    private static  ArrayList<String> experienceList=new ArrayList<> (  );
    private static  ArrayList<ArrayList<String>> tagsList= new ArrayList<> ( );
    private static SharedPreferencesManager preferencesManager;
    private static ImageButton filterButton;
    private PopupWindow filterPopup;
    private  Spinner jtitlespinner;
    TextView relodbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate (R.layout.fragment_home_fragment, container, false);
        searchView=view.findViewById (R.id.searchView);
        filterButton=view.findViewById (R.id.filterButton);
        relodbtn=view.findViewById (R.id.textView19);
        jtitlespinner=view.findViewById (R.id.jobTitleSpinner);
        filterButton.setOnClickListener (v-> showpopup());
        // Initialize RecyclerView and set the adapter
//        datelist=new ArrayList<> (  );
        locationlist=new ArrayList<> (  );
        descriptionlist=new ArrayList<> (  );
        salarylist=new ArrayList<> (  );
        jtitlelist=new ArrayList<> (  );

        jobListings=new joblisting ();
        jobAdapter = new joblistadapter (jobListings);

        recyclerView = view.findViewById (R.id.recycler_view_jobs);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity ( )));
        recyclerView.setAdapter (jobAdapter);
        //spinner jobtitle....
        List<String> spinnerlist=new ArrayList<> (  );
        spinnerlist.add ("Data-Scientist");
        spinnerlist.add ("Data-Engineer");
        spinnerlist.add ("Software-Engineer");
        spinnerlist.add ("Junior-Engineer");
        spinnerlist.add ("Full-Stack-Engineer");
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<> (getContext (), android.R.layout.simple_spinner_item,spinnerlist);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jtitlespinner.setAdapter (stringArrayAdapter);
//        jtitlespinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener ( ) {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d ("JobTitlespinner Block:","Running on selected jobttitle");
//                jobListings=new joblisting ();
//                jobAdapter=new joblistadapter (jobListings);
//                jobAdapter.showshimmer=true;
//                jobAdapter.notifyDataSetChanged ();
//                new FetchDataTask ().execute (API_URL+currentUrl+jtitlespinner.getSelectedItem ());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Log.d ("JobTitlespinner Block:","Running on selected jobttitle");
//                jobListings=new joblisting ();
//                jobAdapter=new joblistadapter (jobListings);
//                jobAdapter.showshimmer=true;
//                jobAdapter.notifyDataSetChanged ();
//                new FetchDataTask ().execute (API_URL+currentUrl+jtitlespinner.getSelectedItem ());
//            }
//        });

        //working upon api....
        // Execute the AsyncTask to make the HTTP request
        preferencesManager= new SharedPreferencesManager(getContext ());
        // Check if an update is needed (e.g., every 24 hours)
        long updateIntervalMillis = TimeUnit.MINUTES.toMillis(2);
        if (preferencesManager.shouldUpdateData(updateIntervalMillis) ) {
            // Perform the update by fetching new data from the API
            // Update the JobListing object and save it

            Log.d ("time stamp changed:","Updating list for new time stamp");
            try {
                clearlist ();
                jobListings=new joblisting ();
                jobAdapter.setdata (jobListings);
                jobAdapter.showshimmer=true;
                jobAdapter.notifyDataSetChanged ();
                FetchDataTask t1=new FetchDataTask ( );
                FetchDataTask t2=new FetchDataTask ();
                t1.execute (API_URL + currentUrl + jtitlespinner.getSelectedItem ( ));
                if(t1.getStatus ().equals (AsyncTask.Status.FINISHED)) {
                    Log.d ("executing:","Foundit");
                    t2.execute (API_URL + "url=https://www.foundit.in/search&jtitle=" + jtitlespinner.getSelectedItem ( ));
                }
                Log.d ("Both fetched..","Now saving....");

            } catch (Exception e) {
                e.printStackTrace ( );
            }


        }
        else{
            jobListings=preferencesManager.getJobListing ();
            Log.d("Retrieved data",String.valueOf (jobListings.getCompanylist ().size ()));
            if(jobListings.getCompanylist ().size ()>1){
                jobAdapter.setdata (jobListings);
                jobAdapter.showshimmer=false;
                jobAdapter.notifyDataSetChanged ();
            }

        }
        //Relod btn
        relodbtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (jobListings!=null){
                    Log.d ("Relod:","Reloding the data list");
                jobAdapter.setdata (jobListings);
                jobAdapter.showshimmer=false;
                jobAdapter.notifyDataSetChanged();
            }}
        });
        //searchView implementation.............
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the submission of the search query (if needed)
                jobAdapter.getFilter ( ).filter (query);
                Log.d ("no of items present:",String.valueOf (jobAdapter.getItemCount ()));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter your data based on the search query and update the RecyclerView

                return true;
            }

        });
        return view;
    }


    private void showpopup(){
        View popupView = LayoutInflater.from(getActivity ().getApplicationContext ()).inflate(R.layout.filter_popup, null);

        // Initialize the PopupWindow
        filterPopup = new PopupWindow (
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Set the animation style (optional)
        filterPopup.setAnimationStyle(R.anim.fade_in);

        // Set up button click listener in the popup
        Button applyButton = popupView.findViewById(R.id.applyButton);
        CheckBox sortbydate=popupView.findViewById (R.id.checkSortDateNewlyFirst);
        RadioButton sortlth=popupView.findViewById (R.id.radioSortSalaryLowToHigh);
        RadioButton sorthtl=popupView.findViewById (R.id.radioSortSalaryHighToLow);
        RadioButton sortexp=popupView.findViewById (R.id.radioSortbyexperience);
//        RadioButton sortnaukri=popupView.findViewById (R.id.naukriRadioButton);
//        RadioButton sortfoundit=popupView.findViewById (R.id.founditRadioButton);
        ImageButton close=popupView.findViewById (R.id.imageView3);
        close.setOnClickListener (v->filterPopup.dismiss ());
        applyButton.setOnClickListener(v -> {
            // Handle the apply button click
            // Apply your filter logic here
            if(sorthtl.isChecked ()){
                jobAdapter.sortbySalary(false);
            }else if (sortlth.isChecked ()){
                jobAdapter.sortbySalary (true);
            }if(sortbydate.isChecked ()){
                jobAdapter.sortbyDate ();
            }else if(sortexp.isChecked ()){
                jobAdapter.sortbyExperience ();
            }
//            if(sortnaukri.isChecked ()){
//                Log.d ("Sort applying: ","naukri.com data is being fatched");
//                String url="url=https://www.naukri.com&jtitle=";
//                currentUrl=url;
//                jobListings=new joblisting ();
//                jobAdapter=new joblistadapter (jobListings);
//                recyclerView.setAdapter (jobAdapter);
//                new FetchDataTask ().execute (API_URL+currentUrl+jtitlespinner.getSelectedItem ());
//            }
//            else if(sortfoundit.isChecked ()){
//                Log.d ("Sort applying: ","Foundit data is being fatched");
//                String url="url=https://www.foundit.in/search&jtitle=";
//                currentUrl=url;
//                jobListings=new joblisting ();
//                jobAdapter=new joblistadapter (jobListings);
//                recyclerView.setAdapter (jobAdapter);
//                new FetchDataTask ().execute (API_URL+currentUrl+jtitlespinner.getSelectedItem ());
//            }
            filterPopup.dismiss();
        });

        // Show the popup at a specific location relative to the filter button
        int[] location = new int[2];
        filterButton.getLocationOnScreen(location);
        filterPopup.showAtLocation(filterButton, Gravity.TOP | Gravity.START, location[0], location[1]);

        // Set up dismiss listener to handle dismissal
        filterPopup.setOnDismissListener(() -> {
            // Perform actions when the popup is dismissed (if needed)
        });
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
                JSONArray linksarray=jsonResult.getJSONArray ("Link");
                JSONArray comparray= jsonResult.getJSONArray ("Company" );
                JSONArray exparray=jsonResult.getJSONArray ("Experience");
                JSONArray tagsarrasy=jsonResult.getJSONArray ("Tags");
                Log.d ("no of tags",String.valueOf (tagsarrasy.length ()));
//                datelist.clear ();
//                descriptionlist.clear ();
//                locationlist.clear ();
//                salarylist.clear ();
//                jtitlelist.clear ();
//                linklists.clear ();
//                companylist.clear ();
//                experienceList.clear ();
//                tagsList.clear ();
                // Process each item
                for (int i = 0; i < dateArray.length(); i++) {
                    String date = dateArray.optString(i, "N/A");
                    String desc = descArray.optString(i, "N/A");
                    String location = locationArray.optString(i, "N/A");
                    String salary = salaryArray.optString(i, "N/A");
                    String jobtitle=joarray.optString (i,"N/A");
                    String link=linksarray.optString (i,"N/A");
                    String cp=comparray.optString (i,"N/A");
                    String expstr=exparray.optString (i,"N/A");
                    ArrayList<String> tagsTobeAdded=new ArrayList<> (  );
                    JSONArray innerArray=tagsarrasy.getJSONArray (i);
                    for (int j=0;j<innerArray.length ();j++){
                        tagsTobeAdded.add (innerArray.optString (j,"N/A"));
                    }
                    datelist.add (date);
                    descriptionlist.add (desc);
                    locationlist.add (location);
                    salarylist.add (salary);
                    jtitlelist.add (jobtitle);
                    linklists.add (link);
                    companylist.add (cp);
                    experienceList.add (expstr);
                    tagsList.add (tagsTobeAdded);
                    if(!salarylist.isEmpty ()){
                        jobListings.setSalarylist (salarylist);
                        jobListings.setDatelist (datelist);
                        jobListings.setJtitlelist (jtitlelist);
                        jobListings.setLocationlist (locationlist);
                        jobListings.setLinklists (linklists);
                        jobListings.setCompanylist (companylist);
                        jobListings.setTagsList (tagsList);
                        jobListings.setDescriptionList (descriptionlist);
                        jobListings.setExperienceList (experienceList);
                        preferencesManager.saveJobListing (jobListings);//saving data in sharedPrefernces
                        Log.d("Data saved!",String.valueOf (preferencesManager.getJobListing ().getCompanylist ().size ()));
                        jobAdapter.showshimmer=false;
                        jobAdapter.notifyDataSetChanged ();
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
    private void clearlist(){
        datelist.clear ();
        descriptionlist.clear ();
        locationlist.clear ();
        salarylist.clear ();
        jtitlelist.clear ();
        linklists.clear ();
        companylist.clear ();
        experienceList.clear ();
        tagsList.clear ();
    }
}