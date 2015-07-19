package com.kve.ramblerswalks;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;



public class RamblersMain extends Activity  implements OnClickListener, OnLongClickListener{

	private EditText 	etPostCode, etStartDay, etEndDay;
	private Spinner 	spStartMonth, spEndMonth;
	private TextView 	tvWeekDays, tvDistanceNumbers;
	private ImageButton ibLowerMinus, ibLowerPlus, ibHigherMinus, ibHigherPlus, ibSearch;
	private Button 		btnWeekends, btnWeekdays, btnEvenings, btnEveryDay, btnThisWeek, btnPickDays;
	
	private Integer distanceLower = 6;
	private Integer distanceHigher = 10;

	private SharedPreferences lastUsedInfo;
	
	private final static int WEEKDAYS_REQUEST = 1;
	private final static int WALKSUMMARY_REQUEST = 2;

	ArrayList<String> chosenWeekdays;

	static volatile ArrayList<WalkInfo> walks;

	//	To avoid passing ArrayLists via intents, which by the way is possible - just like I've got enough to figure for now
	
	String TAG = "RamblersMain";
	
	/*
	  http://www.ramblers.co.uk/walksfinder/walks.rss?showMap=1&zoomLevel=3&advanced=1&area=yo25+9rh&from_date_day=1&from_date_month=7&to_date_day=30&to_date_month=7&grade[]=EA&grade[]=E&grade[]=L&grade[]=M&grade[]=S&grade[]=T&weekday=Sunday&distance=6-10&
	 */
	
	String urlTemplate = "http://www.ramblers.co.uk/walksfinder/walks.rss?showMap=1&zoomLevel=3&advanced=1&area=yo25+9rh&from_date_day=1&from_date_month=4&to_date_day=30&to_date_month=4&grade[]=EA&grade[]=E&grade[]=L&grade[]=M&grade[]=S&grade[]=T&weekday=Sunday&distance=6-10&";
	String pullParserArray[][] = {{"title", ""}, {"description", ""}, {"link", ""}, {"guid", ""}};
	int parserArrayIncrement = 0;
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		lastUsedInfo = getSharedPreferences("walksinfo", MODE_PRIVATE);
		
	 	Calendar today = Calendar.getInstance();

