package com.example.b07project.ui.complaints.student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.objects.Complaint;

import java.util.List;

public class StudentComplaintAdapter extends RecyclerView.Adapter<StudentComplaintViewHolder> {
    Context context;
    List<com.example.b07project.objects.Complaint> myComplaints;
    private final StudentComplaintViewInterface studentComplaintViewInterface;

    public StudentComplaintAdapter(Context context, List<Complaint> complaints, StudentComplaintViewInterface studentComplaintViewInterface) {
        this.context = context;
        this.myComplaints = complaints;
        this.studentComplaintViewInterface = studentComplaintViewInterface;
    }
    @NonNull
    @Override
    public StudentComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentComplaintViewHolder(LayoutInflater.from(context).inflate(R.layout.past_complaints, parent, false), studentComplaintViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentComplaintViewHolder holder, int position) {
        holder.title.setText(myComplaints.get(position).getTitle());
        holder.icon.setImageResource(myComplaints.get(position).getIcon());
        holder.date.setText(myComplaints.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return myComplaints.size();
    }
}
