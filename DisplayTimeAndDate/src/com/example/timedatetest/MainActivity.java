// Displays date and time, allows you to change date and time using Date and Time Picker Dialogs
// Also contains a chronometer

package com.example.timedatetest;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {

	// outlets to be used later in the program
	Calendar cal = Calendar.getInstance();
	TextView label1, label2, label3, label4, label5;
	DateFormat fmtDateAndTime = DateFormat.getTimeInstance();
	Chronometer myChrono;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set outlets equal to elements in the layout
		label1 = (TextView) this.findViewById(R.id.textView1);
		label2 = (TextView) this.findViewById(R.id.textView2);
		label3 = (TextView) this.findViewById(R.id.textView3);
		label4 = (TextView) this.findViewById(R.id.textView4);
		label5 = (TextView) this.findViewById(R.id.textView5);
		
		myChrono = (Chronometer) this.findViewById(R.id.chronometer1);
		
		updateInfo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Time Picker Dialog allows you to change the time displayed
	public void changeTime(View view) {
		new TimePickerDialog(this, t, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
	}
	
	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		
		// when the time is changed in the Time Picker Dialog, that time is set as the time displayed
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
			cal.set(Calendar.MINUTE, minute);
			updateInfo();
		}
	};
	
	// Date Picker Dialog allows you to change the date displayed
	public void changeDate(View view) {
		new DatePickerDialog(this, d, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
	}
	
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		
		// when the date is changed in the Date Picker Dialog, that date is set as the date displayed
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, monthOfYear);
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateInfo();			
		}
	};
	
	// generic method to update time based on changed dates/times
	public void updateInfo() {
		label1.setText(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US) + ", " 
				+ cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) + " "
				+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + ", "
				+ String.valueOf(cal.get(Calendar.YEAR)));
		label2.setText(fmtDateAndTime.format(cal.getTime()));
		label3.setText("");
		label4.setText("");
		label5.setText("");
	}
	
	// methods to start, stop, and reset the chronometer (sort of works like a stopwatch) 
	public void startCR(View view) {
		myChrono.start();
	}
	
	public void stopCR(View view) {
		myChrono.stop();
	}
	
	public void resetCR(View view) {
		myChrono.setBase(SystemClock.elapsedRealtime());
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
}
