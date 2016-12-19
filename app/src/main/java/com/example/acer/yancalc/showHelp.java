package com.example.acer.yancalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class showHelp extends AppCompatActivity {

    private Button standard;
    private Button scientific;
    private TextView scs_text;
    private int getMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Set variables for buttons and text in Help section
        standard = (Button) findViewById(R.id.back_btn);
        scientific = (Button) findViewById(R.id.back_scientific_btn);
        scs_text = (TextView) findViewById(R.id.scientific_text);
        getMode = getIntent().getExtras().getInt("mode");
    }

    protected void onStart()
    {
        super.onStart();
        //overridePendingTransition(R.anim.transition_in, R.anim.transition_out);

        // Decide if display scientific help or not
        // If mode is is 2, show scientific text
        if (getMode == 2)
        {
            standard.setVisibility(View.GONE);
            scientific.setVisibility(View.VISIBLE);
            scs_text.setVisibility(View.VISIBLE);
        }
    }

    // Go back to Standard Calculator
    public void goBack(View view)
    {
        Intent switchMode = new Intent(showHelp.this, Standard.class);
        startActivity(switchMode);
        this.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
    }

    // Go back to Scientific Calculator
    public void goBackScientific(View view)
    {
        Intent switchMode = new Intent(showHelp.this, Scientific.class);
        startActivity(switchMode);
        this.overridePendingTransition(R.animator.transition_in, R.animator.transition_out);
    }
}
