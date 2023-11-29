package com.example.b07project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.b07project.login.LoginModel;
import com.example.b07project.login.LoginPresenter;
import com.example.b07project.login.LoginView;

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    LoginView view;
    @Mock
    LoginModel model;
    LoginPresenter presenter = new LoginPresenter(view, model);

    @Test
    public void checkEmptyEmail(){
        when(view.getPassword()).thenReturn("");
        presenter.checkDB();
        verify(view).setResultText("Please fill in your user details.");
    }
    @Test
    public void checkEmptyPassword(){
        when(view.getEmail()).thenReturn("");
        presenter.checkDB();
        verify(view).setResultText("Please fill in your user details.");
    }
    @Test
    public void checkInvalidUserType(){
        String userType = "invalidUserType";
        presenter.goTo(userType);
        verify(view).setResultText("Invalid user type.");
    }

    @Test
    public void testEmailNotFound(){
        when(view.getEmail()).thenReturn("notanemail");
        when(view.getPassword()).thenReturn("studentpassword");
        presenter.checkDB();
        verify(view).setResultText("Please fill in your user details.");
    }
    @Test
    public void testPasswordNotFound(){
        when(view.getEmail()).thenReturn("genericstudent@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("notapassword");
        presenter.checkDB();
        verify(presenter).setViewText();
    }
    @Test
    public void testAdminPresenterLogin(){
        when(view.getEmail()).thenReturn("genericadmin@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("adminpassword");
        presenter.checkDB();
        verify(presenter).goTo("Admin");
    }
    @Test
    public void testAdminViewLogin(){
        when(view.getEmail()).thenReturn("genericadmin@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("adminpassword");
        presenter.checkDB();
        verify(view).enterAdminApp();
    }
    @Test
    public void testStudentPresenterLogin(){
        when(view.getEmail()).thenReturn("genericstudent@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("studentpassword");
        presenter.checkDB();
        verify(presenter).goTo("Student");
    }
    @Test
    public void testStudentViewLogin(){
        when(view.getEmail()).thenReturn("genericstudent@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("studentpassword");
        presenter.checkDB();
        verify(view).enterStudentApp();
    }

}