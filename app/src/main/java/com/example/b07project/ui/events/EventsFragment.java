package com.example.b07project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;

import com.example.b07project.databinding.FragmentEventsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    private @NonNull FragmentEventsBinding binding;
    ArrayList<EventsModel> eventsModels = new ArrayList<>();
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.myRecyclerView);
        setUpEvents();

        Event_recyclerViewAdapter adapter = new Event_recyclerViewAdapter(this.getActivity(), eventsModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpEvents(){
        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren())
                {
                    // HAVING A NULL REFERENCE ISSUE
                    // String eName = snap.getValue().toString();
                    //String eLocation = snap.child("Location").getValue().toString();
                    //String eDate = snap.child("Date").getValue().toString();
                    //eventsModels.add(new EventsModel(eName,eName,eDate));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}