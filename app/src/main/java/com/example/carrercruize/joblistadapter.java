package com.example.carrercruize;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class joblistadapter extends RecyclerView.Adapter<joblistadapter.ViewHolder> {
    private joblisting joblistings;
    private joblisting filteredjoblist=new joblisting ();
    boolean isFiltered=false;
    boolean showshimmer= true;
    private int shimmerNumber = 5;
    public joblistadapter(joblisting joblistings) {
        this.joblistings = joblistings;

    }
    public joblisting getdata(){
        return this.joblistings;
    }
    public void setdata(joblisting joblisting1){
        this.joblistings=joblisting1;
    }
    //sort by experience
    public void sortbyExperience() {
        // Create a list of indices to maintain the original order
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < joblistings.getExperienceList ().size(); i++) {
            indices.add(i);
        }

        // Sort the indices based on the salary array
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            Collections.sort(indices, Comparator.comparing (joblistings.getExperienceList ()::get));
        }

        // Rearrange all arrays based on the sorted indices
        joblistings.datelist = rearrangeArray(joblistings.datelist, indices);
        joblistings.locationlist = rearrangeArray(joblistings.locationlist, indices);
        joblistings.salarylist = rearrangeArray(joblistings.salarylist, indices);
        joblistings.linklists = rearrangeArray(joblistings.linklists, indices);
        joblistings.companylist = rearrangeArray(joblistings.companylist, indices);
        joblistings.jtitlelist = rearrangeArray(joblistings.jtitlelist, indices);
        joblistings.experienceList=rearrangeArray (joblistings.getExperienceList (),indices);
        joblistings.tagsList=rearrangeArrayofArray (joblistings.getTagsList (),indices);
        notifyDataSetChanged ();
    }
    public void sortbyDate() {
        // Create a list of indices to maintain the original order
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < joblistings.getDatelist ().size(); i++) {
            indices.add(i);
        }

        // Sort the indices based on the salary array
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            Collections.sort(indices, Comparator.comparing (joblistings.getDatelist ()::get));
        }

        // Rearrange all arrays based on the sorted indices
        joblistings.datelist = rearrangeArray(joblistings.datelist, indices);
        joblistings.locationlist = rearrangeArray(joblistings.locationlist, indices);
        joblistings.salarylist = rearrangeArray(joblistings.salarylist, indices);
        joblistings.linklists = rearrangeArray(joblistings.linklists, indices);
        joblistings.companylist = rearrangeArray(joblistings.companylist, indices);
        joblistings.jtitlelist = rearrangeArray(joblistings.jtitlelist, indices);
        notifyDataSetChanged ();
    }

    public void sortbySalary(boolean asc) {
        // Create a list of indices to maintain the original order
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < joblistings.getSalarylist ().size(); i++) {
            indices.add(i);
        }

        // Sort the indices based on the salary array
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
            if(asc){
            Collections.sort(indices, Comparator.comparing (joblistings.getSalarylist ()::get));}
            else{
                Collections.sort(indices, Comparator.comparing (joblistings.getSalarylist ()::get).reversed ());
            }
        }

        // Rearrange all arrays based on the sorted indices
            joblistings.datelist = rearrangeArray(joblistings.datelist, indices);
            joblistings.locationlist = rearrangeArray(joblistings.locationlist, indices);
            joblistings.salarylist = rearrangeArray(joblistings.salarylist, indices);
            joblistings.linklists = rearrangeArray(joblistings.linklists, indices);
            joblistings.companylist = rearrangeArray(joblistings.companylist, indices);
            joblistings.jtitlelist = rearrangeArray(joblistings.jtitlelist, indices);
            notifyDataSetChanged ();
    }
    // Helper method to rearrange an ArrayList based on indices
    private static ArrayList<String> rearrangeArray(ArrayList<String> original, ArrayList<Integer> indices) {
        ArrayList<String> rearranged = new ArrayList<>(original.size());
        for (int index : indices) {
            rearranged.add(original.get(index));
        }
        return rearranged;
    }
    private static ArrayList<ArrayList<String>> rearrangeArrayofArray(ArrayList<ArrayList<String>> original, ArrayList<Integer> indices) {
        ArrayList<ArrayList<String>> rearranged = new ArrayList<>(original.size());
        for (int index : indices) {
            rearranged.add(original.get(index));
        }
        return rearranged;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from (parent.getContext ( )).inflate (R.layout.jobadapter,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(showshimmer){
            holder.shimmerFrameLayout.startShimmer ();
        }else{
            holder.shimmerFrameLayout.stopShimmer ();
            holder.shimmerFrameLayout.setShimmer (null);
            holder.datePostedTextView.setBackground (null);
            holder.datePostedTextView.setText (joblistings.getDatelist ().get (position)+"+ Days Ago");
            holder.titleTextView.setBackground (null);
            holder.titleTextView.setText (joblistings.getJtitlelist ().get (position));
            holder.locationTextView.setBackground (null);
            holder.locationTextView.setText (joblistings.getLocationlist ().get (position));
            holder.salaryTextView.setBackground (null);
            String salar=joblistings.getSalarylist ().get (position);
            if (Integer.parseInt (salar)<=0){
                holder.salaryTextView.setText ("Not Disclosed");
            }else{
                holder.salaryTextView.setText (salar+" Lakhs");
            }

            String url=joblistings.getLinklists ().get (position);
            holder.applybtn.setOnClickListener (new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    // Create an Intent with the ACTION_VIEW action
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    // Set the data of the Intent to the URL
                    intent.setData(Uri.parse (url));

                    // Start the activity using the created Intent
                    view.getContext ().startActivity (intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return showshimmer?shimmerNumber:joblistings.getSalarylist ().size ();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();

                if (filterPattern.trim ().isEmpty()) {
                    isFiltered=false;
                    filteredjoblist = joblistings;
                } else {
                    ArrayList<String> datelist=new ArrayList<> (  );
                    ArrayList<String> locationlist=new ArrayList<> (  );
                    ArrayList<String> salarylist=new ArrayList<> (  );
                    ArrayList<String> linklists=new ArrayList<> (  );
                    ArrayList<String> companylist=new ArrayList<> (  );
                    ArrayList<String> jtitlelist=new ArrayList<> (  );
                    for (int i=0;i<joblistings.getDatelist ().size ();i++) {
                        if (joblistings.getCompanylist ().get (i).toLowerCase().contains(filterPattern) | joblistings.getLocationlist ().get (i).toLowerCase()
                                .contains (filterPattern)) {
                            isFiltered=true;
                            datelist.add (joblistings.getDatelist ().get (i));
                            locationlist.add (joblistings.getLocationlist ().get (i));
                            salarylist.add (joblistings.getSalarylist ().get (i));
                            linklists.add (joblistings.getLinklists ().get (i));
                            companylist.add (joblistings.getCompanylist ().get (i));
                            Log.d("comany filtered: ",joblistings.getCompanylist ().get (i));
                            jtitlelist.add (joblistings.getJtitlelist ().get (i));

                        }
                    }
                    if(companylist.size ()>0){
                    filteredjoblist.setCompanylist (companylist);
                    filteredjoblist.setLinklists (linklists);
                    filteredjoblist.setLocationlist (locationlist);
                    filteredjoblist.setJtitlelist (jtitlelist);
                    filteredjoblist.setDatelist (datelist);
                    filteredjoblist.setSalarylist (salarylist);}
                }

                FilterResults results = new FilterResults();
                results.values = filteredjoblist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                joblistings = (joblisting) results.values;
                notifyDataSetChanged();
            }

        };
    }

    static class ViewHolder extends  RecyclerView.ViewHolder{
        ShimmerFrameLayout shimmerFrameLayout;
        TextView titleTextView;
        TextView locationTextView;
        TextView salaryTextView;
        TextView datePostedTextView;
        Button applybtn;
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            shimmerFrameLayout=itemView.findViewById (R.id.shimmer_view_container);
            titleTextView = itemView.findViewById(R.id.text_job_title);
            locationTextView = itemView.findViewById(R.id.text_job_location);
            salaryTextView = itemView.findViewById(R.id.text_job_salary);
            datePostedTextView = itemView.findViewById(R.id.text_date_posted);
            applybtn=itemView.findViewById (R.id.button3);
        }
    }

}
