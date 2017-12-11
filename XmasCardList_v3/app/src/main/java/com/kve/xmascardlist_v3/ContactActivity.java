package com.kve.xmascardlist_v3;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by PC on 27/11/2017.
 */

public class ContactActivity extends Activity {


    //	View elements
    TextView tvTitleBar;
    EditText etFirstName;
    EditText etEmailAddress;
    EditText etAddress;
    EditText etAreaCode;
    EditText etXRecYears;
    CheckBox cbXSent;
    CheckBox cbXEmail;
    CheckBox cbFavourite;
    EditText etLastSent;
    EditText etKids;
    AutoCompleteTextView actvCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_edit_contact);

        tvTitleBar		= (TextView) findViewById(R.id.tvTitleBar);
        etFirstName		= (EditText) findViewById(R.id.etFirstName);
        etEmailAddress	= (EditText) findViewById(R.id.etEmailAddress);
        etAddress		= (EditText) findViewById(R.id.etAddress);
        etAreaCode		= (EditText) findViewById(R.id.etAreaCode);
        etXRecYears		= (EditText) findViewById(R.id.etXRecYears);
        cbXSent			= (CheckBox) findViewById(R.id.cbXSent);
        cbXEmail		= (CheckBox) findViewById(R.id.cbXEmail);
        cbFavourite		= (CheckBox) findViewById(R.id.cbFavourite);
        etLastSent		= (EditText) findViewById(R.id.etLastSent);
        actvCountry		= (AutoCompleteTextView) findViewById(R.id.actvCountry);
        etKids			= (EditText) findViewById(R.id.etKids);

        setUpCountryAutocomplete();
    }


    protected void setUpCountryAutocomplete()
    {
        //	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //		R.layout.list_item, R.array.countries);
        ArrayAdapter<String> countriesAdapter =
                new ArrayAdapter<String>(this, R.layout.list_item,  getResources().getStringArray( R.array.countries));
        actvCountry.setAdapter(countriesAdapter);

        actvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(getApplicationContext(), "The winner is:" + arg0.getAdapter().getItem(arg2), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
