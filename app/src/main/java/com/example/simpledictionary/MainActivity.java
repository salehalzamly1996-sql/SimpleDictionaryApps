package com.example.simpledictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WordAdapter.OnWordActionListener {

    public static final String EXTRA_WORD = "word";
    public static final String EXTRA_MEANING = "meaning";
    public static final String EXTRA_POSITION = "position";

    private static final int ADD_REQUEST = 1;
    private static final int EDIT_REQUEST = 2;

    private static final String WORDS_KEY = "words";
    private static final String MEANINGS_KEY = "meanings";

    private ArrayList<DictionaryEntry> entries;
    private WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entries = new ArrayList<>();

        if (savedInstanceState != null) {
            ArrayList<String> words = savedInstanceState.getStringArrayList(WORDS_KEY);
            ArrayList<String> meanings = savedInstanceState.getStringArrayList(MEANINGS_KEY);

            if (words != null && meanings != null) {
                for (int i = 0; i < words.size(); i++) {
                    entries.add(new DictionaryEntry(words.get(i), meanings.get(i)));
                }
            }
        } else {
            addDefaultWords();
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WordAdapter(entries, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab_add_word);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });
    }

    private void addDefaultWords() {
        entries.add(new DictionaryEntry("Android System", "نظام أندرويد هو نظام تشغيل للموبايل"));
        entries.add(new DictionaryEntry("Android Studio", "أندرويد ستوديو هو برنامج نستخدمه لعمل تطبيقات أندرويد"));
        entries.add(new DictionaryEntry("Java", "جافا هي لغة برمجة نستخدمها لكتابة كود التطبيق"));
        entries.add(new DictionaryEntry("XML", "XML نستخدمها لتصميم شكل الشاشات في التطبيق"));
        entries.add(new DictionaryEntry("Activity", "Activity هي شاشة من شاشات التطبيق"));
        entries.add(new DictionaryEntry("Intent", "Intent نستخدمه للانتقال من شاشة إلى شاشة ثانية"));
        entries.add(new DictionaryEntry("RecyclerView", "RecyclerView نستخدمه لعرض قائمة كلمات أو عناصر"));
        entries.add(new DictionaryEntry("EditText", "EditText هو مكان يكتب فيه المستخدم نص"));
        entries.add(new DictionaryEntry("Button", "Button هو زر يضغط عليه المستخدم لتنفيذ أمر"));
        entries.add(new DictionaryEntry("Toast", "Toast هي رسالة صغيرة تظهر للمستخدم"));
    }

    @Override
    public void onWordClick(int position) {
        DictionaryEntry entry = entries.get(position);

        Intent intent = new Intent(this, AddEditActivity.class);
        intent.putExtra(EXTRA_WORD, entry.getWord());
        intent.putExtra(EXTRA_MEANING, entry.getMeaning());
        intent.putExtra(EXTRA_POSITION, position);

        startActivityForResult(intent, EDIT_REQUEST);
    }

    @Override
    public void onWordLongClick(int position) {
        entries.remove(position);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, R.string.toast_word_deleted, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            String word = data.getStringExtra(EXTRA_WORD);
            String meaning = data.getStringExtra(EXTRA_MEANING);

            if (requestCode == ADD_REQUEST) {
                entries.add(new DictionaryEntry(word, meaning));
                adapter.notifyDataSetChanged();

                Toast.makeText(this, R.string.toast_word_added, Toast.LENGTH_SHORT).show();
            }

            if (requestCode == EDIT_REQUEST) {
                int position = data.getIntExtra(EXTRA_POSITION, -1);

                if (position != -1) {
                    entries.set(position, new DictionaryEntry(word, meaning));
                    adapter.notifyDataSetChanged();

                    Toast.makeText(this, R.string.toast_word_updated, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> meanings = new ArrayList<>();

        for (int i = 0; i < entries.size(); i++) {
            words.add(entries.get(i).getWord());
            meanings.add(entries.get(i).getMeaning());
        }

        outState.putStringArrayList(WORDS_KEY, words);
        outState.putStringArrayList(MEANINGS_KEY, meanings);
    }
}