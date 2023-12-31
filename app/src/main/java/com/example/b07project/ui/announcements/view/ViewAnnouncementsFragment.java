package com.example.b07project.ui.announcements.view;

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
import com.example.b07project.objects.Announcement;
import com.example.b07project.ui.announcements.create.CreateAnnouncementsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAnnouncementsFragment extends Fragment implements AnnouncementsViewInterface{
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    List<Announcement> announcements;
    FloatingActionButton floatingActionButton;
    public ViewAnnouncementsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_view_announcements, container, false);
        recyclerView = view.findViewById(R.id.announcementrecyclerview);
        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = firebaseDatabase.getReference("Announcements");
        announcements = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        floatingActionButton = view.findViewById(R.id.createAnnouncementButton);
        recyclerView.setAdapter(new AnnouncementsAdapter(getActivity().getApplicationContext(), announcements, this));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot date: snapshot.getChildren()){
                    if (date.getChildrenCount() == 3) {
                        String title = date.child("title").getValue().toString();
                        String description = date.child("description").getValue().toString();
                        String creator = date.child("creator").getValue().toString();
                        Announcement a = new Announcement(date.getKey(), title, description, creator, R.drawable.ic_notifications_black_24dp);
                        Boolean inList = false;
                        for (int i = 0; i < announcements.size(); i++) {
                            if (announcements.get(i).getDate().equals(date.getKey().toString())) {
                                inList = true;
                                break;
                            }
                        }
                        if (!inList) {
                            announcements.add(0, a);
                            recyclerView.getAdapter().notifyItemInserted(0);
                        }
//                        if (!announcements.contains(a)) {
//                            announcements.add(a);
//                            recyclerView.getAdapter().notifyItemInserted(0);
//                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionCreateAnnouncements();
            }
        });
        return view;
    }

    @Override
    public void onItemClick(int position) {
        String date = announcements.get(position).getDate();
        String title = announcements.get(position).getTitle();
        String desc = announcements.get(position).getDescription();
        String creator = announcements.get(position).getCreator();
        this.getParentFragmentManager().beginTransaction().hide(this).commit();
        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fragTrans.add(R.id.adminContainer, new DetailedAnnouncementsFragment(date, title, desc, creator, this)).commit();
    }

    @Override
    public void onFabClick(View view) {
//        this.getParentFragmentManager().beginTransaction().hide(this).commit();
//        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
//        fragTrans.add(R.id.adminContainer, new CreateAnnouncementsFragment(this)).commit();
    }

    private void transactionCreateAnnouncements() {
        this.getParentFragmentManager().beginTransaction().hide(this).commit();
        FragmentTransaction fragTrans = getActivity().getSupportFragmentManager().beginTransaction();
        fragTrans.add(R.id.adminContainer, new CreateAnnouncementsFragment(this)).commit();
    }

}
