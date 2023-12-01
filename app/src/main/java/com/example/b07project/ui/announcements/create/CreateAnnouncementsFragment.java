package com.example.b07project.ui.announcements.create;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentAdminAnnouncementsBinding;
import com.example.b07project.ui.notifications.PushNotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateAnnouncementsFragment extends Fragment {

    EditText inputAnnouncementsTitle, inputAnnouncementDescription;
    Button btnSubmitAnnouncement;
    ImageButton backButton;
    private FragmentAdminAnnouncementsBinding binding;
    private DatabaseReference ref;
    private FirebaseDatabase firebaseDatabase;
    private Toast toast;
    private Fragment frag;

    public CreateAnnouncementsFragment(Fragment frag){
        this.frag = frag;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminAnnouncementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String userFullName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputAnnouncementsTitle = root.findViewById(R.id.editAnnouncementTitle);
        inputAnnouncementDescription = root.findViewById(R.id.editAnnouncementDescription);
        btnSubmitAnnouncement = root.findViewById(R.id.buttonSubmitAnnouncement);
        backButton = root.findViewById((R.id.createAnnouncementBackButton));

        btnSubmitAnnouncement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String inputTitle = inputAnnouncementsTitle.getText().toString();
                String inputDescription = inputAnnouncementDescription.getText().toString();
                if (inputTitle.equals("")) {
                    toast = Toast.makeText(getActivity(), "Please enter a title", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    //Date format: year,month,day,hour,minute,second
                    DateFormat df = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    ref = firebaseDatabase.getReference("Announcements").child(date);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ref.child("title").setValue(inputTitle);
                            ref.child("description").setValue(inputDescription);
                            ref.child("creator").setValue(userFullName);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                    toast = Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT);
                    toast.show();
                    PushNotificationService.sendPushNotification("New Announcement!", inputAnnouncementsTitle.getText().toString(), getActivity());
                    inputAnnouncementsTitle.setText("");
                    inputAnnouncementDescription.setText("");
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(frag);
            }
        });
        showBottomBar(false);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void transition(Fragment frag) {
        showBottomBar(true);
        frag.getParentFragmentManager().beginTransaction().show(frag).commit();
        this.getParentFragmentManager().beginTransaction().remove(this).commit();
    }
    private void showBottomBar(boolean show) {
        BottomNavigationView adminNavView = getActivity().findViewById(R.id.admin_nav_view);
        if (show) {
            adminNavView.setVisibility(View.VISIBLE);
        } else {
            adminNavView.setVisibility(View.GONE);
        }
    }
}