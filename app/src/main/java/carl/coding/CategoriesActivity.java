package carl.coding;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Random;


public class CategoriesActivity extends AppCompatActivity {

    private List<String> elementList;
    private TextView resultText;
    private Button pickButton;
    private Random random;
    private Handler handler;
    private Runnable rollingRunnable;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        elementList = getIntent().getStringArrayListExtra("elements");
        resultText = findViewById(R.id.result_text);
        pickButton = findViewById(R.id.pick_button);

        random = new Random();
        handler = new Handler();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickNextElement();
            }
        });
    }

    private void pickNextElement() {
        pickButton.setEnabled(false);
        rollingRunnable = new Runnable() {
            @Override
            public void run() {
                int randomIndex = random.nextInt(elementList.size());
                String randomElement = elementList.get(randomIndex);
                resultText.setText(randomElement);
                resultText.setTextColor(Color.parseColor("#000000"));
                resultText.setTextSize(45);
                pickButton.setEnabled(true);
            }
        };

        vibrator.vibrate(300);
        rollThroughElements();
    }

    private void rollThroughElements() {
        int duration = 800;
        int interval = 100;
        int iterations = duration / interval;
        // final int lastIndex = elementList.size() - 1;
        resultText.setTextSize(35);
        resultText.setTextColor(Color.parseColor("#666666"));

        handler.postDelayed(new Runnable() {
            int iteration = 0;
            int currentIndex = 0;

            @Override
            public void run() {
                if (iteration < iterations) {
                    resultText.setText(elementList.get(currentIndex));
                    currentIndex = (currentIndex + 1) % elementList.size();
                    iteration++;
                    handler.postDelayed(this, interval);
                } else {
                    handler.post(rollingRunnable);
                }
            }
        }, interval);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
