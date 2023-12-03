package com.example.b07project.ui.complaints.admin;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.b07project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailedComplaintFragment extends Fragment {

    String complaintTitle, complaintUsername, complaintDescription, complaintDate;
    Button complaintBackButton;
    Fragment frag;

    public DetailedComplaintFragment() {
        // Required empty public constructor
    }

    public DetailedComplaintFragment(String complaintTitle, String complaintUsername, String complaintDescription, String complaintDate, Fragment frag) {
        this.complaintTitle = complaintTitle;
        this.complaintUsername = complaintUsername;
        this.complaintDescription = complaintDescription;
        this.complaintDate = complaintDate;
        this.frag = frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed_complaint, container, false);
        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = getActivity().findViewById(R.id.complaintTitle);
        title.setText(complaintTitle);
        TextView username = getActivity().findViewById(R.id.complaintUsername);
        username.setText(complaintUsername);
        TextView description = getActivity().findViewById(R.id.complaintDescription);
        description.setText(complaintDescription);
        TextView date = getActivity().findViewById(R.id.complaintDate);
        date.setText(complaintDate);
        complaintBackButton = getActivity().findViewById(R.id.complaint_back_button);
        complaintBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(frag);
            }
        });
        enableBottomBar(false);
    }

    private void transition(Fragment frag) {
        enableBottomBar(true);
        frag.getParentFragmentManager().beginTransaction().show(frag).commit();
        this.getParentFragmentManager().beginTransaction().remove(this).commit();
    }

    private void enableBottomBar(boolean enable) {
        BottomNavigationView adminNavView = getActivity().findViewById(R.id.admin_nav_view);
        if (enable){
            adminNavView.setVisibility(View.VISIBLE);
        } else if (!enable) {
            adminNavView.setVisibility(View.GONE);
        }
        for (int i = 0; i < adminNavView.getMenu().size(); i++) {
            adminNavView.getMenu().getItem(i).setEnabled(enable);
        }
    }
}