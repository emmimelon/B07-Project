package com.example.b07project.ui.events.admin_events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentAdminEventsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminEventsFragment extends Fragment implements AdminEventsInterface {

    private @NonNull FragmentAdminEventsBinding binding;
    static ArrayList<AdminEventsModel> adminEventsModels = new ArrayList<>();
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public AdminEventsFragment() {
        // empty constructor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.adminEventsRecyclerView);

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events");

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new AdminEventsAdapter(getActivity().getApplicationContext(), adminEventsModels, this));

        FloatingActionButton fab = root.findViewById(R.id.addEventButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminEventsModels.clear(); // Clear existing data to avoid duplicates
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String eName = snap.getKey();
                    Object eDateObj = snap.child("Date").getValue();
                    Object eDescriptionObj = snap.child("Description").getValue();
                    Object eLocationObj = snap.child("Location").getValue();
                    Object eParticipationLimitObj = snap.child("Participation Limit").getValue();

                    if (eName != null && eDateObj != null && eDescriptionObj != null && eLocationObj != null && eParticipationLimitObj != null) {
                        String eDate = eDateObj.toString();
                        String eDescription = eDescriptionObj.toString();
                        String eLocation = eLocationObj.toString();
                        Long eParticipationLimit = (Long) eParticipationLimitObj;

                        AdminEventsModel e = new AdminEventsModel(eName, eDate, eDescription, eLocation, eParticipationLimit);
                        adminEventsModels.add(e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    private void showDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_admin_events_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        EditText eventName = dialogView.findViewById(R.id.eventName);
        EditText eventDate = dialogView.findViewById(R.id.eventDate);
        EditText eventDescription = dialogView.findViewById(R.id.eventDescription);
        EditText eventLocation = dialogView.findViewById(R.id.eventLocation);
        EditText participationLimit = dialogView.findViewById(R.id.participationLimit);

        builder.setPositiveButton("Submit", null); // Set to null to override default behavior
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button submitButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean uncompleted = eventName.getText().toString().isEmpty() ||
                                eventDate.getText().toString().isEmpty() ||
                                eventDescription.getText().toString().isEmpty() ||
                                eventLocation.getText().toString().isEmpty() ||
                                participationLimit.getText().toString().isEmpty();

                        if (uncompleted) {
                            Toast.makeText(getActivity(), "Please fill in all required fields!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            submitData(eventName.getText().toString(),
                                    eventDate.getText().toString(),
                                    eventDescription.getText().toString(),
                                    eventLocation.getText().toString(),
                                    participationLimit.getText().toString());
                            Toast.makeText(getActivity(), "Congrats! Your event is scheduled successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss(); // Close the dialog only if all fields are filled
                        }
                    }
                });
            }
        });

        alertDialog.show();
    }

    @Override
    public void onItemClick(int position) {
        String name = adminEventsModels.get(position).getEventName();
        String location = adminEventsModels.get(position).getLocation();
        String date = adminEventsModels.get(position).getDate();
        String desc = adminEventsModels.get(position).getDescription();
        Long limit = adminEventsModels.get(position).getParticipationLimit();

        this.getParentFragmentManager().beginTransaction().hide(this).commit();
        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fragTrans.add(R.id.adminContainer, new DetailedAdminEventsFragment(name, location, date, desc, limit, this)).commit();
    }

    private void submitData(String name, String date, String description, String location, String limitStr) {
        DatabaseReference ref = db.getReference("Events").child(name);

        int limit = Integer.parseInt(limitStr); // Convert string to integer

        ref.child("Date").setValue(date);
        ref.child("Description").setValue(description);
        ref.child("Location").setValue(location);
        ref.child("Participation Limit").setValue(limit);

        ref.child("Registered Users").setValue(new HashMap<String, Object>());
        ref.child("Reviews").setValue(new HashMap<String, Object>());
    }
}