package com.example.b07project.ui.events.userEvents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentUserEventsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserEventsFragment extends Fragment implements EventRVInterface {

    private @NonNull FragmentUserEventsBinding binding;
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

        binding = FragmentUserEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.studentRecyclerView);

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events");

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new Event_recyclerViewAdapter(getActivity().getApplicationContext(), eventsModels, this));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String eName = snap.getKey().toString();
                    String eLocation = snap.child("Location").getValue().toString();
                    String eDate = snap.child("Date").getValue().toString();
                    String eDescription = snap.child("Description").getValue().toString();

                    EventsModel e = new EventsModel(eName, eLocation, eDate, eDescription);
                    if (!eventsModels.contains(e)) {
                        eventsModels.add(e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
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