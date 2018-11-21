package com.example.trailblazers.techintellect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView welcome; //Added by Haritha

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public static List<FirebaseDataModel> flist;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnTakeQuiz;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters

    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        welcome = view.findViewById(R.id.welcome);
        if(firebaseUser != null){
            welcome.setText("Welcome " +firebaseUser.getDisplayName());
        }
        else{
            welcome.setText("Welcome Guest");
        }

        final String[] SPINNERLIST = {"R Programming", "Natural Language Processing", "Google Go", "Computer Science Acronyms"};
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        final MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) view.findViewById(R.id.quiz_topics_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter1);

        String[] SPINNERLIST_DIFFICULTY_LEVEL = {"Easy", "Medium", "Hard"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, SPINNERLIST_DIFFICULTY_LEVEL);
        final MaterialBetterSpinner materialDesignSpinnerDiffLvl = (MaterialBetterSpinner) view.findViewById(R.id.difficulty_level_spinner);
        materialDesignSpinnerDiffLvl.setAdapter(arrayAdapter2);

        String[] SPINNERLIST_MODE = {"Timed", "Endless"};
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, SPINNERLIST_MODE);
        final MaterialBetterSpinner materialDesignSpinnerMode = (MaterialBetterSpinner) view.findViewById(R.id.mode_spinner);
        materialDesignSpinnerMode.setAdapter(arrayAdapter3);

        //Added by Ravi starts
        btnTakeQuiz =view.findViewById(R.id.btnTakeQuiz);


        btnTakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),QuizScreen.class);

                String topic = materialDesignSpinner.getText().toString();
                String level = materialDesignSpinnerDiffLvl.getText().toString();
                String mode = materialDesignSpinnerMode.getText().toString();

                if(TextUtils.isEmpty(topic))
                {
                    materialDesignSpinner.setError("Topic cannot be empty");

                }
                if(TextUtils.isEmpty(level))
                {
                    materialDesignSpinnerDiffLvl.setError("Level cannot be empty");
                }
                if(TextUtils.isEmpty(mode))
                {
                    materialDesignSpinnerMode.setError("Mode cannot be empty");
                }
                else {

                    FirebaseDataModel f1 = new FirebaseDataModel();
                    flist = new ArrayList<FirebaseDataModel>();
                    f1.setTopic(topic);
                    f1.setLevel(level);
                    flist.add(f1);

                    Bundle bundle = new Bundle();
                    bundle.putString("topic", topic);
                    bundle.putString("level", level);
                    bundle.putString("mode", mode);
                    intent.putExtras(bundle);

                    startActivity(intent);
                    //Added by Ravi ends
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

