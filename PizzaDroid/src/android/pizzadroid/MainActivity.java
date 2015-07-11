// Pizza Droid: try to eat as many pizzas as possible by moving the droid up and down by tapping on the screen. Pizzas appear and disappear in random places.

package android.pizzadroid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.helicopterdroid.R;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	
	// outlets and variables
	ImageView droidIntro, droidGame, pizzaImage, pizzaEnd, noPizza;
	Button playButton;
	ViewFlipper flipper;
	int x = 0, y = 0, pizzas, munch = -1, yay = -1, no = -1, time, cycles;
	Timer timer = new Timer();
	int screenX = 0, screenY = 0, scoreAmt = 0;
	TimerTask updateGame;
	TextView scoreGame, bonus, scoreText, newHighScore, pizzasText, pizzaHighScore, pizzasForEveryone;
	Time start = new Time();
	Rect droidRect, pizzaRect;
	Paint drawPaint = new Paint();
	List<Integer> scores = new ArrayList<Integer>();
	List<Integer> numPizzas = new ArrayList<Integer>();
	SoundPool sound;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// match outlets to elements in XML
		droidIntro = (ImageView) this.findViewById(R.id.droidIntro);
		droidGame = (ImageView) this.findViewById(R.id.droidGame);
		playButton = (Button) this.findViewById(R.id.playButton);
		flipper = (ViewFlipper) this.findViewById(R.id.viewFlipper1);
		scoreGame = (TextView) this.findViewById(R.id.scoreGame);
		bonus = (TextView) this.findViewById(R.id.bonus);
		scoreText = (TextView) this.findViewById(R.id.scoreText);
		newHighScore = (TextView) this.findViewById(R.id.newHighScore);
		pizzaHighScore = (TextView) this.findViewById(R.id.pizzaHighScore);
		pizzasForEveryone = (TextView) this.findViewById(R.id.pizzasForEveryone);
		pizzasText = (TextView) this.findViewById(R.id.pizzasText);
		pizzaImage = (ImageView) this.findViewById(R.id.pizzaImage);
		pizzaEnd = (ImageView) this.findViewById(R.id.pizzaEnd);
		noPizza = (ImageView) this.findViewById(R.id.noPizza);
		
		// set sound
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		sound = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		AssetManager assetManager = getAssets();
		
		// "munch" sound for when droid eats a pizza
		try {
			// finds sound file
			AssetFileDescriptor munchDescriptor = assetManager.openFd("munch.mp3");
			// loads sound from above
			munch = sound.load(munchDescriptor, 1);
			
			// yay sound
			AssetFileDescriptor yayDescriptor = assetManager.openFd("yay.mp3");
			yay = sound.load(yayDescriptor, 1);
			
			// no sound
			AssetFileDescriptor noDescriptor = assetManager.openFd("no.mp3");
			no = sound.load(noDescriptor, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	// this method is called when player chooses to play or replay the game
	public void startGame(View v) {
		// get size of display		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		// set droid icon x-coordinate to about the middle of the screen
		x = (int) 10;
		// set droid icon y-coordinate to same position as droid picture on intro screen
		y = (int) droidIntro.getY();
		
		// get width of screen
		screenX = size.x;
		
		// get height of screen
		// there are 200 pixels between bottom of screen and very edge of the emulator
		screenY = size.y - 200;
		
		// set the coordinates of the droid icon to the above coordinates 
		droidGame.setX(x);
		droidGame.setY(y);
		
		// game loop
		final int FPS = 40;
		updateGame = new UpdateGameTask();
		timer.scheduleAtFixedRate(updateGame, 0, 1000/FPS);
		
		// creates rectangles to be used for collisions between droid and pizzas
		droidRect = new Rect(x, y, x + 96, y + 96);
		pizzaRect = new Rect(1, 1, 1, 1);
		
		// clears out labels on the game and end screen just in case
		newHighScore.setText("");
		bonus.setText("");
		pizzaHighScore.setText("");
		pizzasForEveryone.setText("");
		
		// makes all pictures on the end screen invisible
		pizzaEnd.setVisibility(View.INVISIBLE);
		noPizza.setVisibility(View.INVISIBLE);
		
		// sets score and number of pizzas to zero before the start of the game
		scoreAmt = 0;
		pizzas = 0;
		
		// moves flipper to the game screen
		flipper.setDisplayedChild(1);
	}
	
	boolean touched = false;
	// this method gets called when you touch the screen
	public boolean onTouchEvent(MotionEvent event) {
		// checks if finger is pressing on the screen
		if (event.getAction() ==  MotionEvent.ACTION_MOVE || event.getAction() ==  MotionEvent.ACTION_DOWN) {
			// sets boolean touched to true, which causes the droid to move up in the run() method 
			touched = true;
		}
		return true;
	}
	
	class UpdateGameTask extends TimerTask {
		
		@Override
		public void run() {
			MainActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					
					// runs when no fingers are touching the screen
					// lowers the droid by 3 pixels per run cycle
					y += 3;
					
					// sets new position for the droid and its rectangle
					droidGame.setY(y);
					droidRect.set(x, y, x + 96, y + 96);
					
					// runs when finger is touching screen
					if (touched) {
						// raises droid by 30 pixels/click
						y -= 30;
						
						// sets new position for the droid and its rectangle
						droidGame.setY(y);
						droidRect.set(x, y, x + 96, y + 96);
					}
					
					// sets boolean touched back to false
					touched = false;
					
					// increments score by 1 for every run cycle
					scoreAmt++;
					
					// updates the displayed score
					scoreGame.setText("Score: " + scoreAmt);
					
					// increments time by 1 for every run cycle
					time++;
					
					// after 10 run cycles
					if (time == 10) {
						// cycles++; // debugging
						
						// bonus displays whether the droid got a pizza
						// set to blank before new pizza is created
						bonus.setText("");
						
						// gets y-coordinate of droid icon and y-coordinates 150 pixels above and below
						int droidLoc = (int) droidGame.getY();
						int upperLimit = droidLoc + 150;
						int lowerLimit = droidLoc - 150 - 96; // extra 96 pixels to compensate for the height of the droid icon
						
						Random random = new Random();
						
						// generates random new y-coordinate for the pizza
						// but ensures that the pizza is at least 150 pixels away from the droid icon
						int pizzaY;
						do {
							pizzaY = random.nextInt(screenY - 100);
						} while (pizzaY > lowerLimit && pizzaY < upperLimit);
						
						// sets the pizza to this the same x-coordinate (so it is in line with the droid and can be "eaten") 
						// and newly generated y-coordinate
						pizzaImage.setX(x);
						pizzaImage.setY(pizzaY);
						pizzaImage.setVisibility(View.VISIBLE); // makes pizza visible
						
						// sets pizza rectangle at the same location as pizza image
						pizzaRect.set(x, pizzaY, x + 90, pizzaY + 90);									
					}
					
					// after 90 run cycles
					if (time == 90) {
						// pizza image is set to invisible 
						// and moved far away from droid icon x-coordinate to prevent intersection
						pizzaImage.setVisibility(View.INVISIBLE);
						pizzaImage.setX(500);
						pizzaRect.set(500, 0, 0, 0);
					}
					
					// after 100 run cycles, the time is set back to 0 to repeat the same pizza generation process
					if (time == 100) {
						time = 0;
					}
					
					// when the droid and pizza rectangle collide
					if (Rect.intersects(pizzaRect, droidRect)) {
						// increment number of pizzas eaten
						pizzas++;
						
						// pizza image is set to invisible 
						// and moved far away from droid icon x-coordinate to prevent 2nd intersection
						pizzaImage.setVisibility(View.INVISIBLE); 
						pizzaImage.setX(500);
						pizzaRect.set(500, 0, 0, 0);
						
						// munch sound is played
						sound.play(munch, 1, 1, 0, 0, 1);
						
						// score incremented by 100
						scoreAmt += 100;
						
						// notifies user that they got the pizza
						bonus.setText("You got the pizza! +100");															
					}
					
					// when the droid moves past the top or bottom of the screen,
					// stops the game loop and flips to the game over screen
					if ((y + droidGame.getHeight()) > screenY || y < 0) {
						// cancels Timer Task
						updateGame.cancel();
						
						// displays score
						scoreText.setText("Score: " + scoreAmt);
						
						// used to check if new score is a high score
						boolean isGreater = false;
						
						// if there are no previous scores, new score is automatically a high score
						if (scores.size() == 0) {
							isGreater = true;
						}
						
						// goes through an ArrayList of previous scores to determine if the new score is greater than all previous scores
						for (int score: scores) {
							if (scoreAmt > score) {
								isGreater = true;
							} else {
								isGreater = false;
							}							
						}
						
						// if score is the highest score
						if (isGreater) {
							// displays "new high score!"
							newHighScore.setText("New High Score!");
						} 
						
						// if score is not the highest score
						else {
							// prompts player to try again
							newHighScore.setText("Please try again");
						}
						
						// adds new score to ArrayList of all scores
						scores.add(scoreAmt);
						
						// displays the number pizzas eaten
						pizzasText.setText("Pizzas: " + pizzas);
						
						// used to check if new pizza score is highest score
						boolean pizzaHigh = false;
						
						// automatically highest score if there is no previous playing history
						if (numPizzas.size() == 0) {
							pizzaHigh = true;
						}
						
						// goes through an ArrayList of previous scores to determine if the new score is greater than all previous scores
						for (int numPizza: numPizzas) {
							if (pizzas > numPizza) {
								pizzaHigh = true;
							} else {
								pizzaHigh = false;
							}							
						}
						
						// if the new pizza score is the highest score, outputs "high score", pizza icon, and "pizzas for everyone!"
						if (pizzaHigh) {
							sound.play(yay, 1, 1, 0, 0, 1); // plays yay sound
							pizzaHighScore.setText("New Pizza High Score!");
							pizzaEnd.setVisibility(View.VISIBLE);
							noPizza.setVisibility(View.INVISIBLE);
							pizzasForEveryone.setText("Pizzas for everyone!");
						} 
						
						// if both the new pizza score and the new score is not the highest score, outputs no pizza icon, and "no pizzas for you!"
						else if (!isGreater){
							sound.play(no, 1, 1, 0, 0, 1); // plays no sound
							pizzaHighScore.setText("");
							noPizza.setVisibility(View.VISIBLE);
							pizzaEnd.setVisibility(View.INVISIBLE);
							pizzasForEveryone.setText("No pizzas for you!");
						}
						
						// adds new pizza score to ArrayList of all pizza scores
						numPizzas.add(pizzas);						
						
						// flips to the end (game over) screen
						flipper.setDisplayedChild(2);
						
						// resets score and pizza score to 0
						scoreAmt = 0;
						pizzas = 0;
					}
				}
			});
		}
	}
}
