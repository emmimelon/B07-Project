package com.example.b07project.ui.events.admin_events.view_users;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.ui.events.admin_events.DetailedAdminEventsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<UserModel> userList;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String eventName, eventDate, eventLocation, eventDescription;
    private Long eventParticipationLimit;
    private Button goBackButton;
    private Fragment frag;

    public RegisteredUsersFragment(Fragment frag) {
        this.frag = frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registered_users, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);

        // Retrieve event name from bundle
        if (getArguments() != null) {
            eventName = getArguments().getString("event_name");
            eventLocation = getArguments().getString("event_location");
            eventDate = getArguments().getString("event_date");
            eventDescription = getArguments().getString("event_description");
            eventParticipationLimit = getArguments().getLong("event_participation_limit");
        }

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events").child(eventName).child("Registered Users");

        fetchUsers();

        goBackButton = view.findViewById(R.id.buttonGoBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.adminContainer, new DetailedAdminEventsFragment(eventName,
                        eventLocation, eventDate, eventDescription, eventParticipationLimit, frag));
                transaction.commit();
            }
        });

        return view;
    }

    private void fetchUsers() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                if (!dataSnapshot.exists() || !dataSnapshot.hasChildren()) {
                    // Show toast if no users are found
                    Toast.makeText(getContext(), "Oh No! Nobody registered for your event :(", Toast.LENGTH_LONG).show();
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String userName = snap.getKey();
                        UserModel user = new UserModel(userName);
                        userList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}