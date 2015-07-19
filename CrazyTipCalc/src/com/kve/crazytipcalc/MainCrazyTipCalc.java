package com.kve.crazytipcalc;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class MainCrazyTipCalc extends Activity {
	
	//	first try change to have one check changed listener, this code sucks
 //	tut5 -  11.58 mins
	
	final static  String TOTAL_BILL 		= "TOTAL_BILL";
	final static  String CURRENT_TIP 		= "CURRENT_TIP";
	final static  String BILL_WITHOUT_TIP 	= "BILL_WITHOUT_TIP";
	
	private double billBeforeTip, tipAmount, finalBill;

	private EditText etBill, etTip, etFinal;
	private TextView tvTimeWaiting;
	private SeekBar sbTip;
	
	private int[] checkListValues = new int[12];
	
	private CheckBox cbFriendly, cbSpecials, cbOpinion;
	private RadioGroup rgAvailability;
	private RadioButton rbBad, rbOK, rbGood;
	private Button btnStart, btnPause, btnReset;
	
	private Spinner spProblemSolving;
	private Chronometer chTimeWaiting;
	
	private long secondsWaited;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_crazy_tip_calc);
		
		if (null == savedInstanceState)
		{
			billBeforeTip 	= 0.0d;
			tipAmount 		= 0.15d;
			finalBill	 	= 0.0d;
		}
		else
		{
			billBeforeTip 	= savedInstanceState.getDouble(BILL_WITHOUT_TIP);
			tipAmount 		= savedInstanceState.getDouble(CURRENT_TIP);
			finalBill	 	= savedInstanceState.getDouble(TOTAL_BILL);
		}
		btnStart	= (Button) 		findViewById(R.id.btnStart); 
		btnPause	= (Button) 		findViewById(R.id.btnPause); 
		btnReset	= (Button) 		findViewById(R.id.btnReset); 
		sbTip 		= (SeekBar)  	findViewById(R.id.sbTip);
		etBill  	= (EditText) 	findViewById(R.id.etBill);
		etTip   	= (EditText) 	findViewById(R.id.etTip);
		etFinal 	= (EditText) 	findViewById(R.id.etFinalBill);
		cbFriendly 	= (CheckBox) 	findViewById(R.id.cbFriendly);
		cbSpecials	= (CheckBox) 	findViewById(R.id.cbSpecials);
		cbOpinion	= (CheckBox) 	findViewById(R.id.cbOpinion);
		rbBad		= (RadioButton) findViewById(R.id.rbAvailableBad);
		rbOK		= (RadioButton) findViewById(R.id.rbAvailableOK); 
		rbGood		= (RadioButton) findViewById(R.id.rbAvailableGood); 
		tvTimeWaiting  = (TextView)    findViewById(R.id.tvTimeWaiting);
		rgAvailability = (RadioGroup)  findViewById(R.id.rgAvailability);
		chTimeWaiting  = (Chronometer) findViewById(R.id.chTimeWaiting);
		spProblemSolving = (Spinner)   findViewById(R.id.spProblemSolving);
						
		etBill.addTextChangedListener(billBeforeTipListener);
		sbTip.setOnSeekBarChangeListener(tipSeekBarListener);
		
		setupIntroCheckBoxes();
		addchangeListenersToRadios();
		addItemSelectedListenerToSpinner();
		addButtonOnClickListeners();
	}

	


	private TextWatcher billBeforeTipListener = new TextWatcher(){

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			cbFriendly.setChecked(true);
			cbSpecials.setChecked(false);
			cbOpinion.setChecked(false);
			
	
	}

		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			try {
				billBeforeTip 	= Double.parseDouble(s.toString());
			} catch (NumberFormatException e) {
				billBeforeTip	= 0;
			}

			updateTipAndFinalBill();
		}

		
			
	};
	
	private void setTipFromWaitressChecklist() {
		// TODO Auto-generated method stub
		float checklistTotal = 0;
		for (int item : checkListValues)
		{
			checklistTotal += item;
		}
		etTip.setText(String.format("%.02f", (checklistTotal / 100)));
	}
	
	private void updateTipAndFinalBill() {
		// TODO Auto-generated method stub
		tipAmount = Double.parseDouble(etTip.getText().toString());
		finalBill = (tipAmount * billBeforeTip) + billBeforeTip;
		etFinal.setText(String.format("%.02f", finalBill));
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Seek Bar
	//////////////////////////////////////////////////////////////////////////////////
	private OnSeekBarChangeListener tipSeekBarListener = new OnSeekBarChangeListener(){

		@Override
		public void onProgressChanged(SeekBar sb, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
			tipAmount = ((double)(sbTip.getProgress())) / 100;
			etTip.setText(String.format("%02f", tipAmount));
			updateTipAndFinalBill();
			
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Check Boxes
	//////////////////////////////////////////////////////////////////////////////////
	private void setupIntroCheckBoxes() {
		// TODO Auto-generated method stub
		//	This dude's code really sucks
		cbFriendly.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checkListValues[0] = (isChecked?4 : 0);
				setTipFromWaitressChecklist();
				updateTipAndFinalBill();
			}
		});
		cbSpecials.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checkListValues[1] = (isChecked?1 : 0);
				setTipFromWaitressChecklist();
				updateTipAndFinalBill();
			}});
		cbOpinion.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				checkListValues[2] = (isChecked?2 : 0);
				setTipFromWaitressChecklist();
				updateTipAndFinalBill();
		}});		
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	//	Radio Buttons
	//////////////////////////////////////////////////////////////////////////////////
	private void addchangeListenersToRadios() {
		// TODO Auto-generated method stub
		//private RadioGroup rgAvailability;
		//private RadioButton rbBad, rbOK, rbGood;
		rgAvailability.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				checkListValues[3] = (rbBad.isChecked() ?-2 : 0);
				checkListValues[4] = (rbOK.isChecked()  ? 0 : 0);
				checkListValues[5] = (rbGood.isChecked()? 2 : 0);
				setTipFromWaitressChecklist();
				updateTipAndFinalBill();
			}
		});

	}

	//////////////////////////////////////////////////////////////////////////////////
	//	Spinner
	//////////////////////////////////////////////////////////////////////////////////
	private void addItemSelectedListenerToSpinner() {
		spProblemSolving.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedItem = spProblemSolving.getSelectedItem().toString();
				checkListValues[6] =  (selectedItem.equals("Bad")  ?-2 : 0);
				checkListValues[7] =  (selectedItem.equals("OK")   ? 2 : 0);
				checkListValues[8] =  (selectedItem.equals("Good") ? 4 : 0);
				setTipFromWaitressChecklist();
				updateTipAndFinalBill();
		
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
	}

	//////////////////////////////////////////////////////////////////////////////////
	//	Chronometer
	//////////////////////////////////////////////////////////////////////////////////

	private void addButtonOnClickListeners() {
		btnStart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				int stoppedTime = 0;
				String chronoText = chTimeWaiting.getText().toString();
				String timeParts[] = chronoText.split(":");
				
				if (timeParts.length ==2){
					//	We're dealing with seconds and miliseconds
					stoppedTime = Integer.parseInt(timeParts[0] )* 60 * 1000 + 
							Integer.parseInt(timeParts[1] ) * 1000;
				}
				if (timeParts.length ==3){
					//	We're dealing with minutes, seconds and miliseconds - anyone figure a better way of doing this?
					stoppedTime = Integer.parseInt(timeParts[0] )* 60 * 60 *1000 +
							Integer.parseInt(timeParts[1] )* 60 * 1000 + 
							Integer.parseInt(timeParts[2] ) * 1000;
				}
				chTimeWaiting.setBase(SystemClock.elapsedRealtime() - stoppedTime);
				
				secondsWaited = Long.parseLong(timeParts[1]);
				updateTipBasedOnTimeWaited(secondsWaited);
				
				chTimeWaiting.start();
				
		}});
	
		btnPause.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				chTimeWaiting.stop();
			}});
		
		btnReset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				chTimeWaiting.setBase(SystemClock.elapsedRealtime());
				secondsWaited = 0;
			}});

		
	}


	protected void updateTipBasedOnTimeWaited(long aSecondsWaited) {

		checkListValues[9] =  ((aSecondsWaited > 10)  ?-2 : 2);
		setTipFromWaitressChecklist();
		updateTipAndFinalBill();
	
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);
		outState.putDouble(CURRENT_TIP, tipAmount);
		outState.putDouble(TOTAL_BILL, finalBill);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_crazy_tip_calc, menu);
		return true;
	}
	
	

}
