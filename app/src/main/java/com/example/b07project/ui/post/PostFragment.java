package com.example.b07project.ui.post;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b07project.MainActivity;
import com.example.b07project.R;
import com.example.b07project.databinding.FragmentNotificationsBinding;
import com.example.b07project.databinding.FragmentPostBinding;
import com.example.b07project.ui.notifications.NotificationsViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PostFragment extends Fragment{

    private FragmentPostBinding binding;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String[] postCategories = {"Computer Science Major", "Computer Science Specialist",
                "Mathematics Major", "Mathematics Specialist", "Statistics Major", "Statistics Specialist"};
        autoCompleteTextView = root.findViewById(R.id.autoPostChoice);
        arrayAdapter = new ArrayAdapter<String>(getActivity() ,
                R.layout.post_choices, postCategories);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String items = (String) parent.getItemAtPosition(position);

            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}