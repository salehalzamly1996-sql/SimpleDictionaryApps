package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText wordEditText;
    private EditText meaningEditText;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        TextView title = findViewById(R.id.screen_title);
        wordEditText = findViewById(R.id.edit_word);
        meaningEditText = findViewById(R.id.edit_meaning);
        Button saveButton = findViewById(R.id.save_button);

        Intent intent = getIntent();

        position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1);

        if (position == -1) {
            title.setText(R.string.add_word_title);
        } else {
            title.setText(R.string.edit_word_title);

            String word = intent.getStringExtra(MainActivity.EXTRA_WORD);
            String meaning = intent.getStringExtra(MainActivity.EXTRA_MEANING);

            wordEditText.setText(word);
            meaningEditText.setText(meaning);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWord();
            }
        });
    }

    private void saveWord() {
        String word = wordEditText.getText().toString().trim();
        String meaning = meaningEditText.getText().toString().trim();

        if (word.isEmpty() || meaning.isEmpty()) {
            Toast.makeText(this, R.string.toast_missing_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.EXTRA_WORD, word);
        resultIntent.putExtra(MainActivity.EXTRA_MEANING, meaning);
        resultIntent.putExtra(MainActivity.EXTRA_POSITION, position);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}