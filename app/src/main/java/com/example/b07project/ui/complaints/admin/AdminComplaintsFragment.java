package com.example.b07project.ui.complaints.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b07project.objects.admin.Complaint;
import com.example.b07project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class AdminComplaintsFragment extends Fragment implements ComplaintViewInterface {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref, userRef;
    private RecyclerView recyclerView;
    List<Complaint> complaints;
    String fullName, complaintInfo, complaintDesc, complaintDate;
    public AdminComplaintsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_complaints, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = firebaseDatabase.getReference("Complaints");
        userRef = firebaseDatabase.getReference("Users");

        complaints = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new ComplaintAdapter(getActivity().getApplicationContext(), complaints, this));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot name: snapshot.getChildren()) {
                    for (DataSnapshot complaint: name.getChildren()) {
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot userInfo: snapshot.getChildren()) {
                                    if (userInfo.getKey().equals(name.getKey())) {
                                        for (DataSnapshot values: userInfo.getChildren()) {
                                            if (values.getKey().equals("Name")) {
                                                fullName = values.getValue(String.class);
                                                complaintInfo = complaint.getValue().toString();
                                                try {
                                                    complaintDesc = complaintInfo.substring(complaintInfo.indexOf("=") + 1, complaintInfo.indexOf(','));
                                                    complaintDate = complaintInfo.substring(complaintInfo.indexOf(',') + 7, complaintInfo.indexOf('}'));
                                                } catch (StringIndexOutOfBoundsException e) {
                                                    complaintDesc = complaintInfo;
                                                    complaintDate = "";
                                                }
                                                if (fullName != null && complaint.getKey() != null && complaintDesc != null && complaintDate != null
                                                && !complaintDate.equals("")) {
                                                    Complaint c = new Complaint(fullName, complaint.getKey(), complaintDesc, R.drawable.megaphone, complaintDate);
                                                    if (!complaints.contains(c) && c.getDate() != null) {
                                                        complaints.add(c);
                                                        Collections.sort(complaints);
                                                        Collections.reverse(complaints);
                                                        recyclerView.getAdapter().notifyItemInserted(0);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {
        String title = complaints.get(position).getTitle();
        String name = complaints.get(position).getName();
        String desc = complaints.get(position).getDescription();
        String date = complaints.get(position).getDate();
        this.getParentFragmentManager().beginTransaction().hide(this).commit();
        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fragTrans.add(R.id.adminContainer, new DetailedComplaintFragment(title, name, desc, date, this)).commit();
    }
}