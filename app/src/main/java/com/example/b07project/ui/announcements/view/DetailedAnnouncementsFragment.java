package com.example.b07project.ui.announcements.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.TextView;

public class DetailedAnnouncementsFragment extends Fragment {
    String date, title, description, creator;
    TextView titleText, creatorText, dateText, descriptionText;
    Button announcementBackButton;
    Fragment frag;
    public DetailedAnnouncementsFragment(){
        // Required empty public constructor
    }
    public DetailedAnnouncementsFragment(String date, String title, String description, String creator, Fragment frag) {
        this.date = FormatDate.formatDate(date);
        this.title = title;
        this.description = description;
        this.creator = ("By: " + creator);
        this.frag = frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_detailed_announcements, container, false);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleText = getActivity().findViewById(R.id.adminAnnouncementTitle);
        titleText.setText(title);
        creatorText = getActivity().findViewById(R.id.adminAnnouncementCreator);
        creatorText.setText(creator);
        dateText = getActivity().findViewById(R.id.adminAnnouncementDate);
        dateText.setText(date);
        descriptionText = getActivity().findViewById(R.id.adminAnnouncementDescription);
        descriptionText.setText(title);
        announcementBackButton = getActivity().findViewById(R.id.admin_announcements_back_button);
        announcementBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(frag);
            }
        });
        showBottomBar(false);
    }


    private void transition(Fragment frag) {
        showBottomBar(true);
        frag.getParentFragmentManager().beginTransaction().show(frag).commit();
        this.getParentFragmentManager().beginTransaction().remove(this).commit();
    }

    private void showBottomBar(boolean show) {
        BottomNavigationView adminNavView = getActivity().findViewById(R.id.admin_nav_view);
        if (show) {
            adminNavView.setVisibility(View.VISIBLE);
        } else {
            adminNavView.setVisibility(View.GONE);
        }
    }
}
