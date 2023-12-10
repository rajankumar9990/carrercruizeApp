package com.example.carrercruize;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class joblistadapter extends RecyclerView.Adapter<joblistadapter.ViewHolder> {
    private joblisting joblistings;
    boolean showshimmer= true;
    private int shimmerNumber = 5;
    public joblistadapter(joblisting joblistings) {
        this.joblistings = joblistings;

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
            holder.datePostedTextView.setText (joblistings.getDatelist ().get (position));
            holder.titleTextView.setBackground (null);
            holder.titleTextView.setText (joblistings.getJtitlelist ().get (position));
            holder.locationTextView.setBackground (null);
            holder.locationTextView.setText (joblistings.getLocationlist ().get (position));
            holder.salaryTextView.setBackground (null);
            holder.salaryTextView.setText (joblistings.getSalarylist ().get (position));

        }
    }

    @Override
    public int getItemCount() {
        return showshimmer?shimmerNumber:joblistings.getSalarylist ().size ();
    }


    static class ViewHolder extends  RecyclerView.ViewHolder{
        ShimmerFrameLayout shimmerFrameLayout;
        TextView titleTextView;
        TextView locationTextView;
        TextView salaryTextView;
        TextView datePostedTextView;
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            shimmerFrameLayout=itemView.findViewById (R.id.shimmer_view_container);
            titleTextView = itemView.findViewById(R.id.text_job_title);
            locationTextView = itemView.findViewById(R.id.text_job_location);
            salaryTextView = itemView.findViewById(R.id.text_job_salary);
            datePostedTextView = itemView.findViewById(R.id.text_date_posted);
        }
    }
}