        setContentView(R.layout.activity_ramblers_main);
        if (savedInstanceState == null) {
    	 	etPostCode 			= (EditText) findViewById(R.id.etPostCode);
		 	etStartDay 			= (EditText) findViewById(R.id.etStartDay);
		 	etEndDay 			= (EditText) findViewById(R.id.etEndDay);
		 	spStartMonth 		= (Spinner)  findViewById(R.id.spStartMonth);
		 	spEndMonth 			= (Spinner)  findViewById(R.id.spEndMonth);
		 	tvDistanceNumbers 	= (TextView) findViewById(R.id.tvDistanceNumbers);
			tvWeekDays 			= (TextView) findViewById(R.id.tvWeekDays);
			btnWeekends 		= (Button) 	 findViewById(R.id.btnWeekends);
			btnWeekdays 		= (Button) 	 findViewById(R.id.btnWeekdays);
			btnEvenings 		= (Button) 	 findViewById(R.id.btnEvenings);
			btnEveryDay 		= (Button) 	 findViewById(R.id.btnEveryDay);
			btnThisWeek 			= (Button) 	 findViewById(R.id.btnThisWeek);
			btnPickDays 		= (Button) 	 findViewById(R.id.btnPickDays);
			btnWeekends.		setOnClickListener(this);
			btnWeekdays.		setOnClickListener(this);
			btnEvenings.		setOnClickListener(this);
			btnThisWeek.		setOnClickListener(this);
			btnPickDays.		setOnClickListener(this);
			btnEveryDay.		setOnClickListener(this);
			ibLowerMinus 		= (ImageButton)  findViewById(R.id.ibLowerMinus);
			ibLowerPlus 		= (ImageButton)  findViewById(R.id.ibLowerPlus);
			ibHigherMinus 		= (ImageButton)  findViewById(R.id.ibHigherMinus);
			ibHigherPlus 		= (ImageButton)  findViewById(R.id.ibHigherPlus);
			ibSearch 			= (ImageButton)  findViewById(R.id.ibSearch);
			ibLowerMinus.		setOnClickListener(this);
			ibLowerPlus.		setOnClickListener(this);
			ibHigherMinus.		setOnClickListener(this);
			ibHigherPlus.		setOnClickListener(this);
			ibSearch.			setOnClickListener(this);
			ibLowerMinus.		setOnLongClickListener(this);
			ibLowerPlus.		setOnLongClickListener(this);
			ibHigherMinus.		setOnLongClickListener(this);
			ibHigherPlus.		setOnLongClickListener(this);
	 	
		 	distanceLower =		Integer.parseInt((lastUsedInfo.getString("FromDistance", 	"6")));	
		 	distanceHigher =	Integer.parseInt((lastUsedInfo.getString("ToDistance", 		"10")));	
		 	showDistance();
		 	
		 	etPostCode.			setText(lastUsedInfo.getString("PostCode", "YO25 9RH"));
		 	etStartDay.			setText("" + (today.get(Calendar.DATE)));		
		 	etEndDay.			setText("" + (today.getActualMaximum(Calendar.DAY_OF_MONTH)));
	
	
		 	ArrayAdapter<CharSequence> monthAdapter = 
		 			ArrayAdapter.createFromResource(RamblersMain.this, R.array.month_list, android.R.layout.simple_spinner_item);
		 	monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 	spStartMonth.setAdapter(monthAdapter);
		 	spEndMonth.setAdapter(monthAdapter);
		 	//	Oh spare me, month is date and date is month
		 	Log.d(TAG, "ThisMondth is "+ (today.get(Calendar.MONTH)));
		 	spStartMonth.setSelection(today.get(Calendar.MONTH));
		 	spEndMonth.setSelection(today.get(Calendar.MONTH));
				 	
		 	tvWeekDays.setText(		lastUsedInfo.getString("Weekdays", 	"Saturday,Sunday;"));
	
       }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ramblers_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// Check which request we're responding to
    	if (requestCode == WEEKDAYS_REQUEST) {
    		// Make sure the request was successful
    		if (resultCode == RESULT_OK) {
    			// The user picked a contact.
    			// The Intent's data Uri identifies which contact was selected.
    			chosenWeekdays = data.getStringArrayListExtra("weekdays");
    			// Do something with the contact here (bigger example below)
    			showWeekdays();
    		}
    	}
    	else  if (requestCode == WEEKDAYS_REQUEST) {
    		// Make sure the request was successful
    		if (resultCode != RESULT_OK) {
    			// The user picked a contact.
    			// The Intent's data Uri identifies which contact was selected.
    			Toast.makeText(getApplicationContext(), "No walk info was found for these days", 
    					Toast.LENGTH_LONG).show();
    		}
    	}
    }
	
	@Override
	protected void onPause() {
		updateLastUsedInfo();
		super.onPause();

	}

	
	@Override
	protected void onStop() {
		updateLastUsedInfo();
		super.onStop();

	}



    private void showDistance()
    {
      	if (distanceLower <= 1) distanceLower = 1;
     	if (distanceLower >= 25) distanceLower = 25;
       	if (distanceHigher <= 1) distanceHigher = 1;
     	if (distanceHigher >= 25) distanceHigher = 25;
     	
     	if (distanceLower > distanceHigher) distanceLower = distanceHigher;
    	
     	tvDistanceNumbers.	setText("" + distanceLower + "-" + distanceHigher);
  	
    }
    
    private void showWeekdays() {
		String weekdaysText = "";
		for (String selWeekday : chosenWeekdays)
		{
			weekdaysText = weekdaysText + selWeekday + ", ";
		}
		tvWeekDays.setText(weekdaysText);
		getWalkInfo("PickDays");
	}

	private void updateLastUsedInfo()
	{
		SharedPreferences.Editor prefsEditor = lastUsedInfo.edit();
		prefsEditor.clear();
		prefsEditor.putString("PostCode", 		etPostCode.getText().toString());
		prefsEditor.putString("FromDistance", 	("" + distanceLower));		
		prefsEditor.putString("ToDistance", 	("" + distanceHigher));		
		prefsEditor.putString("StartDay", 		etStartDay.getText().toString());	
		prefsEditor.putString("EndDay", 		etEndDay.getText().toString());		
		prefsEditor.putString("Weekdays", 		tvWeekDays.getText().toString());	
		prefsEditor.apply();
		prefsEditor.commit();
	}

    //////////////////////////////////////////////////////////////////////
    //		Call WalkInfo												//
    //////////////////////////////////////////////////////////////////////

    private void getWalkInfo(String buttonPressed)
    {
		Intent walkIntent = new Intent(RamblersMain.this, WalkSummary.class);
		walkIntent.putExtra("PostCode", 		etPostCode.getText().toString());
		walkIntent.putExtra("Distance", 		tvDistanceNumbers.getText().toString());
		walkIntent.putExtra("FromDistance", 	("" + distanceLower));
		walkIntent.putExtra("ToDistance", 		("" + distanceHigher));
		walkIntent.putExtra("StartDay", 		etStartDay.getText().toString());	
		walkIntent.putExtra("EndDay", 			etEndDay.getText().toString());		
		walkIntent.putExtra("StartMonthNo", 	"" + ((spStartMonth.getSelectedItemPosition() + 1)));	
		walkIntent.putExtra("EndMonthNo", 		"" + ((spEndMonth.getSelectedItemPosition() + 1)));	
		walkIntent.putExtra("Weekdays", 		tvWeekDays.getText().toString());	
		walkIntent.putExtra("ButtonPressed", 	buttonPressed);
		startActivityForResult(walkIntent, 2);
		 	
 	/*
		startActivity(walkIntent);
		*/
    }
    
    //////////////////////////////////////////////////////////////////////
    //		Click Listeners												//
    //////////////////////////////////////////////////////////////////////
      
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibLowerMinus:
			lowerMinusClicked(v);

			break;
		case R.id.ibLowerPlus:
			lowerPlusClicked(v);

			break;
		case R.id.ibHigherMinus:
			higherMinusClicked(v);

			break;
		case R.id.ibHigherPlus:
			higherPlusClicked(v);

			break;
		case R.id.ibSearch:
			findWalksClicked(v);
			break;
		case R.id.btnWeekends:
			findWalksWeekendClicked(v);

			break;
		case R.id.btnWeekdays:
			findWalksWeekdaysClicked(v);

			break;
		case R.id.btnEvenings:
			findWalksEveningsClicked(v);

			break;
		case R.id.btnEveryDay:
			findWalksEveryDayClicked(v);

			break;
		case R.id.btnThisWeek:
			findWalksThisWeekClicked(v);

			break;
		case R.id.btnPickDays:
			findWalksPickDaysClicked(v);

			break;


		default:
			break;
		}

	}


	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.ibLowerMinus:
			lowerMinusLongClicked(v);

			break;
		case R.id.ibLowerPlus:
			lowerPlusLongClicked(v);

			break;
		case R.id.ibHigherMinus:
			higherMinusLongClicked(v);

			break;
		case R.id.ibHigherPlus:
			higherPlusLongClicked(v);

			break;
		default:
			break;
		}
		return false;
	}

    public void findWalksClicked(View v)
    {
		Toast.makeText(getApplicationContext(), "Find Walks using screen info", 
				Toast.LENGTH_SHORT).show();
		getWalkInfo("UseInfo");
   }
    
    public void findWalksWeekendClicked(View v)
    {
 		Toast.makeText(getApplicationContext(), "Find weekend walks for the next month", 
				Toast.LENGTH_SHORT).show();
		getWalkInfo("Weekend");
    }
   
    
    public void findWalksWeekdaysClicked(View v)
    {
		Toast.makeText(getApplicationContext(), "Find Weekday walks for the next month", 
				Toast.LENGTH_SHORT).show();
		getWalkInfo("Weekdays");
  }
    
    public void findWalksEveningsClicked(View v)
    {
		Toast.makeText(getApplicationContext(), "Find evening walks for the next month", 
				Toast.LENGTH_SHORT).show();
		getWalkInfo("Evenings");
    }
    
    public void findWalksEveryDayClicked(View v)
    {
		Toast.makeText(getApplicationContext(), "Find all walks for the next month", 
				Toast.LENGTH_SHORT).show();
		getWalkInfo("EveryDay");
 }
    
    public void findWalksThisWeekClicked(View v)
    {
		Toast.makeText(getApplicationContext(), "Find walks for the next week", 
				Toast.LENGTH_SHORT).show();
		getWalkInfo("ThisWeek");
  }
 
    public void findWalksPickDaysClicked(View v)
    {
		Toast.makeText(getApplicationContext(), "Pick days and then find walks", 
				Toast.LENGTH_SHORT).show();
		//	Go to pick screen
		Intent weekdaysIntent = new Intent(RamblersMain.this, Weekdays.class);
		weekdaysIntent.putExtra("weekdays", tvWeekDays.getText().toString() );
		startActivityForResult(weekdaysIntent, 1);
   }

    public void lowerMinusClicked(View v)
    {
    	distanceLower--; 
    	showDistance();
    }
 
    public void lowerPlusClicked(View v)
    {
       	distanceLower++; 
    	showDistance();
   }
    public void higherMinusClicked(View v)
    {
      	distanceHigher--; 
    	showDistance();   	
    }
    public void higherPlusClicked(View v)
    {
     	distanceHigher++; 
    	showDistance();   	
   }
  
    public void lowerMinusLongClicked(View v)
    {
       	distanceLower-=5; 
    	showDistance();
    }
    public void lowerPlusLongClicked(View v)
    {
       	distanceLower+=5; 
    	showDistance();
    }
    public void higherMinusLongClicked(View v)
    {
       	distanceHigher-=5; 
    	showDistance();   	
    }
    public void higherPlusLongClicked(View v)
    {
       	distanceHigher+=5; 
    	showDistance();   	
     }



}
