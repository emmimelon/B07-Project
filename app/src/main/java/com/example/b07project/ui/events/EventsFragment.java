package com.example.b07project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentEventsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventsFragment extends Fragment {

    private FragmentEventsBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private EditText eventName, eventDate, eventDescription, eventLocation, participationLimit;
    private Button submitEventButton;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        eventName = root.findViewById(R.id.eventName);
        eventDate = root.findViewById(R.id.eventDate);
        eventDescription = root.findViewById(R.id.eventDescription);
        eventLocation = root.findViewById(R.id.eventLocation);
        participationLimit = root.findViewById(R.id.participationLimit);
        submitEventButton = root.findViewById(R.id.submitEventButton);

        submitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = db.getInstance().getReference("Events").child(eventName.getText().toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        submitData();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(getActivity(), "Congrats! Your event is scheduled successfully", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void submitData() {
        String date = eventDate.getText().toString();
        String description = eventDescription.getText().toString();
        String location = eventLocation.getText().toString();
        int limit = Integer.parseInt(participationLimit.getText().toString());

        ref.child("Date").setValue(date);
        ref.child("Description").setValue(description);
        ref.child("Location").setValue(location);
        ref.child("Participation Limit").setValue(limit);
    }
}