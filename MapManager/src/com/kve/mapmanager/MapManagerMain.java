package com.kve.mapmanager;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


//	Android 17  - 5.30 mins
public class MapManagerMain extends Activity implements OnClickListener {

	private Button btnShowMap;
	private EditText etAddress;
	private ImageButton ibNext;
	private ImageButton ibPrevious;

	private int currentAddressCounter = 0;
	
	ArrayList<HashMap<String, Object>> recents;
	ArrayList<HashMap<String, Object>> favourites;
	
	DBTools dbTools = new DBTools(MapManagerMain.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
 
 
    	   	setContentView(R.layout.activity_map_manager_main);
    	   	etAddress 	= (EditText) 	findViewById(R.id.etAddress);
    		btnShowMap	= (Button)		findViewById(R.id.btnShowMap);
    		ibNext 		= (ImageButton)	findViewById(R.id.ibNext);
    		ibPrevious 	= (ImageButton)	findViewById(R.id.ibPrevious);

    		btnShowMap.	setOnClickListener(this);
    		ibNext.		setOnClickListener(this);
    		ibPrevious.	setOnClickListener(this);

    		recents = dbTools.getRecent();
   			showAddress();
  

     	btnShowMap.setEnabled(true);
      }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_manager_main, menu);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShowMap:
		   	btnShowMap.setEnabled(false);
		    			showMap(v);

			break;

		case R.id.ibNext:
			currentAddressCounter++;
			if (currentAddressCounter >= (recents.size() - 1))
			{
				currentAddressCounter = (recents.size() - 1);
			}
			showAddress();
			break;
			
		case R.id.ibPrevious:
			currentAddressCounter--;
			if (currentAddressCounter <= 0)
			{
				currentAddressCounter = 0;
			}
			showAddress();
			break;

		default:
			break;
		}		
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	 	btnShowMap.setEnabled(true);
	 	  	}


	@Override
	protected void onPause() {
		super.onPause();
		//	If there isn't a recent with this name then add it
		String currentPlaceName = etAddress.getText().toString();
		if (currentPlaceName.length() > 2)
		{
			dbTools.insertRecent(etAddress.getText().toString());
		}
	}


	public void showMap(View v) {
		String address = etAddress.getText().toString();
		address = address.replace(' ', '+');
		Intent geoIntent = new Intent(
				android.content.Intent.ACTION_VIEW, Uri
						.parse("geo:0,0?q=" + address));
		startActivity(geoIntent);
		//finish();		I don't want it to finish
	}
	
	private void showAddress()
	{
		if (currentAddressCounter == 0)
		{
			ibPrevious.setEnabled(false);

		}
		else
		{
			ibPrevious.setEnabled(true);
		}
		if (currentAddressCounter == (recents.size() - 1))
		{
			ibNext.setEnabled(false);

		}
		else
		{
			ibNext.setEnabled(true);
		}
  		if (recents.size() > 0)
		{
  			etAddress.setText(recents.get(currentAddressCounter).get("placeName").toString());
		}
		
	}
}
