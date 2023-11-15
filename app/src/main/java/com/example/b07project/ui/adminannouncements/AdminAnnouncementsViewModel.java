package com.example.b07project.ui.adminannouncements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminAnnouncementsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdminAnnouncementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is announcements fragment -Mikey :)");
    }

    public LiveData<String> getText() {
        return mText;
    }
}