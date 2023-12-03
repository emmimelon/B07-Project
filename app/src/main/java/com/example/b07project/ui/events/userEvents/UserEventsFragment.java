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

    static ArrayList<EventsModel> eventsModels = new ArrayList<>();
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

        View view = inflater.inflate(R.layout.fragment_user_events, container, false);
        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events");

        RecyclerView recyclerView = view.findViewById(R.id.studentRecyclerView);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (snap.exists() && snap.child("Location").getValue() != null &&
                            snap.child("Date").getValue() != null && snap.child("Description").getValue() != null){
                        String eName = snap.getKey().toString();
                        String eLocation = snap.child("Location").getValue().toString();
                        String eDate = snap.child("Date").getValue().toString();
                        String eDescription = snap.child("Description").getValue().toString();

                        EventsModel e = new EventsModel(eName, eLocation, eDate, eDescription);
                        if (!eventsModels.contains(e)) {
                            int index = 0;
                            boolean notPlaced = true;
                            for(int i = 0; i < eventsModels.size() && notPlaced; i++){
                                int eDateYear = Integer.parseInt(eDate.substring(0,4));
                                int year = Integer.parseInt(eventsModels.get(i).getEventDate().
                                        substring(0,4));
                                int eDateMonth = Integer.parseInt(eDate.substring(5, 7));
                                int month = Integer.parseInt(eventsModels.get(i).getEventDate().
                                        substring(5, 7));
                                if (eDateYear > year){
                                    index = i;
                                    notPlaced = false;
                                }

                                else if (eDateYear == year && eDateMonth > month){
                                    index = i;
                                    notPlaced = false;
                                }
                                else if (eDateYear == year && eDateMonth == month && Integer.parseInt(
                                        eDate.substring(8)) > Integer.parseInt(
                                        eventsModels.get(i).getEventDate().substring(8))){
                                    index = i;
                                    notPlaced = false;
                                }
                                else{
                                    index++;
                                }
                            }
                            eventsModels.add(index, e);
                            recyclerView.getAdapter().notifyItemInserted(0);
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new Event_recyclerViewAdapter(getActivity().getApplicationContext(), eventsModels, this));
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

}
