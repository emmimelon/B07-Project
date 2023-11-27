package com.example.b07project.login;

public class LoginPresenter {
    LoginModel model;
    LoginView view;
    public LoginPresenter(LoginView view, LoginModel model){
        this.model = model;
        this.view = view;
    }
    public void checkDB(String email, String password) {
        if (email.equals("") || password.equals("")){
            view.setResultText("Please fill in your user details.");
        }
        else {
            model.signIn(this, email, password);
        }
    }
    public void setViewText() {
        view.setResultText("This user does not exist.");
    }
    public void goTo(String userType){
        if (userType.equals("Student")){
            view.enterStudentApp();
        }
        else if (userType.equals("Admin")){
            view.enterAdminApp();
        }
        else {
            view.setResultText("Invalid user type.");
        }
    }
}