package com.example.b07project.ui.events.userEvents;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentRateEventBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RateEventFragment extends Fragment {
    private int rating;
    private Button rateSubmit;
    private String title, userFullName;
    private TextView eventTitle;
    private ImageButton star1, star2, star3, star4, star5, backButton;
    private EditText feedback;
    private Fragment frag;
    private FragmentRateEventBinding binding;
    private Toast toast;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    public RateEventFragment(String title, Fragment frag){
        this.title = title;
        this.frag = frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rate_event, container, false);
        return v;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userFullName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        rating = 0;
        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        backButton = view.findViewById(R.id.rateBack);
        rateSubmit = view.findViewById(R.id.rateSubmit);
        feedback = view.findViewById(R.id.rateEventFeedback);
        eventTitle = view.findViewById((R.id.rateEventTitle));
        String strEventTitle = ("Rate: " + title);
        eventTitle.setText(strEventTitle);
        ImageButton[] stars = new ImageButton[5];
        int[] starIds = {R.id.star1, R.id.star2, R.id.star3, R.id.star4, R.id.star5};

        for (int i = 0; i < 5; i++) {
            stars[i] = view.findViewById(starIds[i]);

            final int starIndex = i + 1;
            stars[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rating = starIndex;
                    editStarColour(stars, starIndex);
                }
            });
        }
        rateSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String strFeedback = feedback.getText().toString();
                if (rating == 0 || strFeedback.equals("")){
                    toast = Toast.makeText(getActivity(), "Please fill out all details", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    ref = db.getReference("Events").child(title).child("Reviews").child(userFullName);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ref.child("comment").setValue(strFeedback);
                            ref.child("rating").setValue(rating);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    toast = Toast.makeText(getActivity(), "Success! Thanks for your feedback!", Toast.LENGTH_SHORT);
                    toast.show();
                    transition(frag);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(frag);
            }
        });
    }
    private void editStarColour(ImageButton[] stars, int rating) {
        for (int i = 0; i < rating; i++) {
            stars[i].setColorFilter(Color.argb(255, 253, 204, 13));
        }
        if (rating != 5) {
            for (int i = rating; i < stars.length; i++) {
                stars[i].setColorFilter(Color.argb(255, 51, 51, 51));
            }
        }
    }
    private void transition(Fragment frag) {
        frag.getParentFragmentManager().beginTransaction().show(frag).commit();
        this.getParentFragmentManager().beginTransaction().remove(this).commit();
    }
}
