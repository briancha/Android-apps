// Displays list of Star Trek characters alongside icons of their factions
// When you click one of their names, the name of their faction is displayed

package com.example.fancylist;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	TextView selection;

	// parallel arrays of name and their respective faction
	private static final String[] faction = { "federation", "ferengi",
			"federation", "klingon", "romulan", "klingon", "romulan",
			"ferengi", "ferengi", "romulan", "romulan", "klingon",
			"federation", "federation", "romulan", "federation", "romulan",
			"klingon", "klingon", "federation", "klingon" };
	private static final String[] name = { "Kirk", "Bok", "Spock", "Warf",
			"Bochra", "Martok", "Almak", "Brunt", "Dr. Farek", "Koval",
			"Bochra", "Bo'rak", "Scottie", "Cartwright", "Nanclus", "Data",
			"Kimara Cretak", "Duras", "Atul", "Picard", "Azetbur" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		selection = (TextView) findViewById(R.id.textView1);
		setListAdapter(new IconAdapter());
	}
	
	
	// when a list item is clicked, the top TextView is set to that person's faction
	public void onListItemClick(ListView parent, View view, int position, long id) {
		selection.setText(faction[position]);
	}
	
	class IconAdapter extends ArrayAdapter <String> {
		IconAdapter() {
			super(MainActivity.this, R.layout.row, name);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
						
			View row = convertView;
			
			// if a row is empty, fill it with content
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, parent, false);
			}
			
			ViewHolder holder = (ViewHolder) row.getTag();
			
			if (holder == null) {
				holder = new ViewHolder(row);
				row.setTag(holder);
			}
						
			/*TextView label = (TextView) row.findViewById(R.id.label);
			
			ImageView icon = (ImageView) row.findViewById(R.id.icon);*/
			
			// sets row text to a name in the array
			holder.label.setText(name[position]);
			
			// changes the text color of the name based on the faction the person is in
			// adds symbol for the faction to the left of the name
			if (faction[position].equals("klingon")) {
				holder.label.setTextColor(Color.RED);
				holder.icon.setImageResource(R.drawable.klingon);
			}
			if (faction[position].equals("federation")) {
				holder.label.setTextColor(Color.BLUE);
				holder.icon.setImageResource(R.drawable.federation);
			}
			if (faction[position].equals("romulan")) {
				holder.label.setTextColor(Color.GRAY);
				holder.icon.setImageResource(R.drawable.romulan);
			}
			if (faction[position].equals("ferengi")) {
				holder.label.setTextColor(Color.GREEN);
				holder.icon.setImageResource(R.drawable.ferengi);
			}
			
			return row;		
		}
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
