package com.example.b07project.ui.complaints;

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
import com.example.b07project.databinding.FragmentComplaintsBinding;
import com.example.b07project.objects.Complaint;
import com.example.b07project.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ComplaintsFragment extends Fragment {

    private FragmentComplaintsBinding binding;
    private DatabaseReference ref;
    private FirebaseDatabase firebaseDatabase;
    EditText inputComplaintTitle, inputComplaintDescription;
    Button btnSubmitComplaint;

    boolean justAdded = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentComplaintsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputComplaintTitle = root.findViewById(R.id.editTextComplaintTitle);
        inputComplaintDescription = root.findViewById(R.id.editTextComplaintDescription);
        btnSubmitComplaint = root.findViewById(R.id.buttonSubmitComplaint);
        btnSubmitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justAdded = false;
                String inputTitle = inputComplaintTitle.getText().toString();
                String inputDescription = inputComplaintDescription.getText().toString();
                Complaint complaint = new Complaint(id, inputTitle, inputDescription);
                if (inputTitle.equals("") || inputDescription.equals("")){
                    Toast.makeText(getActivity(), "Please fill in your complaint details", Toast.LENGTH_SHORT).show();
                }
                else {
                    ref = firebaseDatabase.getReference("Complaints").child(id).child(inputTitle);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                complaint.writeNewComplaint();
                                justAdded = true;
                                Toast.makeText(getActivity(), "Submitted Complaint!", Toast.LENGTH_SHORT).show();
                            }
                            else if (justAdded == false){
                                Toast.makeText(getActivity(), "You already made an unresolved complaint with this title!", Toast.LENGTH_SHORT).show();
                            }
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