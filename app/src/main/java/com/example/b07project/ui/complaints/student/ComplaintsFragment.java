package com.example.b07project.ui.complaints.student;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07project.R;
import com.example.b07project.databinding.FragmentComplaintsBinding;
import com.example.b07project.objects.Complaint;
import com.example.b07project.objects.User;
import com.example.b07project.ui.complaints.admin.ComplaintAdapter;
import com.example.b07project.ui.complaints.admin.DetailedComplaintFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComplaintsFragment extends Fragment implements StudentComplaintViewInterface{

    private FragmentComplaintsBinding binding;
    private DatabaseReference ref, listRef;
    private FirebaseDatabase firebaseDatabase;
    private RelativeLayout layout;
    EditText inputComplaintTitle, inputComplaintDescription;
    Button btnSubmitComplaint;
    List<Complaint> myComplaints;
    private RecyclerView pastComplaints;
    boolean justAdded = false;
    public ComplaintsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentComplaintsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance("https://b07-project-c5222-default-rtdb.firebaseio.com/");
        inputComplaintTitle = root.findViewById(R.id.editTextComplaintTitle);
        inputComplaintDescription = root.findViewById(R.id.editTextComplaintDescription);
        btnSubmitComplaint = root.findViewById(R.id.buttonSubmitComplaint);
        pastComplaints = root.findViewById(R.id.pastComplaints);
        layout = root.findViewById(R.id.studentComplaintLayout);
        btnSubmitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justAdded = false;
                String inputTitle = inputComplaintTitle.getText().toString();
                String inputDescription = inputComplaintDescription.getText().toString();
                Complaint complaint = new Complaint(id, inputTitle, inputDescription);
                if (inputTitle.equals("") || inputDescription.equals("")){
                    Toast.makeText(getActivity(), "Please fill in your complaint details", Toast.LENGTH_SHORT).show();
                }
                else if (inputTitle.contains(".") || inputTitle.contains("#") || inputTitle.contains("$") ||
                        inputTitle.contains("[") || inputTitle.contains("]")){
                    Toast.makeText(getActivity(), "Do not use these character in your title: .#$[]", Toast.LENGTH_SHORT).show();
                }
                else {
                    ref = firebaseDatabase.getReference("Complaints").child(id).child(inputTitle);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()){
                                complaint.writeNewComplaint();
                                justAdded = true;
                                Toast.makeText(getActivity(), "Submitted Complaint!", Toast.LENGTH_SHORT).show();
                            }
                            else if (justAdded == false){
                                Toast.makeText(getActivity(), "You already hava a complaint with this title!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        listRef = firebaseDatabase.getReference("Complaints").child(id);
        myComplaints = new ArrayList<>();
        pastComplaints.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        pastComplaints.setAdapter(new StudentComplaintAdapter(getActivity().getApplicationContext(), myComplaints, this));
        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot title: snapshot.getChildren()){
                        if (title != null && title.child("Description").getValue() != null){
                            Complaint c = new Complaint(title.getKey().toString(), title.child("Description").getValue().toString(),
                                    title.child("Date").getValue().toString(), R.drawable.ic_complaint_black);
                            if (!myComplaints.contains(c)) {
                                myComplaints.add(c);
                                Collections.sort(myComplaints);
                                Collections.reverse(myComplaints);
                                pastComplaints.getAdapter().notifyItemInserted(0);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
    @Override
    public void onItemClick(int position) {
        String title = myComplaints.get(position).getTitle();
        String date = myComplaints.get(position).getDate();
        String desc = myComplaints.get(position).getDescription();
        expandComplaintPopup(title, desc, date);
    }

    private void expandComplaintPopup(String title, String description, String date){
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.complaint_popup, null);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        TextView currentTitle = popup.findViewById(R.id.currentComplaintTitle);
        TextView currentDesc = popup.findViewById(R.id.currentComplaintDescription);
        TextView currentDate = popup.findViewById(R.id.currentComplaintDate);
        currentTitle.setText(title);
        currentDesc.setText(description);
        currentDate.setText(date);

        boolean canExpand = true;
        PopupWindow popupWindow = new PopupWindow(popup, width, height, canExpand);
        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
            }
        });
        Button okay = popup.findViewById(R.id.okButton);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}