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
import androidx.fragment.app.FragmentTransaction;

import com.example.b07project.AdminActivity;
import com.example.b07project.MainActivity;
import com.example.b07project.R;
import com.example.b07project.databinding.FragmentLoginBinding;
import com.example.b07project.signup.SignUpFragment;

public class LoginView extends Fragment {

    private @NonNull FragmentLoginBinding binding;
    LoginPresenter presenter;
    EditText loginInputEmail, loginInputPassword;
    TextView output, signUpInstead;
    Button loginBtn;
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        presenter = new LoginPresenter(this, new LoginModel());

        loginInputEmail = root.findViewById(R.id.loginEmail);
        loginInputPassword = root.findViewById(R.id.loginPassword);
        loginBtn = root.findViewById(R.id.loginButton);
        loginBtn.setVisibility(View.VISIBLE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.checkDB();
            }
        });
        signUpInstead = root.findViewById(R.id.signUPInstead);
        signUpInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment signupFragment = new SignUpFragment();
                loginBtn.setVisibility(View.GONE);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.loginLayout, signupFragment).commit();
            }
        });
        return root;
    }
    public void setResultText(String resultText){
        output = root.findViewById(R.id.output2);
        output.setText(resultText);
    }
    public void enterStudentApp() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
    public void enterAdminApp(){
        startActivity(new Intent(getActivity(), AdminActivity.class));
    }
    public String getEmail(){
        String email = loginInputEmail.getText().toString();
        return email;
    }
    public String getPassword(){
        String loginPassword = loginInputPassword.getText().toString();
        return loginPassword;
    }

}