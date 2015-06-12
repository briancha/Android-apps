package com.example.colortest;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

	// create variables to store layout XML elements later on
	View myView;
	TextView labelRed, labelGreen, labelBlue;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // set layout elements to variables, so they can be manipulated
        myView = (View) findViewById(R.id.view1);
        labelRed = (TextView) findViewById(R.id.labelRed);
        labelGreen = (TextView) findViewById(R.id.labelGreen);
        labelBlue = (TextView) findViewById(R.id.labelBlue);
        
        // set initial view background color to black
        myView.setBackgroundColor(Color.argb(255, 0, 0, 0));
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
    
    public void buttonPressed(View view) {
    	// generates random integer from 0-255 for RGB color value    	
    	Random rnd = new Random();
    	int red = rnd.nextInt(255);
    	int green = rnd.nextInt(255);
    	int blue = rnd.nextInt(255);
    	
    	// the 3 random integers from 0-255 are then used to generate a random color
    	myView.setBackgroundColor(Color.argb(255, red, green, blue));
    	
    	// displays RGB numerical values underneath "Change Color" button
    	labelRed.setText("" + red);
    	labelGreen.setText("" + green);
    	labelBlue.setText("" + blue);    	
    }
}
