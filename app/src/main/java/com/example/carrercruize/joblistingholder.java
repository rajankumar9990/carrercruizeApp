package com.example.carrercruize;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class joblistingholder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView locationTextView;
    public TextView salaryTextView;
    public TextView datePostedTextView;

    public joblistingholder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.text_job_title);
        locationTextView = itemView.findViewById(R.id.text_job_location);
        salaryTextView = itemView.findViewById(R.id.text_job_salary);
        datePostedTextView = itemView.findViewById(R.id.text_date_posted);
    }
}
