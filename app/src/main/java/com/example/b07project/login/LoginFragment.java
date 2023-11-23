package com.example.b07project.login;

import android.app.Activity;
import android.content.Intent;
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

import com.example.b07project.AdminActivity;
import com.example.b07project.MainActivity;
import com.example.b07project.R;
import com.example.b07project.WelcomeActivity;
import com.example.b07project.databinding.FragmentLoginBinding;
import com.example.b07project.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private @NonNull FragmentLoginBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    EditText loginInputEmail, loginInputPassword;
    Button loginStudentBtn;
    Button loginAdminBtn;
    View root;
    boolean isFound;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        loginInputEmail = root.findViewById(R.id.loginEmail);
        loginInputPassword = root.findViewById(R.id.loginPassword);
        loginStudentBtn = root.findViewById(R.id.loginStudentButton);
        loginAdminBtn = root.findViewById(R.id.loginAdminButton);

        loginStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginInputEmail.getText().toString();
                String loginPassword = loginInputPassword.getText().toString();
                isFound = false;
                mAuth.signInWithEmailAndPassword(email, loginPassword)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    ref = db.getReference("Users").child(user.getUid());
                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            isFound = snapshot.child("Type").getValue().equals("Student");
                                            if (isFound == true){
                                                startActivity(new Intent(getActivity(), MainActivity.class));
                                            }
                                            else{
                                                setOutputText();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                else {
                                    errorMessage();
                                }
                            }
                        });

            }
        });

        loginAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginInputEmail.getText().toString();
                String loginPassword = loginInputPassword.getText().toString();
                isFound = false;
                mAuth.signInWithEmailAndPassword(email, loginPassword)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    ref = db.getReference("Users").child(user.getUid());
                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            isFound = snapshot.child("Type").getValue().equals("Admin");
                                            if (isFound == true){
                                                startActivity(new Intent(getActivity(), AdminActivity.class));
                                            }
                                            else{
                                                setOutputText();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                                else {
                                    errorMessage();
                                }
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
        output.setText("Incorrect password or user type.");
    }
}