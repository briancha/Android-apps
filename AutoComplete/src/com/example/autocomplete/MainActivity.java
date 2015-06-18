// shows words from a dictionary as you type in the text box

package com.example.autocomplete;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

	// outlet: text box
	AutoCompleteTextView myTextBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// link outlet with element
		myTextBox = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		
		// takes dictionary words from an XML file and places them in an array 
		String[] myArray = getResources().getStringArray(R.array.word_dict);
		
		final ArrayList<String> list = new ArrayList<String>();
		
		// takes words from the String array myArray and places them into an ArrayList
		for (String word: myArray) {
			list.add(word);
		}
		
		// adapter allows you to display specific words from the list
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, list);
		
		// when the user types 2 characters into the text box, the app begins to show suggestions
		myTextBox.setThreshold(2);
		
		myTextBox.setAdapter(adapter);
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
}
