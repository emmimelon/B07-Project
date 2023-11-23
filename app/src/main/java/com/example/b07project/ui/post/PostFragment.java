package com.example.b07project.ui.post;

import androidx.appcompat.content.res.AppCompatResources;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07project.MainActivity;
import com.example.b07project.R;
import com.example.b07project.databinding.FragmentNotificationsBinding;
import com.example.b07project.databinding.FragmentPostBinding;
import com.example.b07project.ui.notifications.NotificationsViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class PostFragment extends Fragment{

    private FragmentPostBinding binding;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    LinearLayout gpaSelect;
    LinearLayout postSuccess, postFail;

    TextView courseCode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        gpaSelect = (LinearLayout) root.findViewById(R.id.selectGPA);
        gpaSelect.setVisibility(View.GONE);
        postSuccess = (LinearLayout) root.findViewById(R.id.postSuccess);
        postFail = (LinearLayout) root.findViewById(R.id.postFail);
        postSuccess.setVisibility(View.GONE);
        postFail.setVisibility(View.GONE);

        String[] postCategories = {"Computer Science Major/Specialist", "Computer Science Minor",
                "Mathematics Specialist", "Mathematics Major", "Statistics Specialist", "Statistics Major"};
        Double[] possibleGPA = {0.0, 0.7, 1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0};

        Dictionary<String,String[]> reqCourseDictionary = new Hashtable<>();
        String[] reqCourses = {"CSC/MAT A67", "CSC A48", "MAT A22", "MAT A31", "MAT A37"};
        reqCourseDictionary.put(postCategories[0], reqCourses);
        reqCourses = new String[]{"CSC/MAT A67", "CSC A48", "MAT A22/A23", "MAT A31/A30/A32", "CSC A08"};
        reqCourseDictionary.put(postCategories[1], reqCourses);
        reqCourses = new String[]{"CSC/MAT A67", "MAT A22", "MAT A31", "MAT A37"};
        reqCourseDictionary.put(postCategories[2], reqCourses);
        reqCourseDictionary.put(postCategories[3], reqCourses);
        reqCourses = new String[]{"CSCA08/A20", "MATA22", "MATA30/A31", "MATA36/A37"};
        reqCourseDictionary.put(postCategories[4], reqCourses);
        reqCourses = new String[]{"CSCA08", "CSC/MATA67", "MATA22", "MATA31", "MATA37"};
        reqCourseDictionary.put(postCategories[5], reqCourses);

        Double[] thresholds = {2.5, 0.7, 2.5, 2.0, 2.5, 2.3};

        courseCode = (TextView) root.findViewById(R.id.courseCode);
        autoCompleteTextView = root.findViewById(R.id.autoPostChoice);
        arrayAdapter = new ArrayAdapter<String>(getActivity() ,
                R.layout.post_choices, postCategories);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                postSuccess.setVisibility(View.GONE);
                postFail.setVisibility(View.GONE);
                String items = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), "Selected: " +items, Toast.LENGTH_SHORT).show();
                gpaSelect.setVisibility(View.VISIBLE);
                String[] requiredCourses = reqCourseDictionary.get(items);
                AutoCompleteTextView autoGPA = root.findViewById(R.id.autoGPAChoice);
                ArrayAdapter<Double> arrayAdapterGPA = new ArrayAdapter<>(getActivity() ,
                        R.layout.post_choices, possibleGPA);
                autoGPA.setAdapter(arrayAdapterGPA);
                courseCode.setText(requiredCourses[0]);
                Double[] gpaAcrossCourses = new Double[requiredCourses.length];
                for (int i = 0; i < requiredCourses.length; i++){
                    gpaAcrossCourses[i] = 0.0;
                }
                autoGPA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    int i = 1;
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        if (i < requiredCourses.length){
                            courseCode.setText(requiredCourses[i]);
                            Double gpa = (Double) parent.getItemAtPosition(pos);
                            if (gpa == 0.0){
                                postFail.setVisibility(View.VISIBLE);
                                gpaSelect.setVisibility(View.GONE);
                            }
                            gpaAcrossCourses[i-1] = gpa;
                            i++;
                        }
                        else {
                            gpaSelect.setVisibility(View.GONE);
                            double avgGPA = 0;
                            int j = 0;
                            for (j = 0; j < gpaAcrossCourses.length; j++){
                                avgGPA += gpaAcrossCourses[j];
                            }
                            if (j != 0){
                                avgGPA = avgGPA/j;
                                int count = 0;
                                boolean extracheck = true;
                                switch(items){
                                    case "Computer Science Major/Specialist":
                                        count = 0;
                                        if (gpaAcrossCourses[0] < 1.7) count++;
                                        if (gpaAcrossCourses[2] < 1.7) count++;
                                        if (gpaAcrossCourses[4] < 1.7) count++;
                                        if(gpaAcrossCourses[1] < 3.0 || count >=2){
                                            extracheck = false;
                                        }
                                        break;
                                    case "Mathematics Specialist":
                                        count = 0;
                                        if (gpaAcrossCourses[0] < 3.0) count++;
                                        if (gpaAcrossCourses[1] < 3.0) count++;
                                        if (gpaAcrossCourses[3] < 3.0) count++;
                                        if (count >= 2) extracheck = false;
                                        break;
                                    case "Mathematics Major":
                                        count = 0;
                                        if (gpaAcrossCourses[0] < 3.0 && gpaAcrossCourses[1]
                                                < 3.0 && gpaAcrossCourses[3] < 3.0) extracheck = false;
                                        break;
                                    default: extracheck = true;
                                }

                                if (avgGPA >= thresholds[position] && extracheck == true){
                                    postSuccess.setVisibility(View.VISIBLE);
                                    KonfettiView konfettiView = root.findViewById(R.id.konfettiView);
                                    EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
                                    konfettiView.start(
                                            new PartyFactory(emitterConfig)
                                                    .spread(360)
                                                    .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                                                    .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                                                    .setSpeedBetween(0f, 30f)
                                                    .position(new Position.Relative(0.5, 0.3))
                                                    .build()
                                    );
                                }
                                else{
                                    postFail.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                    }
                });

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