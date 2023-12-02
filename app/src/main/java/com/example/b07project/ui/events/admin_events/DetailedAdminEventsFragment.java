package com.example.b07project.ui.events.admin_events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07project.R;
import com.example.b07project.ui.events.admin_events.view_users.RegisteredUsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailedAdminEventsFragment extends Fragment {
    String eventName;
    String eventDate;
    String eventDescription;
    String eventLocation;
    Long eventParticipationLimit;
    AppCompatImageButton eventBackButton;
    Button seeRegisteredUsers;
    Button seeRatings;
    Fragment frag;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public DetailedAdminEventsFragment(String eventName, String eventLocation, String eventDate,
                                       String eventDescription, Long eventParticipationLimit,
                                       Fragment frag) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.eventParticipationLimit = eventParticipationLimit;
        this.frag = frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed_admin_events, container, false);
        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = getActivity().findViewById(R.id.eventName);
        name.setText(eventName);
        TextView location = getActivity().findViewById(R.id.eventLocation);
        location.setText(eventLocation);
        TextView date = getActivity().findViewById(R.id.eventDate);
        date.setText(eventDate);
        TextView description = getActivity().findViewById(R.id.eventDesc);
        description.setText(eventDescription);
        TextView participationLimit = getActivity().findViewById(R.id.eventLimit);
        participationLimit.setText(String.valueOf(Math.toIntExact(eventParticipationLimit)));

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events").child(eventName);

        eventBackButton = getActivity().findViewById(R.id.eventBackButton);
        eventBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(frag);
            }
        });

        seeRegisteredUsers = getActivity().findViewById(R.id.eventUsers);
        seeRegisteredUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RegisteredUsersFragment registeredUsersFragment = new RegisteredUsersFragment(frag);

                Bundle bundle = new Bundle();
                bundle.putString("event_name", eventName);
                bundle.putString("event_ocation", eventLocation);
                bundle.putString("event_date", eventDate);
                bundle.putString("event_description", eventDescription);
                bundle.putLong("event_participation_limit", eventParticipationLimit);
                registeredUsersFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.adminContainer, registeredUsersFragment).commit();
            }
        });

        seeRatings = getActivity().findViewById(R.id.eventRatings);
        seeRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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