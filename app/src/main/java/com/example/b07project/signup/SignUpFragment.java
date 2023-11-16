package com.example.b07project.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentSignupBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpFragment extends Fragment {

    private FragmentSignupBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    EditText inputUTORID, inputPassword, inputName;
    Button createStudentButton;
    View root;
    boolean isFound;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // final TextView textView = binding.

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputUTORID = root.findViewById(R.id.enterUTORID);
        inputPassword = root.findViewById(R.id.enterPassword);
        inputName = root.findViewById(R.id.enterName);
        createStudentButton = root.findViewById(R.id.studentButton);
        createStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = db.getReference("Users").child(inputUTORID.getText().toString());
                isFound = false;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isFound = snapshot.exists();
                        setOutputText();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return root;
        }

    private void setOutputText()
    {
        TextView output = (TextView) root.findViewById(R.id.output);
        if(isFound)
        {
            output.setText("Found it!");
        }
        else
        {
            output.setText("Didn't find it!");
        }
    }
}