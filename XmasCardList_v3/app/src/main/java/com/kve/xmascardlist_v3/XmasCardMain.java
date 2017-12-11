package com.kve.xmascardlist_v3;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmasCardMain extends ListActivity {

    public static String[] africa;
    public static String[] downUnder;

    TextView tvContactId;
    TextView tvContactName;

    DBTools dbTools = new DBTools(XmasCardMain.this);

    private FilterPrefs filterPrefs;
    private boolean prefsChanged;

    //  http://www.vogella.com/tutorials/AndroidListView/article.html#androidlists_view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmas_card_main);
        //	Set up our preferences arrays from resources
        //	Can't do this in final as mixing static and .. whativer the opp of static is
        africa = getResources().getStringArray(R.array.Africa);
        downUnder = getResources().getStringArray(R.array.DownUnder);


        //  We are going to need a list view


    }

    @Override
    protected void onResume() {
        super.onResume();
        //	Refresh the contacts every time we get back
        showContacts();
    }

    private void showContacts() {
        if (null == filterPrefs || prefsChanged) {
            updatePrefs();
        }

        ArrayList<HashMap<String, Object>> contactList = dbTools.getContacts(filterPrefs);
        //ArrayList<HashMap<String, Object>> contactList = dbTools.getContacts();



        ListAdapter adapter = new SimpleAdapter(XmasCardMain.this, contactList, R.layout.contact_entry,
                new String[] 	{"contactId", "firstName"},
                new int[]		{R.id.tvContactId, R.id.tvContactName});
        setListAdapter(adapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        tvContactId 	= (TextView) v.findViewById(R.id.tvContactId);
        tvContactName 	= (TextView) v.findViewById(R.id.tvContactName);
        String contactIdValue = tvContactId.getText().toString();
        Intent editIntent = new Intent(getApplication(), EditContact.class);
        editIntent.putExtra("contactId", contactIdValue);
        startActivity(editIntent);

    }

    public void showAddContact(View view) {
        Intent addIntent = new Intent(getApplication(), AddContact.class);
        startActivity(addIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.xmas_card_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.mnu_preferences) {
            startActivity(new Intent(this, PreferencesActivity.class));
            prefsChanged = true;

        } else if (id == R.id.mnu_mark_all_unsent) {
            dbTools.markAllXmasNotSent();
            prefsChanged = true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updatePrefs() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (null == filterPrefs) {
            filterPrefs = new FilterPrefs();
        }
        filterPrefs.UpdateFilterPrefs(
                sharedPrefs.getString("region", "All"),
                sharedPrefs.getString("last_sent", "All"),
                sharedPrefs.getString("xmas_received", ""),
                sharedPrefs.getString("xmas_sent", "All"),
                sharedPrefs.getString("favourites", "All"));

        prefsChanged = false;
    }


}