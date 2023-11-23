package com.example.b07project.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.b07project.databinding.FragmentSignupBinding;
import com.example.b07project.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpFragment extends Fragment {
    private static final String TAG = "MyApp/";
    private FragmentSignupBinding binding;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    EditText inputPassword, inputName, inputEmail;
    Button createStudentButton;
    Button createAdminButton;
    View root;
    boolean isFound;
    boolean isStudent;
    boolean isAdmin;
    private boolean justCreated = false;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

        db = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputPassword = root.findViewById(R.id.enterPassword);
        inputName = root.findViewById(R.id.enterName);
        inputEmail = root.findViewById(R.id.enterEmail);

        createStudentButton = root.findViewById(R.id.studentButton);
        createAdminButton = root.findViewById(R.id.adminButton);
        createStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = inputPassword.getText().toString();
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                isFound = false;
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    justCreated = true;
                                    Log.d(TAG, "createUserWithEmail: success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).setPhotoUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/b/b5/Windows_10_Default_Profile_Picture.svg")).build();
                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Log.d(TAG, "User profile updated.");
                                                        User ourUser = new User(user, "Student");
                                                        ourUser.addNewUser();
                                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                                    }
                                                }
                                            });

                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed or user already exists.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        createAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = inputPassword.getText().toString();
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                isFound = false;
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    justCreated = true;
                                    Log.d(TAG, "createUserWithEmail: success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).setPhotoUri(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/b/b5/Windows_10_Default_Profile_Picture.svg")).build();
                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Log.d(TAG, "User profile updated.");
                                                        User ourUser = new User(user, "Admin");
                                                        ourUser.addNewUser();
                                                        startActivity(new Intent(getActivity(), AdminActivity.class));
                                                    }
                                                }
                                            });

                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed or user already exists.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return root;
    }

    private void setOutputTextStudent()
    {
        TextView output = (TextView) root.findViewById(R.id.output);
        output.setText( "User is already a student");
    }
    private void setOutputTextAdmin()
    {
        TextView output = (TextView) root.findViewById(R.id.output);
        output.setText("User is already an admin");
    }
}
