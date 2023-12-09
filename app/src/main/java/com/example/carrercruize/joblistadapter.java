package com.example.carrercruize;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class joblistadapter extends RecyclerView.Adapter<joblistingholder> {
    private joblisting joblistings;

    public joblistadapter(joblisting joblistings) {
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

        // Bind data to ViewHolder
        holder.titleTextView.setText(joblistings.getJtitlelist ().get (position));
        holder.descriptionTextView.setText(joblistings.getDescriptionlist ().get (position));
        holder.locationTextView.setText(joblistings.getLocationlist ().get (position));
        holder.salaryTextView.setText(joblistings.getSalarylist ().get (position));
        holder.datePostedTextView.setText(joblistings.getDatelist ().get (position));
    }

    @Override
    public int getItemCount() {
        return joblistings.getJtitlelist ().size();
    }
}
