package com.ramblerswalks3.sners.ramblerswalk3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeekdaysFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class WeekdaysFragment extends Fragment  implements View.OnClickListener {

    private CheckBox tickBoxes[] = new CheckBox[7];
    private String dayArray[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private Button btnWeekdays;

    private OnFragmentInteractionListener mListener;

    public WeekdaysFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment WeekdaysFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static WeekdaysFragment newInstance(String param1, String param2) {
//        WeekdaysFragment fragment = new WeekdaysFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_weekdays, container, false);
        tickBoxes[0] 	= (CheckBox) view.findViewById(R.id.cbMonday);
        tickBoxes[1] 	= (CheckBox) view.findViewById(R.id.cbTuesday);
        tickBoxes[2] 	= (CheckBox) view.findViewById(R.id.cbWednesday);
        tickBoxes[3] 	= (CheckBox) view.findViewById(R.id.cbThursday);
        tickBoxes[4] 	= (CheckBox) view.findViewById(R.id.cbFriday);
        tickBoxes[5] 	= (CheckBox) view.findViewById(R.id.cbSaturday);
        tickBoxes[6] 	= (CheckBox) view.findViewById(R.id.cbSunday);
        btnWeekdays 	= (Button) 	 view.findViewById(R.id.btnWeekdays);

        btnWeekdays.setOnClickListener(this);

       // Intent intent = getIntent();
      //  String weekdaysString = intent.getExtras().getString("weekdays");
     //   populateTickBoxes(weekdaysString);

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

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(), "Weekdays Pressed", Toast.LENGTH_SHORT).show();
        ArrayList<String> chosenDays = new ArrayList<String>();
        for (int wCounter = 0; wCounter < 7; wCounter++)
        {
            if (tickBoxes[wCounter].isChecked())
            {
                chosenDays.add(dayArray[wCounter]);
            }
        }
//        Intent responseIntent = new Intent(getActivity(), );
//        String chosenDaysArray[] = chosenDays.toArray(new String[chosenDays.size()]);
       /// responseIntent.putExtra("weekdays", chosenDays);
        // navigate to fragment
//        this.setResult(RESULT_OK, responseIntent);
//        finish();
    }

    private void populateTickBoxes(String weekdaysString) {
        // TODO Auto-generated method stub

        for (int dayCounter = 0 ; dayCounter < 7 ; dayCounter++)
        {
            if (weekdaysString.indexOf(dayArray[dayCounter]) >= 0)
            {
                //	Weekday needs a tick
                tickBoxes[dayCounter].setChecked(true);
            }
            else
            {
                tickBoxes[dayCounter].setChecked(false);
            }

        }
    }
}
