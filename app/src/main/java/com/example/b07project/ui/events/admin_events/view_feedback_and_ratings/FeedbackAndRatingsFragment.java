package com.example.b07project.ui.events.admin_events.view_feedback_and_ratings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class FeedbackAndRatingsFragment extends Fragment {
    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<FeedbackModel> feedbackList;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String eventName, eventDate, eventLocation, eventDescription;
    private Long eventParticipationLimit;
    private Button goBackButton;
    private Fragment frag;
    private TextView averageRating;

    public FeedbackAndRatingsFragment(Fragment frag) {
        this.frag = frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback_and_ratings, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        feedbackList = new ArrayList<>();
        adapter = new FeedbackAdapter(feedbackList);
        recyclerView.setAdapter(adapter);

        // Retrieve event name from bundle
        if (getArguments() != null) {
            eventName = getArguments().getString("event_name");
            eventLocation = getArguments().getString("event_location");
            eventDate = getArguments().getString("event_date");
            eventDescription = getArguments().getString("event_description");
            eventParticipationLimit = getArguments().getLong("event_participation_limit");
        }

        averageRating = view.findViewById(R.id.textViewAverageRating);

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        ref = db.getReference("Events").child(eventName).child("Reviews");

        fetchFeedback();

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

    private void fetchFeedback() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double ratingSum = 0;
                int ratingCount = 0;
                feedbackList.clear();
                if (!dataSnapshot.exists() || !dataSnapshot.hasChildren()) {
                    // Show toast if no feedback is found
                    Toast.makeText(getContext(), "Oh No! Nobody commented your event :(", Toast.LENGTH_LONG).show();
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        String userName = snap.getKey();
                        Object comment = snap.child("comment").getValue();
                        Object rating = snap.child("rating").getValue();

                        if (comment != null && rating != null) {
                            String userComment = comment.toString();
                            Long userRating = (Long) rating;
                            ratingSum += userRating.doubleValue();
                            ratingCount++;
                            FeedbackModel feedback = new FeedbackModel(userName, userComment, userRating);
                            feedbackList.add(feedback);
                        }
                    }
                    if (ratingCount > 0) {
                        double ratingAverage = (ratingSum / ratingCount);
                        averageRating.setText(String.format("Average Rating : %.2f", ratingAverage));
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