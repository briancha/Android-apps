// Displays name of dwarf after clicking on an image of one

package com.example.gridtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView myLabel;
	GridView myGrid;
	
	private static final String[] items = {"Happy", "Grumpy", "Sneezy", "Sleepy", "Bashful", "Dopey", "Doc"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set outlets equal to elements in layout
		myLabel = (TextView) findViewById(R.id.textView1);
		myGrid = (GridView) findViewById(R.id.gridView1);
		
		// allows GridView to work with images
		myGrid.setAdapter(new ImageAdapter(this));
		
		// allows Grid to listen for clicks
		myGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// sets the text of the label to the name of the right dwarf
				myLabel.setText(items[position]);
			}
			
		});
	}
	
	public class ImageAdapter extends BaseAdapter {
		private Context mContext;		
		
		public ImageAdapter (Context c) {
			mContext = c;
		}
		
		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ImageView imageView;
			
			// if view is empty, displays the images of the dwarves
			if (view == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(8, 8, 8, 8);
			} else {
				imageView = (ImageView) view;
			}
			
			imageView.setImageResource(mThumbIds[position]);
			
			return imageView;
		}
		
		private int[] mThumbIds = {
				// pictures of seven dwarves
				R.drawable.happy, R.drawable.grumpy, R.drawable.sneezy, R.drawable.sleepy, R.drawable.bashful, R.drawable.dopey, R.drawable.doc
		};
		
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
