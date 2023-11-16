package com.example.b07project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminComplaintsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminComplaintsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminComplaintsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminComplaintsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminComplaintsFragment newInstance(String param1, String param2) {
        AdminComplaintsFragment fragment = new AdminComplaintsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_complaints, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        // Need to add Firebase integration
        List<Complaint> complaints = new ArrayList<Complaint>();
        complaints.add(new Complaint("Adam S.", "I didn't like the food", R.drawable.megaphone));
        complaints.add(new Complaint("John S.", "Too loud", R.drawable.megaphone));
        complaints.add(new Complaint("Mark T.", "Need more attendance", R.drawable.megaphone));
        complaints.add(new Complaint("Sam T.", "Too far", R.drawable.megaphone));
        complaints.add(new Complaint("Joe M.", "Not accessible", R.drawable.megaphone));
        complaints.add(new Complaint("Dutch R.", "Not walkable", R.drawable.megaphone));
        complaints.add(new Complaint("Paula T.", "No transit proximity", R.drawable.megaphone));
        complaints.add(new Complaint("Kat P.", "Not enough languages", R.drawable.megaphone));
        complaints.add(new Complaint("Jean C.", "No french", R.drawable.megaphone));



        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(new ComplaintAdapter(getActivity().getApplicationContext(), complaints));
        return view;
    }
}