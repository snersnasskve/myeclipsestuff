package com.kve.xmascardlist2;


import java.util.HashMap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;


public class EditContact extends ContactActivity implements OnClickListener {
	String contactId;

	DBTools dbTools = new DBTools(EditContact.this);

	TableRow trEditButtons;
	Button btnNavigate;
	Button btnEditSave;
	Button btnDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		trEditButtons 	= (TableRow) 	findViewById(R.id.tableRowEditButtons);
		btnNavigate		= (Button)		findViewById(R.id.btnNav);
		btnEditSave		= (Button)		findViewById(R.id.btnEdit);
		btnDelete		= (Button)		findViewById(R.id.btnDelete);

		tvTitleBar.setText(R.string.edit_contact);
		trEditButtons.setVisibility(View.VISIBLE);
		
		btnNavigate.setOnClickListener(this);
		btnEditSave.setOnClickListener(this);
		btnDelete.	setOnClickListener(this);
	
		Intent recIntent = getIntent();
		contactId = recIntent.getExtras().getString("contactId");

		HashMap<String, Object> contacts = dbTools.getContact(contactId);
		if (contacts.size() > 0)
		{
		etFirstName.	setText((String)contacts.get("firstName"));
		etEmailAddress.	setText((String)contacts.get("email"));
		etAddress.		setText((String)contacts.get("address"));
		etAreaCode.		setText((String)contacts.get("areaCode"));
		actvCountry.	setText((String)contacts.get("country"));
		etXRecYears.	setText((String)contacts.get("xmasRec"));
		etLastSent.		setText((String)contacts.get("lastSent"));
		etKids.			setText((String)contacts.get("kids"));
		if (1 == (Integer)contacts.get("xmasSent")){
			cbXSent.		setChecked(true);
		} else {
			cbXSent.		setChecked(false);
		}
		if (1 == (Integer)contacts.get("xmasEmail")){
			cbXEmail.		setChecked(true);
		} else {
			cbXEmail.		setChecked(false);
		}
		if (1 == (Integer)contacts.get("favourite")){
			cbFavourite.	setChecked(true);
		} else {
			cbFavourite.	setChecked(false);
		}
		}
		else
		{
			//BUG
			finish();
		}
	}

	private void editContact(View view)
	{
		HashMap<String, Object> queryValuesMap = new HashMap<String, Object>();
		queryValuesMap.put("contactId",	contactId);
		queryValuesMap.put("firstName",	etFirstName.	getText().toString());
		queryValuesMap.put("lastName", 	"");
		queryValuesMap.put("phone", 	"");
		queryValuesMap.put("email", 	etEmailAddress.	getText().toString());
		queryValuesMap.put("address", 	etAddress.		getText().toString());
		queryValuesMap.put("areaCode", 	etAreaCode.		getText().toString());
		queryValuesMap.put("country", 	actvCountry.	getText().toString());
		queryValuesMap.put("xmasRec", 	etXRecYears.	getText().toString());
		queryValuesMap.put("xmasSent", 	(cbXSent.		isChecked() ? 1 : 0));
		queryValuesMap.put("xmasEmail", (cbXEmail.		isChecked() ? 1 : 0));
		queryValuesMap.put("favourite", (cbFavourite.	isChecked() ? 1 : 0));
		queryValuesMap.put("lastSent",	etLastSent.		getText().toString());
		queryValuesMap.put("kids",		etKids.			getText().toString());

		dbTools.updateContact(queryValuesMap);
		finish();

	}

	private void callMailActivity( ) {
		// replyIntent = new Intent(getApplication(), XmasCardMain.class);
		//startActivity(replyIntent);
		finish();

	}

	private void deleteContact(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Delete Alert");
		builder.setMessage("Are you sure you want to delete "+ etFirstName.	getText().toString());
		
		builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			  @Override
             public void onClick(DialogInterface dialog,int id) {
                 // if this button is clicked, close
                 // current activity
     			dbTools.deleteContact(contactId);
     			             }
           });
		builder.setNegativeButton("No", null);
	builder.create().show();
		//finish();


	}
	
	public void goBack(View view)
	{
		finish();
	
	}

	private void navigate(View view)
	{
		try {
			String address = "";
			if (etAddress.getText().toString().compareTo("") != 0)
			{
				address = 	address + etAddress.getText().toString() + ",";
			}
			if (etAreaCode.getText().toString().compareTo("") != 0)
			{
				address = 	address + etAreaCode.getText().toString() + ",";
			}
			if (actvCountry.getText().toString().compareTo("") != 0)
			{
				address = 	address + actvCountry.getText().toString();
			}
			address = address.replace(' ', '+');
			Intent geoIntent = new Intent(
					android.content.Intent.ACTION_VIEW, Uri
					.parse("geo:0,0?q=" + address));
			startActivity(geoIntent);
		} catch (Exception e) {
			Log.e("maperr", e.toString());
		}


	}


	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() { /*DialogInterface called while setting the AlertDialog Buttons */
		public void onClick(DialogInterface dialog, int which) {

			//Here you can perform functions of Alert Dialog Buttons as shown


			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				dbTools.deleteContact(contactId);
				finish();
				break;


			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				break;
			}
		}
	};

	@Override
	public void onClick(View view) {
	    switch(view.getId()) {
        case R.id.btnNav:
        	navigate		(view);
        	break;
        case R.id.btnEdit:
        	editContact		(view);
        	break;
        case R.id.btnDelete:
        	deleteContact	(view);
        	break;
       }	}

	

}
