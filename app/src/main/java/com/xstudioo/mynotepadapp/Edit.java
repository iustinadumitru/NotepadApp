package com.xstudioo.mynotepadapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText nTitle, nContent;
    Calendar c;
    String todaysDate;
    String currentTime;
    long nId;
    Button italicButton, boldButton;
    Integer italic_count;
    Integer bold_count;
    String style;
    Button colorButton;
    String color;
    List<String> available_colors = Arrays.asList("#000000", "#008000", "#FF0000", "#FF69B4", "#FFA500");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent i = getIntent();
        nId = i.getLongExtra("ID", 0);
        SimpleDatabase db = new SimpleDatabase(this);
        Note note = db.getNote(nId);

        final String title = note.getTitle();
        String content = note.getContent();
        nTitle = findViewById(R.id.noteTitle);
        nContent = findViewById(R.id.noteDetails);
        nTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        nTitle.setText(title);
        nContent.setText(content);

        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));

        style = note.getStyle();
        switch (note.getStyle()) {
            case "italic": {
                nContent.setTypeface(null, Typeface.ITALIC);
                break;
            }
            case "bold": {
                nContent.setTypeface(null, Typeface.BOLD);
                break;
            }
        }

        italicButton = (Button) findViewById(R.id.button_italic2);
        boldButton = (Button) findViewById(R.id.button_bold2);

        italic_count = 0;
        bold_count = 0;

        italicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bold_count = 0;
                italic_count += 1;
                if (italic_count % 2 == 1) {
                    nContent.setTypeface(null, Typeface.ITALIC);
                    style = "italic";
                } else {
                    nContent.setTypeface(null);
                    style = "";
                }
            }
        });

        boldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                italic_count = 0;
                bold_count += 1;
                if (bold_count % 2 == 1) {
                    nContent.setTypeface(null, Typeface.BOLD);
                    style = "bold";
                } else {
                    nContent.setTypeface(null);
                    style = "";
                }
            }
        });

        colorButton = (Button) findViewById(R.id.button_colors2);
        color = note.getColor();
        nContent.setTextColor(Color.parseColor(color));
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = available_colors.get(new Random().nextInt(available_colors.size()));
                nContent.setTextColor(Color.parseColor(color));
            }
        });
    }

    private String pad(int time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Note note = new Note(nId, nTitle.getText().toString(), nContent.getText().toString(), todaysDate, currentTime, style, color);

            SimpleDatabase sDB = new SimpleDatabase(getApplicationContext());
            long id = sDB.editNote(note);

            goToMain();
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
