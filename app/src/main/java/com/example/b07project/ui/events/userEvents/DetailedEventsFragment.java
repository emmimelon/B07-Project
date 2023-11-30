package com.example.b07project.ui.events.userEvents;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.R;
import com.example.b07project.ui.announcements.create.CreateAnnouncementsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailedEventsFragment extends Fragment {

    private boolean hasRegistered, hasRated, inDB;
    String eventName, eventLocation, eventDate, eventDescription, userName;
    int eventLimit;
    ArrayList<String> eventRSVP;
    AppCompatImageButton eventBackButton;
    Button eventResgisterButton, rateEvent;
    Fragment frag;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public DetailedEventsFragment(String eventName, String eventLocation, String eventDate,
                                  String eventDescription, Fragment frag) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.frag = frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed_events, container, false);
        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = getActivity().findViewById(R.id.event_Name);
        name.setText(eventName);
        TextView loc = getActivity().findViewById(R.id.event_Location);
        loc.setText(eventLocation);
        TextView date = getActivity().findViewById(R.id.event_Date);
        date.setText(eventDate);
        TextView desc = getActivity().findViewById(R.id.event_Desc);
        desc.setText(eventDescription);
        rateEvent = view.findViewById(R.id.eventRateButton);
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        hasRegistered = checkRegistered();
        hasRated = checkRated();
        setRateBtnColour(rateEvent);
        rateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasRegistered) {
                    Toast.makeText(getContext(), "You must be registered to rate!",
                            Toast.LENGTH_SHORT).show();
                } else if (hasRated) {
                    Toast.makeText(getContext(), "You've already rated this event!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    transactionRateEvent();
                }
            }
        });

        eventResgisterButton = getActivity().findViewById(R.id.eventSignUp);
        eventResgisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = db.getReference("Events").child(eventName);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eventRSVP = new ArrayList<>();
                        for(DataSnapshot child : snapshot.child("Registered Users").getChildren()){
                            eventRSVP.add(child.getKey().toString());
                        }
                        eventLimit = Integer.parseInt(snapshot.child("Participation Limit").getValue().toString());

                        if (eventRSVP.contains(userName)) {
                            Toast.makeText(getContext(), userName + " is already registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if (eventRSVP.size() >= eventLimit){
                            Toast.makeText(getContext(), "Event full",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            ref.child("Registered Users").child(userName).setValue("true");
                            Toast.makeText(getContext(), "Successfully signed up",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                checkRegistered();
                setRateBtnColour(rateEvent);
            }
        });
        eventBackButton = getActivity().findViewById(R.id.eventBackButton);
        eventBackButton.setOnClickListener(new View.OnClickListener() {
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

    private boolean checkRegistered(){
        inDB = false;
        ref = db.getReference("Events").child(eventName).child("Registered Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot users: snapshot.getChildren()){
                    if (users.getKey().toString().equals(userName)){
                        inDB = false;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (inDB) {
            return true;
        }
        return false;
    }
    private boolean checkRated(){
        inDB = false;
        ref = db.getReference("Events").child(eventName).child("Reviews");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot users: snapshot.getChildren()){
                    if (users.getKey().toString().equals(userName)){
                        inDB = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (inDB) {
            return true;
        }
        return false;
    }
    private void setRateBtnColour(Button rateBtn){
        if (hasRated || !hasRegistered){
            rateBtn.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 51, 51, 51)));
        } else {
            Toast.makeText(getContext(), "AAAAAAAAA! " + hasRated + " "  + hasRegistered,
                    Toast.LENGTH_SHORT).show();
            rateBtn.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 37, 53, 74)));
        }
    }
    private void enableBottomBar(boolean enable) {
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        for (int i = 0; i < navView.getMenu().size(); i++) {
            navView.getMenu().getItem(i).setEnabled(enable);
        }
    }
    private void transactionRateEvent() {
        this.getParentFragmentManager().beginTransaction().hide(this).commit();
        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fragTrans.add(R.id.container, new RateEventFragment(eventName, this)).commit();
    }
}