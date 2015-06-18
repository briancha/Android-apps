// Tip Calculator: Takes in total check size, number of parties to split the bill with, and tip percentage 
// to calculate how much each person should pay in total and in terms of tips

package com.tipcalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	// outlets: for referencing elements in the layout later on
	EditText checkField;
	SeekBar numPeopleSeekBar;
	TextView numPeople;
	SeekBar percentTipSeekBar;
	TextView percentTip;
	Button calculateButton;
	TextView totalLabel, totalTip, totalAmount, message;
	
	// initialize the number of parties to 2 and tip percentage to 20% when application first loads 
	int numberParties = 2;
	int tipPercent = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// finds elements in layout and saves them to outlets, so they can be referenced and changed
		checkField = (EditText) findViewById(R.id.checkField);
		numPeopleSeekBar = (SeekBar) findViewById(R.id.numPeopleSeekBar);
		numPeople = (TextView) findViewById(R.id.numPeople);
		percentTipSeekBar = (SeekBar) findViewById(R.id.percentTipSeekBar);
		percentTip = (TextView) findViewById(R.id.percentTip);
		calculateButton = (Button) findViewById(R.id.calculateButton);
		totalLabel = (TextView) findViewById(R.id.totalLabel);
		totalTip = (TextView) findViewById(R.id.totalTip);
		totalAmount = (TextView) findViewById(R.id.totalAmount);
		message = (TextView) findViewById(R.id.message);
		
		// when the seek bar for the number of parties is changed
		numPeopleSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// the number of parties is calculated by dividing the progress of the seek bar by 10, i.e. 20% = 2 parties
				numberParties = progress / 10;
				
				// prevents user from setting the number of parties to 0
				// if # parties is zero, increments 1
				if (numberParties == 0) {
					numberParties++;
				}
				
				// singular vs. plural: if number of parties is 1, makes "party" singular.
				if (numberParties == 1) {
					numPeople.setText(numberParties + " party");
				} else {
					numPeople.setText(numberParties + " parties");
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
		});
		
		// when tip percentage seek bar is changed
		percentTipSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// rounds tip percentage to the nearest 5 or 10, i.e. 5%, 10%, 15%, 20%
				// so it is easier for the user to use
				tipPercent = (int) ((progress / 5) + 0.5);
				tipPercent *= 5;
				percentTip.setText(tipPercent + "%");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	// when calculate button is pressed
	public void calculate(View v) {
		// initializes totals and tip amounts
		double totalAmountDouble = 0;
		double totalTipDouble = 0;
		double perPersonAmount = 0;
		double perPersonTip = 0;
		
		// makes sure Total Check Size field has a number
		try {
			// pre tip check amount
			String amountString = checkField.getText().toString();
			double preTipAmount = Double.parseDouble(amountString);
			
			// calculates tip and total amount based on percent entered
			totalTipDouble = preTipAmount * tipPercent / 100;
			totalAmountDouble = preTipAmount + totalTipDouble;
			
			// divides total number based on number of parties
			perPersonAmount = totalAmountDouble / numberParties;
			perPersonTip = totalTipDouble / numberParties;
			
			// initialize strings that will display amounts
			String totalTipString = "";
			String totalAmountString = "";
			String perPersonTipString = "";
			String perPersonAmountString = "";
			
			// parallel arrays of corresponding strings and doubles to easily change the values
			String[] amountStrings = {totalTipString, totalAmountString, perPersonTipString, perPersonAmountString};
			double[] amounts = {totalTipDouble, totalAmountDouble, perPersonTip, perPersonAmount};
			
			// makes sure numbers have 2 decimal places
			DecimalFormat twoDecimalPlaces = new DecimalFormat("0.00");
			
			// makes sure numbers have no decimal places
			DecimalFormat noDecimalPlaces = new DecimalFormat("0");
			
			// iterates through the 4 numerical amounts
			for (int i = 0; i < amountStrings.length; i++) {
				// formats all decimal numbers so they have 2 decimal places
				amountStrings[i] = twoDecimalPlaces.format(amounts[i]);

				int lastDigitBeforeDecimalPoint = amountStrings[i].length() - 2; 
				
				// but if a number is a whole number, removes all numbers after the decimal point
				if (amountStrings[i].substring(lastDigitBeforeDecimalPoint).equals("00")) {
					amountStrings[i] = noDecimalPlaces.format(amounts[i]);					
				}
			}
			
			// displays total, tip, and amount each party should pay in tip and total
			totalLabel.setText("Total: ");
			totalTip.setText("Tip: $" + amountStrings[0]);
			totalAmount.setText("Amount: $" + amountStrings[1]);
			message.setText("Each party should pay a $" + amountStrings[2] + " tip and a total of $" + amountStrings[3] + "." );
		} 
		
		// if Total Check Size field is empty, prompts user to enter a number 
		catch (NumberFormatException e) {
			message.setText("Please enter a number in the Total Check Size field." );
		}
		
		
	}
}
