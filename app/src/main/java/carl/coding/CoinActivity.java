package carl.coding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CoinActivity extends AppCompatActivity {

    Button flipButton;
    Drawable headsDrawable;
    Drawable tailsDrawable;
    Handler handler;
    ImageView coinImageView;
    int animationDuration = 500;
    ObjectAnimator animator1;
    ObjectAnimator animator2;
    Random randomizer;
    TextView coinFlipResult;
    Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);

        // Initiate randomizer
        randomizer = new Random();

        // Initiate vibrator
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initiate handler
        handler = new Handler();

        // Get widget by ID
        coinImageView = findViewById(R.id.coinImageView);
        coinFlipResult = findViewById(R.id.coinFlipResult);
        flipButton = findViewById(R.id.flipButton);

        headsDrawable = ContextCompat.getDrawable(this, R.drawable.coin_heads);
        tailsDrawable = ContextCompat.getDrawable(this, R.drawable.coin_tails);
        coinImageView.setImageDrawable(headsDrawable);
        animator1 = ObjectAnimator.ofFloat(coinImageView, "rotationY", 0f, 360*4+90f);
        animator2 = ObjectAnimator.ofFloat(coinImageView, "rotationY", -90f, 0f);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void flip(View view) {
        // Disable button during animation
        flipButton.setEnabled(false);

        // Reset results TextView widget
        coinFlipResult.setText(" ");

        // Vibrate to provide feedback to the user
        vib.vibrate(300);

        // Flip 1 or 2
        int flipResult = randomizer.nextInt(2);

        // Schedule coin flip animation
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator1.setDuration(animationDuration);
                animator1.start();
            }
        }, 10);

        // Schedule displaying result
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator2.setDuration(200);
                animator2.start();
                String flipResultString = resultToTextAndImage(flipResult);
                coinFlipResult.setText(flipResultString);
                // Reactivate Button
                flipButton.setEnabled(true);
            }
        }, animationDuration);
    }

    private String resultToTextAndImage(int oneOrTwo) {
        String returnString;
        if (oneOrTwo == 1) {
            returnString = "HEADS";
            coinImageView.setImageDrawable(headsDrawable);
        } else {
            returnString = "TAILS";
            coinImageView.setImageDrawable(tailsDrawable);
        }
        return returnString;
    }
}