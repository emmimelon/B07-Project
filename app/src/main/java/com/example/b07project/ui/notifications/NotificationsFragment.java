package com.example.b07project.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.objects.Announcement;
import com.example.b07project.ui.announcements.view.AnnouncementsViewInterface;
import com.example.b07project.ui.announcements.view.DetailedAnnouncementsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationsFragment extends Fragment implements NotificationsViewInterface {

    private RecyclerView recyclerView;
    private List<Notification> notifications;
    private DatabaseReference ref, ref2;
    private FirebaseDatabase firebaseDatabase;
    private DateFormat dateFormat;

    public NotificationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = getActivity().findViewById(R.id.recycler_view_notifications);

        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = firebaseDatabase.getReference("Announcements");
        ref2 = firebaseDatabase.getReference("Events");

        notifications = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new NotificationAdapter(getActivity().getApplicationContext(), notifications, this));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot date : snapshot.getChildren()) {
                    if (date != null){
                        String[] dateString = date.getKey().split(",");
                        int year = Integer.parseInt(dateString[0]);
                        int month = Integer.parseInt(dateString[1]);
                        int day = Integer.parseInt(dateString[2]);
                        if (date.child("title").getValue() != null && date.child("description").getValue()
                        != null && date.child("creator").getValue() != null){
                            Notification n = new Notification("Announcement", date.child("title").getValue().toString(),
                                    R.drawable.megaphone, LocalDate.of(year, month, day), date.child("description").getValue().toString(), date.child("creator").getValue().toString(), date.getKey());
                            if (!notifications.contains(n)) {
                                notifications.add(n);
                                Collections.sort(notifications);
                                Collections.reverse(notifications);
                                recyclerView.getAdapter().notifyItemInserted(0);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot event : snapshot.getChildren()) {
                    if (event != null){
                        String[] dateString = event.child("Date").getValue().toString().split("\\.");
                        int year = Integer.parseInt(dateString[0]);
                        int month = Integer.parseInt(dateString[1]);
                        int day = Integer.parseInt(dateString[2]);
                        Notification n = new Notification("Event", event.getKey(), R.drawable.calendar, LocalDate.of(year, month, day), "", "", "");
                        if (!notifications.contains(n)) {
                            notifications.add(n);
                            Collections.sort(notifications);
                            Collections.reverse(notifications);
                            recyclerView.getAdapter().notifyItemInserted(0);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        String type = notifications.get(position).getType();
        if (type.equals("Announcement")) {
            String date = notifications.get(position).getFullDate();
            String title = notifications.get(position).getTitle();
            String desc = notifications.get(position).getDesc();
            String creator = notifications.get(position).getCreator();
            this.getParentFragmentManager().beginTransaction().hide(this).commit();
            FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
            fragTrans.add(R.id.container, new StudentDetailedAnnouncementsFragment(date, title, desc, creator, this)).commit();
        }
    }
}