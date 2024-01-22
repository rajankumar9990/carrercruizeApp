package com.example.carrercruize;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "Joblistingretreive";
    private static final String KEY_JOB_LISTING = "job_listing";
    private static final String KEY_LAST_UPDATE_TIMESTAMP = "last_update_timestamp";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveJobListing(joblisting jobListing) {
        // Convert the JobListing object to a JSON string
        String json = gson.toJson(jobListing);

        // Save the JSON string to SharedPreferences
        sharedPreferences.edit().putString(KEY_JOB_LISTING, json).apply();

        long currentTimestamp = System.currentTimeMillis();
        sharedPreferences.edit().putLong(KEY_LAST_UPDATE_TIMESTAMP, currentTimestamp).apply();

    }

    public joblisting getJobListing() {
        // Retrieve the JSON string from SharedPreferences
        String json = sharedPreferences.getString(KEY_JOB_LISTING, null);

        if (json != null) {
            // Convert the JSON string back to a JobListing object
            return gson.fromJson(json, joblisting.class);
        } else {
            return null;
        }
    }

//    private String jobListingToJsonString(joblisting jobListing) {
//        JSONObject json = new JSONObject();
//
//        try {
//            json.put("datelist", new JSONObject().put("datelist", jobListing.getDatelist()));
//            json.put("locationlist", new JSONObject().put("locationlist", jobListing.getLocationlist()));
//            json.put("experiencelist",new JSONObject (  ).put ("experiencelist",jobListing.getExperienceList ()));
//            json.put("salarylist",new JSONObject (  ).put ("salarylist",jobListing.getSalarylist ()));
//            json.put("linklists",new JSONObject (  ).put ("linklists",jobListing.getLinklists ()));
//            json.put("companylist",new JSONObject (  ).put ("companylist",jobListing.getCompanylist ()));
//            json.put("jtitlelist",new JSONObject (  ).put ("jtitlelist",jobListing.getJtitlelist ()));
//            json.put("tagslist",new JSONObject (  ).put ("tagslist",jobListing.getTagsList ()));
//            // Repeat the process for other properties...
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return json.toString();
//    }
//
//    private joblisting jsonStringToJobListing(String jsonString) {
//        try {
//            JSONObject json = new JSONObject(jsonString);
//            joblisting jobListing = new joblisting ();
//
//            // Extract values from JSON and set them in the JobListing object
//            jobListing.setDatelist(new ArrayList<> (Arrays.asList(json.getJSONObject("datelist").getString("datelist"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("locationlist").getString("locationlist"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("experiencelist").getString("experiencelist"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("salarylist").getString("salarylist"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("linklists").getString("linklists"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("companylist").getString("companylist"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("jtitlelist").getString("jtitlelist"))));
//            jobListing.setLocationlist(new ArrayList<>(Arrays.asList(json.getJSONObject("tagslist").getString("tagslist"))));
//
//            // Repeat the process for other properties...
//
//            return jobListing;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public boolean shouldUpdateData(long updateIntervalMillis) {
        // Get the last update timestamp from SharedPreferences
        long lastUpdateTimestamp = sharedPreferences.getLong(KEY_LAST_UPDATE_TIMESTAMP, 0);

        // Calculate the time elapsed since the last update
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - lastUpdateTimestamp;

        // Check if the time elapsed is greater than the update interval
        return timeElapsed > updateIntervalMillis;
    }
}
