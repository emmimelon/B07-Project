package com.example.b07project.ui.complaints;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ComplaintsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ComplaintsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Complaints fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}