/*
 * Copyright (C) 2013 Code Here Now - A subsidiary of Mobs & Geeks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.codeherenow.sicalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;

public class SICalculatorActivity extends Activity implements View.OnClickListener {

    private Button calculate;

    private int years;

    private double principal;
    private double interest;
    private double simpleInterest;

    static final String LOG_TAG = "SI calculator";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sicalculator);

        Log.v(LOG_TAG, "Hello World");

        calculate = (Button) findViewById(R.id.calculate);
        // allows calculate button to be clicked
        calculate.setOnClickListener(this);
        //System.out.println("test output");

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            // when seekbar is changed, sets # years to value of bar
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                years = i;
                // changes description of # years
                ((TextView) findViewById(R.id.years)).setText(i + " Year(s)");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onClick(View view) {
        // gets number entered into principal
        String principalString = ((EditText) findViewById(R.id.editPrincipal)).getText().toString();
        principal = Double.parseDouble(principalString);
        // rounds number to 2 decimal places
        principal = (double) Math.round(principal * 100) / 100;
        //System.out.println("principal worked");

        // gets number entered into interest
        String interestString = ((EditText) findViewById(R.id.editInterest)).getText().toString();
        interest = Double.parseDouble(interestString);
        // rounds number to 2 decimal places
        interest = (double) Math.round(interest * 100) / 100;
        //System.out.println("interest worked");

        // calculates simple interest
        simpleInterest = principal * (interest / 100) * years;
        // rounds number to 2 decimal places
        simpleInterest = (double) Math.round(simpleInterest * 100)/ 100;

        // changes text view to display simple interest based on principal, interest, and # years
        ((TextView) findViewById(R.id.sentence)).setText("The interest for $" + principal + " at a rate of " + interest + "% for " +
        years + " year(s) is $" + simpleInterest + ".");
    }
}
