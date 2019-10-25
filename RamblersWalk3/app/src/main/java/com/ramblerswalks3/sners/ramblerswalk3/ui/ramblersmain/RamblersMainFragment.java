package com.ramblerswalks3.sners.ramblerswalk3.ui.ramblersmain;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ramblerswalks3.sners.ramblerswalk3.R;
import com.ramblerswalks3.sners.ramblerswalk3.RamblersMain;

public class RamblersMainFragment extends Fragment {

    private RamblersMainViewModel mViewModel;
    private ImageButton ibGroups;
    private ImageButton ibWeekdays;

    public static RamblersMainFragment newInstance() {
        return new RamblersMainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ramblers_main_fragment, container, false);
        ibGroups =      (ImageButton) view.findViewById(R.id.ibGroups);
        ibWeekdays =    (ImageButton) view.findViewById(R.id.ibWeekdays);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RamblersMainViewModel.class);
        // TODO: Use the ViewModel
    }


    public void selectLocation(android.view.View v)
    {
        Toast.makeText(getActivity(), "Select Location",
                Toast.LENGTH_SHORT).show();

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.location_fragment_list, new LocationFragment())
//                .commitNow();

    }

    public void selectWeekdays(android.view.View v) {
        Toast.makeText(getActivity(), "Select Weekdays",
                Toast.LENGTH_SHORT).show();

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.location_fragment_list, new LocationFragment())
//                .commitNow();

    }

        //////////////////////////////////////////////////////////////////////
        //		Call WalkInfo												//
        //////////////////////////////////////////////////////////////////////

        private void getWalkInfo(String buttonPressed)
        {
            Toast.makeText(getActivity(), "ToDo - get walks info",
                    Toast.LENGTH_SHORT).show();
            //  class not yet built
 //           Intent walkIntent = new Intent(getActivity(), WalkSummary.class);
//            walkIntent.putExtra("PostCode", 		etPostCode.getText().toString());
//            walkIntent.putExtra("Distance", 		tvDistanceNumbers.getText().toString());
//            walkIntent.putExtra("FromDistance", 	("" + distanceLower));
//            walkIntent.putExtra("ToDistance", 		("" + distanceHigher));
//            walkIntent.putExtra("StartDay", 		etStartDay.getText().toString());
//            walkIntent.putExtra("EndDay", 			etEndDay.getText().toString());
//            walkIntent.putExtra("StartMonthNo", 	"" + ((spStartMonth.getSelectedItemPosition() + 1)));
//            walkIntent.putExtra("EndMonthNo", 		"" + ((spEndMonth.getSelectedItemPosition() + 1)));
//            walkIntent.putExtra("Weekdays", 		tvWeekDays.getText().toString());
//            walkIntent.putExtra("ButtonPressed", 	buttonPressed);
  //          startActivityForResult(walkIntent, 2);

 	/*
		startActivity(walkIntent);
		*/
        }
    }




