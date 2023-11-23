package com.example.carrercruize;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class joblistadapter extends RecyclerView.Adapter<joblistingholder> {
    private List<joblisting> joblistings;

    public joblistadapter(List<joblisting> joblistings) {
        this.joblistings = joblistings;
    }

    @Override
    public joblistingholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobadapter, parent, false);
        return new joblistingholder(view);
    }

    @Override
    public void onBindViewHolder(joblistingholder holder, int position) {
        joblisting joblisting = joblistings.get(position);

        // Bind data to ViewHolder
        holder.titleTextView.setText(joblisting.getTitle());
        holder.descriptionTextView.setText(joblisting.getDescription());
        holder.locationTextView.setText(joblisting.getLocation());
        holder.salaryTextView.setText(joblisting.getSalary());
        holder.datePostedTextView.setText(joblisting.getDatePosted());
    }

    @Override
    public int getItemCount() {
        return joblistings.size();
    }
}
