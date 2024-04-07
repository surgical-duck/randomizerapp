package carl.coding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CategoriesSetupActivity extends AppCompatActivity {

    private List<String> elementList;
    private LinearLayout elementListLayout;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_setup);

        elementList = new ArrayList<>();
        elementListLayout = findViewById(R.id.element_list_layout);
        headerText = findViewById(R.id.textView2);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElement();
            }
        });

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategoriesActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        addElement();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void addElement() {

        EditText editText = new EditText(this);
        editText.setHint("New category");
        editText.requestFocus(); // Set focus to the text field of the newly added element
        editText.setSingleLine(true); // Set the text field to be a single line
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addElement(); // Trigger adding element when Enter key is pressed
                    return true;
                }
                return false;
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editText.setLayoutParams(params);
        editText.setBackgroundResource(android.R.drawable.edit_text);
        editText.setPadding(16, 16, 16, 16);

        Button removeButton = new Button(this);
        removeButton.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);

        LinearLayout.LayoutParams removeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        removeParams.setMarginStart(16);
        removeButton.setLayoutParams(removeParams);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeElement(editText);
            }
        });

        LinearLayout elementLayout = new LinearLayout(this);
        LinearLayout.LayoutParams elementParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        elementParams.setMargins(0, 16, 0, 16);
        elementLayout.setLayoutParams(elementParams);
        elementLayout.setOrientation(LinearLayout.HORIZONTAL);
        elementLayout.addView(editText);
        elementLayout.addView(removeButton);

        elementListLayout.addView(elementLayout);

//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.HORIZONTAL);
//        layout.addView(editText);
//        layout.addView(removeButton);
//
//        elementListLayout.addView(layout);
    }

    private void removeElement(View view) {
        LinearLayout layout = (LinearLayout) view.getParent();
        elementListLayout.removeView(layout);
    }

    private void startCategoriesActivity() {
        removeEmptyElements();

        if (elementList.size() == 0){
            addElement();
            return;
        }

        Intent intent = new Intent(this, CategoriesActivity.class);
        intent.putStringArrayListExtra("elements", (ArrayList<String>) elementList);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void removeEmptyElements() {
        elementList.clear();
        ArrayList<Integer> intList = new ArrayList<>();

        // Generate list of elements to bring into next activity
        for (int i = 0; i < elementListLayout.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) elementListLayout.getChildAt(i);
            EditText editText = (EditText) layout.getChildAt(0);
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                elementList.add(editText.getText().toString().trim());
            }
            else {
                // Remember index of empty element EditTexts
                intList.add(i);
            }
        }

        // Remove elements with empty EditText
        if (!intList.isEmpty()){
            intList.sort(Collections.reverseOrder());
            int listSize = intList.size();
            for(int i = 0; i < listSize; i++){
                View childToRemove = elementListLayout.getChildAt(intList.get(i));
                elementListLayout.removeView(childToRemove);
            }
        }
    }
}
