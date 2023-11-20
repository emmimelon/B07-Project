package com.example.b07project.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.b07project.AdminActivity;
import com.example.b07project.MainActivity;
import com.example.b07project.R;
import com.example.b07project.WelcomeActivity;
import com.example.b07project.databinding.FragmentLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private @NonNull FragmentLoginBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    EditText inputUTORID, inputPassword;
    Button loginStudentBtn;
    Button loginAdminBtn;
    View root;
    boolean isFound;
    boolean correctPassword;
    boolean correctType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        ;
        root = binding.getRoot();

        // final TextView textView = binding.

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputUTORID = root.findViewById(R.id.loginUTORID);
        inputPassword = root.findViewById(R.id.loginPassword);
        loginStudentBtn = root.findViewById(R.id.loginStudentButton);
        loginAdminBtn = root.findViewById(R.id.loginAdminButton);
        loginStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = db.getReference("Users").child(inputUTORID.getText().toString());
                isFound = false;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isFound = snapshot.exists();
                        if (isFound) {
                            correctPassword = snapshot.child("Password").getValue().equals(inputPassword.getText().toString());
                            correctType = snapshot.child("Type").getValue().equals("Student");

                            if (correctPassword && correctType) {
                                Intent in2 = new Intent(getActivity(), MainActivity.class);
                                in2.putExtra("message2", "Successfully Logged In As Student");
                                startActivity(in2);
                            } else {
                                errorMessage();
                            }
                        } else {
                            setOutputText();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        loginAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = db.getReference("Users").child(inputUTORID.getText().toString());
                isFound = false;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isFound = snapshot.exists();
                        if (isFound) {
                            correctPassword = snapshot.child("Password").getValue().equals(inputPassword.getText().toString());
                            correctType = snapshot.child("Type").getValue().equals("Admin");
                            if (correctPassword && correctType) {
                                Intent in = new Intent(getActivity(), AdminActivity.class);
                                in.putExtra("message3", "Successfully Logged In As Admin3");
                                startActivity(in);
                            } else {
                                errorMessage();
                            }
                        } else {
                            setOutputText();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return root;
    }

    private void setOutputText() {
        TextView output = (TextView) root.findViewById(R.id.output2);
        output.setText("User not found");
    }

    private void errorMessage() {
        TextView output = (TextView) root.findViewById(R.id.output2);
        output.setText("Wrong password or wrong type");
    }
}