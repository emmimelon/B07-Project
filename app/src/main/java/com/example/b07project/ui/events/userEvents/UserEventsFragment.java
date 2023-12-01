package com.example.b07project.ui.events.userEvents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentUserEventsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserEventsFragment extends Fragment implements EventRVInterface {

    static ArrayList<EventsModel> eventsModels;
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public UserEventsFragment() {
        // empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        eventsModels = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_user_events, container, false);
        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events");

        RecyclerView recyclerView = view.findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new Event_recyclerViewAdapter(getActivity().getApplicationContext(), eventsModels, this));
        showRecyclerView(false, recyclerView);

        CalendarView calView = view.findViewById(R.id.myCalendarView);

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                while (eventsModels.size() != 0)
                {
                    eventsModels.remove(0);
                    recyclerView.getAdapter().notifyItemRemoved(0);
                }
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snap : snapshot.getChildren()) {
                            String eDate = snap.child("Date").getValue().toString();
                            String givenDate = year + "."+ (month + 1) + "." + dayOfMonth;
                            if (eDate.equals(givenDate)){
                                String eName = snap.getKey().toString();
                                String eLocation = snap.child("Location").getValue().toString();
                                String eDescription = snap.child("Description").getValue().toString();

                                EventsModel e = new EventsModel(eName, eLocation, eDate, eDescription);
                                if (!eventsModels.contains(e)) {
                                    eventsModels.add(e);
                                    recyclerView.getAdapter().notifyItemInserted(0);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                showRecyclerView(true, recyclerView);

            }
        });
        return view;
    }

    @Override
    public void onItemClick(int position) {
        String name = eventsModels.get(position).getEventName();
        String location = eventsModels.get(position).getEventLocation();
        String date = eventsModels.get(position).getEventDate();
        String desc = eventsModels.get(position).getEventDescription();

        this.getParentFragmentManager().beginTransaction().hide(this).commit();
        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fragTrans.add(R.id.container, new DetailedEventsFragment(name, location, date, desc, this)).commit();


    }

    private void showRecyclerView(boolean show, RecyclerView view) {
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}

    /*int index = 0;
    boolean notPlaced = true;
                                    for(int i = 0; i < eventsModels.size() && notPlaced; i++){
        if (Integer.parseInt(eDate.substring(0,4)) > Integer.parseInt(
        eventsModels.get(i).getEventDate().substring(0,4))){
        index = i;
        notPlaced = false;
        }
        else if (Integer.parseInt(eDate.substring(5, 7)) > Integer.parseInt(
        eventsModels.get(i).getEventDate().substring(5, 7))){
        index = i;
        notPlaced = false;
        }
        else if (Integer.parseInt(eDate.substring(8)) > Integer.parseInt(
        eventsModels.get(i).getEventDate().substring(8))){
        index = i;
        notPlaced = false;
        }
        else{
        index++;
        }
        */