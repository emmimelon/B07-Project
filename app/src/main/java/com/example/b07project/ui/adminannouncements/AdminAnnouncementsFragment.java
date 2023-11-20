package com.example.b07project.ui.adminannouncements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentAdminAnnouncementsBinding;
import com.example.b07project.databinding.FragmentComplaintsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class AdminAnnouncementsFragment extends Fragment {

    private FragmentAdminAnnouncementsBinding binding;

    private DatabaseReference ref;
    private FirebaseDatabase firebaseDatabase;
    EditText inputAnnouncementsTitle, inputAnnouncementDescription;
    private String utorid;
    Button btnSubmitAnnouncement;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminAnnouncementsViewModel adminAnnouncementsViewModel =
                new ViewModelProvider(this).get(AdminAnnouncementsViewModel.class);

        binding = FragmentAdminAnnouncementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputAnnouncementsTitle = root.findViewById(R.id.editAnnouncementTitle);
        inputAnnouncementDescription = root.findViewById(R.id.editAnnouncementDescription);
        btnSubmitAnnouncement = root.findViewById(R.id.buttonSubmitAnnouncement);

        btnSubmitAnnouncement.setOnClickListener(new View.OnClickListener(){

            @Override
                    public void onClick(View v) {
                if (inputAnnouncementsTitle.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getActivity(), "Please enter a title", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    ref = firebaseDatabase.getReference("Announcements").child(inputAnnouncementsTitle.getText().toString());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ref.setValue(inputAnnouncementDescription.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}