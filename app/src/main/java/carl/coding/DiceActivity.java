package carl.coding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Vibrator;

import java.util.Random;


public class DiceActivity extends AppCompatActivity {

    // Declare instance variables
    Button rollButton;
    Drawable dice1Drawable;
    Drawable dice2Drawable;
    Drawable dice3Drawable;
    Drawable dice4Drawable;
    Drawable dice5Drawable;
    Drawable dice6Drawable;
    Handler handler;  // Create a handler to update the UI with delays
    ImageView diceImageView1;
    Random randomizer;
    TextView rollResult;
    Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        // Initiate randomizer
        randomizer = new Random();

        // Initiate vibrator
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initiate handler
        handler = new Handler();

        // Load dice images
        dice1Drawable = ContextCompat.getDrawable(this, R.drawable.dice_1_512);
        dice2Drawable = ContextCompat.getDrawable(this, R.drawable.dice_2_512);
        dice3Drawable = ContextCompat.getDrawable(this, R.drawable.dice_3_512);
        dice4Drawable = ContextCompat.getDrawable(this, R.drawable.dice_4_512);
        dice5Drawable = ContextCompat.getDrawable(this, R.drawable.dice_5_512);
        dice6Drawable = ContextCompat.getDrawable(this, R.drawable.dice_6_512);

        // Get widget by ID
        rollResult = findViewById(R.id.rollResult);
        rollButton = findViewById(R.id.rollButton);
        diceImageView1 = findViewById(R.id.ImageView1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void roll(View view) {
        // Disable button during animation
        rollButton.setEnabled(false);

        // Vibrate to provide feedback to the user
        vib.vibrate(300);

        int[] resultInt = {1, 2}; // Variable to hold the rolling and previous value

        // Roll through some results to create tension
        for (int i = 0; i <= 6; i++) {

            // Delay for each step
            int delay = 70*i + (i+10)*(i+10);

            // Schedule all steps with specified delays
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Reduce scale to communicate rolling
                    diceImageView1.setScaleX((float) 0.8);
                    diceImageView1.setScaleY((float) 0.8);
                    // Roll a new random value
                    resultInt[1] = resultInt[0];
                    resultInt[0] = randomizer.nextInt(6) + 1;
                    // Make sure next roll is different from the previous
                    if (resultInt[0] == resultInt[1]) {
                        if (resultInt[0] == 6) {
                            resultInt[0] = 1;
                        } else {
                            resultInt[0] += 1;
                        }
                    }

                    //String resultingString = String.valueOf(resultInt[0]);
                    //rollResult.setText(resultingString);
                    resultToImage(resultInt[0]);

                }
            }, delay);
        }

        // Delay the final update to display the resulting value
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //String resultingString = String.valueOf(resultInt[0]);
                //rollResult.setText(resultingString);
                diceImageView1.setScaleX((float) 1);
                diceImageView1.setScaleY((float) 1);
                resultToImage(resultInt[0]);
                rollButton.setEnabled(true);  // Reactivate Button
            }
        }, 6*70 + (6+10)*(6+10)); // Delay to wait for the animation to complete, same as above
    }

    private void resultToImage(int rollResult) {

        if (rollResult == 1) {
            diceImageView1.setImageDrawable(dice1Drawable);
        } else if (rollResult == 2) {
            diceImageView1.setImageDrawable(dice2Drawable);
        } else if (rollResult == 3) {
            diceImageView1.setImageDrawable(dice3Drawable);
        } else if (rollResult == 4) {
            diceImageView1.setImageDrawable(dice4Drawable);
        } else if (rollResult == 5) {
            diceImageView1.setImageDrawable(dice5Drawable);
        } else if (rollResult == 6) {
            diceImageView1.setImageDrawable(dice6Drawable);
        }
    }
}