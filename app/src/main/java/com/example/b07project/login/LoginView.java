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
import com.example.b07project.databinding.FragmentLoginBinding;

public class LoginView extends Fragment {

    private @NonNull FragmentLoginBinding binding;
    LoginPresenter presenter;
    EditText loginInputEmail, loginInputPassword;
    TextView output;
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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginInputEmail.getText().toString();
                String loginPassword = loginInputPassword.getText().toString();
                presenter.checkDB(email, loginPassword);
            }
        });

        return root;
    }
    void setResultText(String resultText){
        output = root.findViewById(R.id.output2);
        output.setText(resultText);
    }
    void enterStudentApp() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
    void enterAdminApp(){
        startActivity(new Intent(getActivity(), AdminActivity.class));
    }
}