package com.kve.xmascardlist_v3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import java.util.HashMap;

/**
 * Created by PC on 27/11/2017.
 */

public class AddContact extends ContactActivity implements View.OnClickListener {

    TableRow trAddButtons;
    Button btnAddSave;

    DBTools dbTools = new DBTools(AddContact.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        trAddButtons 	= (TableRow) 	findViewById(R.id.tableRowAddButtons);
        btnAddSave 		= (Button) 		findViewById(R.id.btnAddSave);

        trAddButtons.setVisibility(View.VISIBLE);
        tvTitleBar.setText(R.string.add_contact);
        btnAddSave.setOnClickListener(this);
    }


    private void callMailActivity(View view) {
        finish();

    }

    @Override
    public void onClick(View view) {
        HashMap<String, Object> queryValuesMap = new HashMap<String, Object>();
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

        dbTools.insertContact(queryValuesMap);
        this.callMailActivity(view);

    }
}
