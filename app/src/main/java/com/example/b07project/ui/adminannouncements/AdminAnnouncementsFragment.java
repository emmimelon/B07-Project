package com.example.b07project.ui.adminannouncements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07project.databinding.FragmentAdminAnnouncementsBinding;
import com.example.b07project.databinding.FragmentNotificationsBinding;

public class AdminAnnouncementsFragment extends Fragment {

    private FragmentAdminAnnouncementsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdminAnnouncementsViewModel adminAnnouncementsViewModel =
                new ViewModelProvider(this).get(AdminAnnouncementsViewModel.class);

        binding = FragmentAdminAnnouncementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAdminAnnouncements;
        adminAnnouncementsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}