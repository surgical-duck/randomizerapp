package carl.coding;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;


public class DiceDynamicActivity extends AppCompatActivity {

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
        rollResult = findViewById(R.id.yatzyRollResult);
        rollButton = findViewById(R.id.yatzyRollButton);
        diceImageView1 = findViewById(R.id.yatzyImageView1);
        diceImageView1.setImageDrawable(dice1Drawable);
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

        // Variable to hold the rolled values
        int[] resultInt = {1, 2, 3, 4, 5, 6};

        // Roll through some results to create tension
        for (int i = 1; i <= 10; i++) {

            // Delay for each step
            int delay = 50*i + (i+10)*(i+10);

            // Schedule all steps with specified delays
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int j=0; j<6; j++) {
                        // Roll a new random value
                        resultInt[j] = randomizer.nextInt(6) + 1;

                        //String resultingString = String.valueOf(resultInt[0]);
                        //rollResult.setText(resultingString);
                        resultToImage(diceImageView1, resultInt[j]);
                    }
                }
            }, delay);
        }

        // Delay the final update to display the resulting value
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //String resultingString = String.valueOf(resultInt[0]);
                //rollResult.setText(resultingString);
                for (int k=0; k<6; k++) {
                    resultToImage(diceImageView1, resultInt[k]);
                }
                rollButton.setEnabled(true);  // Reactivate Button
            }
        }, 50*10 + (10+10)*(10+10)); // Delay to wait for the animation to complete, same as above
    }

    private void resultToImage(ImageView diceImageView, int rollResult) {

        if (rollResult == 1) {
            diceImageView.setImageDrawable(dice1Drawable);
        } else if (rollResult == 2) {
            diceImageView.setImageDrawable(dice2Drawable);
        } else if (rollResult == 3) {
            diceImageView.setImageDrawable(dice3Drawable);
        } else if (rollResult == 4) {
            diceImageView.setImageDrawable(dice4Drawable);
        } else if (rollResult == 5) {
            diceImageView.setImageDrawable(dice5Drawable);
        } else if (rollResult == 6) {
            diceImageView.setImageDrawable(dice6Drawable);
        }

    }
}