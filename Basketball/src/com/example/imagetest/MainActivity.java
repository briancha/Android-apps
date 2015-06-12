package com.example.imagetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {

	ImageView ball;
	float ballX, ballY = 0; // handles x and y coordinates of ball
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // sets ImageView equal to element in layout XML
        ball = (ImageView) findViewById(R.id.ball);
        
        // initializes ball to (0, 0) location
        ball.setX(ballX); 
        ball.setY(ballY);
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
    
    // When arrow buttons are clicked, moves the ball 25 pixels in that direction.
    public void downButtonPress(View view) {
    	ballY += 25;
    	ball.setY(ballY);
    }
    	
	public void upButtonPress(View view) {
		ballY -= 25;
		ball.setY(ballY);
	}
	
	public void leftButtonPress(View view) {
	    ballX -= 25;
	    ball.setX(ballX);
	}
	
	public void rightButtonPress(View view) {
		ballX += 25;
		ball.setX(ballX);
	}
}
