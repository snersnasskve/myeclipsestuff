package com.ramblerswalks3.sners.ramblerswalk3;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ramblerswalks3.sners.ramblerswalk3.ui.ramblersmain.RamblersMainFragment;

// https://www.youtube.com/watch?v=UqtsyhASW74
public class RamblersMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ramblers_main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ramblers_fragment_container, RamblersMainFragment.newInstance())
                    .commitNow();
        }
    }


}