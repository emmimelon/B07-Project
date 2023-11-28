package com.example.b07project.ui.events;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07project.databinding.FragmentEventsBinding;

public class EventsFragment extends Fragment {
    private FragmentEventsBinding binding;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Initialize UI components and populate them with event names/keys
        // Set up click listeners for event items to navigate to event details

        return root;
    }
}
