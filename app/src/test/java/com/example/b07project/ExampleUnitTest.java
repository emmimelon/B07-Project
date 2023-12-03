package com.example.b07project;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
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
    LoginPresenter presenter;


    @Before
    public void setup(){
        presenter = new LoginPresenter(view, model);
    }
    @Test
    public void checkEmptyEmail(){
        when(view.getEmail()).thenReturn("");
        presenter.checkDB();
        verify(view).setResultText("Please fill in your user details.");

    }
    @Test
    public void checkEmptyPassword(){
        when(view.getPassword()).thenReturn("");
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
        when(view.getPassword()).thenReturn("studentpassword");
        when(view.getEmail()).thenReturn("notanemail");

        doAnswer(invocation -> {
            LoginPresenter callbackPresenter = invocation.getArgument(0);
            callbackPresenter.setViewText();
            return null;
        }).when(model).signIn(any(LoginPresenter.class), eq("notanemail"), eq("studentpassword"));

        presenter.checkDB();

        verify(view).setResultText("Incorrect password or user does not exist.");
    }
    @Test
    public void testPasswordNotFound(){
        when(view.getEmail()).thenReturn("genericstudent@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("notapassword");

        doAnswer(invocation -> {
            LoginPresenter callbackPresenter = invocation.getArgument(0);
            callbackPresenter.setViewText();
            return null;
        }).when(model).signIn(any(LoginPresenter.class), eq("genericstudent@mail.utoronto.ca"), eq("notapassword"));


        presenter.checkDB();
        verify(view).setResultText("Incorrect password or user does not exist.");
    }
    @Test
    public void testAdminPresenterLogin(){
        when(view.getEmail()).thenReturn("genericadmin@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("adminpassword");

        doAnswer(invocation -> {
            LoginPresenter callbackPresenter = invocation.getArgument(0);
            callbackPresenter.goTo("Admin");
            return null;
        }).when(model).signIn(any(LoginPresenter.class), eq("genericadmin@mail.utoronto.ca"), eq("adminpassword"));


        presenter.checkDB();
        verify(view).enterAdminApp();
    }

    @Test
    public void testStudentPresenterLogin(){
        when(view.getEmail()).thenReturn("genericstudent@mail.utoronto.ca");
        when(view.getPassword()).thenReturn("studentpassword");

        doAnswer(invocation -> {
            LoginPresenter callbackPresenter = invocation.getArgument(0);
            callbackPresenter.goTo("Student");
            return null;
        }).when(model).signIn(any(LoginPresenter.class), eq("genericstudent@mail.utoronto.ca"), eq("studentpassword"));

        presenter.checkDB();
        verify(view).enterStudentApp();
    }


}