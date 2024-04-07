package carl.coding;

import static java.util.Arrays.sort;

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

import java.util.List;
import java.util.Random;


public class YatzyActivity extends AppCompatActivity {

    // Declare instance variables
    Button yatzyRollButton;
    Drawable yatzy1Drawable;
    Drawable yatzy2Drawable;
    Drawable yatzy3Drawable;
    Drawable yatzy4Drawable;
    Drawable yatzy5Drawable;
    Drawable yatzy6Drawable;
    Handler handler;  // Create a handler to update the UI with delays
    ImageView yatzyImageView1;
    ImageView yatzyImageView2;
    ImageView yatzyImageView3;
    ImageView yatzyImageView4;
    ImageView yatzyImageView5;
    ImageView[] imageViewArray;
    boolean[] holdArray;
    Random randomizer;
    TextView yatzyRollResult;
    Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yatzy);

        // Initiate randomizer
        randomizer = new Random();

        // Initiate vibrator
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initiate handler
        handler = new Handler();

        // Load dice images
        yatzy1Drawable = ContextCompat.getDrawable(this, R.drawable.dice_1_512);
        yatzy2Drawable = ContextCompat.getDrawable(this, R.drawable.dice_2_512);
        yatzy3Drawable = ContextCompat.getDrawable(this, R.drawable.dice_3_512);
        yatzy4Drawable = ContextCompat.getDrawable(this, R.drawable.dice_4_512);
        yatzy5Drawable = ContextCompat.getDrawable(this, R.drawable.dice_5_512);
        yatzy6Drawable = ContextCompat.getDrawable(this, R.drawable.dice_6_512);

        // Get widget by ID
        yatzyRollResult = findViewById(R.id.yatzyRollResult);
        yatzyRollButton = findViewById(R.id.yatzyRollButton);

        yatzyImageView1 = findViewById(R.id.yatzyImageView1);
        yatzyImageView2 = findViewById(R.id.yatzyImageView2);
        yatzyImageView3 = findViewById(R.id.yatzyImageView3);
        yatzyImageView4 = findViewById(R.id.yatzyImageView4);
        yatzyImageView5 = findViewById(R.id.yatzyImageView5);
        imageViewArray = new ImageView[]{yatzyImageView1, yatzyImageView2, yatzyImageView3, yatzyImageView4, yatzyImageView5};

        holdArray = new boolean[]{false, false, false, false, false};
        //boolean yatzyImageView1.holdBool = new ;
        yatzyImageView1.setClickable(true);
        yatzyImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        yatzyImageView1.setImageDrawable(yatzy1Drawable);
        yatzyImageView2.setImageDrawable(yatzy2Drawable);
        yatzyImageView3.setImageDrawable(yatzy3Drawable);
        yatzyImageView4.setImageDrawable(yatzy4Drawable);
        yatzyImageView5.setImageDrawable(yatzy5Drawable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void yatzyRoll(View view) {
        // Disable button during animation
        yatzyRollButton.setEnabled(false);

        // Vibrate to provide feedback to the user
        vib.vibrate(300);

        int[] resultInt = {1, 2, 3, 4, 5}; // Variable to hold the rolling and previous value

        // Roll through some results to create tension
        for (int i = 0; i <= 6; i++) {

            // Delay for each step
            int delay = i*70 + (i+10)*(i+10);

            // Schedule all steps with specified delays
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for(int j=0; j<5; j++) {
                        // Reduce scale to show rolling
                        imageViewArray[j].setScaleX((float) 0.7);
                        imageViewArray[j].setScaleY((float) 0.7);
                        // Roll a new random value
                        resultInt[j] = randomizer.nextInt(6) + 1;
                        // Update image based on resulting random int
                        resultToImage(imageViewArray[j], resultInt[j]);
                    }
                }
            }, delay);
        }

        // Delay the final update to display the resulting value
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sort(resultInt);
                int sum = 0;
                for(int k=0; k<5; k++) {
                    // Enlarge to signal animation end
                    imageViewArray[k].setScaleX((float) 0.8);
                    imageViewArray[k].setScaleY((float) 0.8);
                    // Add to sum
                    sum += resultInt[k];
                    // Update image based on resulting random int
                    resultToImage(imageViewArray[k], resultInt[k]);
                }
                // Generate string and update TextView
                String sumText = "Sum: " + String.valueOf(sum);
                yatzyRollResult.setText(sumText);

                // Reactivate Button
                yatzyRollButton.setEnabled(true);
            }
        }, 6*70 + (6+10)*(6+10)); // Delay to wait for the animation to complete, same as above
    }

    private void resultToImage(ImageView imageView, int rollResult) {

        if (rollResult == 1) {
            imageView.setImageDrawable(yatzy1Drawable);
        } else if (rollResult == 2) {
            imageView.setImageDrawable(yatzy2Drawable);
        } else if (rollResult == 3) {
            imageView.setImageDrawable(yatzy3Drawable);
        } else if (rollResult == 4) {
            imageView.setImageDrawable(yatzy4Drawable);
        } else if (rollResult == 5) {
            imageView.setImageDrawable(yatzy5Drawable);
        } else if (rollResult == 6) {
            imageView.setImageDrawable(yatzy6Drawable);
        }
    }
}