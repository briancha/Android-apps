// Displays different Android elements and how they can be used, i.e. buttons, checkboxes, switches, radio buttons, seekbars, text fields

package com.example.basicwidgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.example.project2.R;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	// elements to be used later on in program
	Button button1, updateLabel;
	TextView button1Label, radioLabel, sliderLabel, textBoxText;
	CheckBox checkbox;
	Switch switch1;
	RadioButton radio1, radio2, radio3;
	SeekBar slider;
	EditText textBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// links elements that are in the layout XML file with Java variables
		button1 = (Button) findViewById(R.id.button1);
		button1Label = (TextView) findViewById(R.id.button1Label);
		checkbox = (CheckBox) findViewById(R.id.checkbox);
		switch1 = (Switch) findViewById(R.id.switch1);
		radio1 = (RadioButton) findViewById(R.id.radio1);
		radio2 = (RadioButton) findViewById(R.id.radio2);
		radio3 = (RadioButton) findViewById(R.id.radio3);
		radioLabel = (TextView) findViewById(R.id.radioLabel);
		slider = (SeekBar) findViewById(R.id.slider);
		sliderLabel = (TextView) findViewById(R.id.sliderLabel);
		textBox = (EditText) findViewById(R.id.textBox);
		textBoxText = (TextView) findViewById(R.id.textBoxText);
		updateLabel = (Button) findViewById(R.id.updateLabel);

		// allows checkbox to "listen" for changes
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			// When checkbox is checked or unchecked, the app displays that			
			public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
				if (isChecked) {
					checkbox.setText("This checkbox is: checked");
				} else {
					checkbox.setText("This checkbox is: unchecked");
				}
			}
		});

		// allows switch to "listen" for changes
		switch1.setOnCheckedChangeListener(this);

		// allows radio buttons to "listen" for changes
		RadioButton[] radioButtons = { radio1, radio2, radio3 };
		for (RadioButton button : radioButtons) {
			button.setOnCheckedChangeListener(this);
		}
		
		slider.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			// when seekbar is moved, the progress is displayed as a percentage
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				sliderLabel.setText(progress + "%");
			}

			public void onStartTrackingTouch(SeekBar seekBar) {}
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

	// When Button 1 is pressed, display # times button has been clicked.
	int clicks = 0;
	public void button1Pressed(View v) {
		clicks++;
		String button1Text = "Button 1 clicked ";
		
		// changes whether "time" is singular or plural based on # clicks.
		if (clicks == 1) {
			button1Label.setText(button1Text + clicks + " time.");
		} else {
			button1Label.setText(button1Text + clicks + " times.");
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		
		// If switch is clicked on or off, the text besides it displays that.
		case R.id.switch1:
			if (isChecked) {
				switch1.setText("This switch is: on");
			} else {
				switch1.setText("This switch is: off");
			}
			break;
		
		// When a radio button is selected, the text displays which one.
		case R.id.radio1:
		case R.id.radio2:
		case R.id.radio3:
			if (isChecked) {
				radioLabel.setText(buttonView.getText() + " selected");
			}
			break;
		}
	}
	
	// When the "Update Label" button is pressed, the TextView to the left of it displays the text that was entered in the EditText field
	public void updateLabelButtonPressed(View v) {
		textBoxText.setText(textBox.getText());
	}
}
