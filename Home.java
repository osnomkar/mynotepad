package com.conq.omkar.mynotepad;

/*
* This Activity is the Home of Notepad App.
* Contents in this Activity :-
* 1. New Button
* 2. Edit Button
* */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //New Button for Make a new note
        Button btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this :: onNew);

        //Edit Button for edit old note
        Button btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(this :: onEdit);

    }

    private void onNew(View view) {

        //Moving from this activity to NewNote Activity
        Class classNew = NewNote.class;
        Intent intentNew = new Intent(this, classNew);
        startActivity(intentNew);

    }

    private void onEdit(View view) {

        //Moving from this activity to ListNote Activity
        Class classList = ListNote.class;
        Intent intentList = new Intent(this, classList);
        startActivity(intentList);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
