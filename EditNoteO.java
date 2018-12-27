package com.conq.omkar.mynotepad;

/*
 * This is EditNoteO activity.
 * It means separate edit activity when we edit old Note.
 * In this activity we will get note from user.
 * Content of the Activity :-
 * 1. TextView
 * 2. EditText
 * 3. Save Button
 * 4. Back Button
 * */
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EditNoteO extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note_o);

        ArrayList<HashMap<String , String>> arrayList;
        arrayList = (ArrayList<HashMap<String , String>>)getIntent().getSerializableExtra(ListNote.KEY);
        HashMap<String,String> hashMap = arrayList.get(0);
        String[] string = new String[hashMap.size()];
        int i = 0;
        for (HashMap<String, String> hash : arrayList) {
            for (String current : hash.values()) {
                string[i] = current;
                i++;
            }
        }
        ((TextView)findViewById(R.id.txvEditOName)).setText(string[0]);
        ((EditText)findViewById(R.id.txtEditONote)).setText(string[1]);

        findViewById(R.id.btnEditOSave).setOnClickListener(this::onSave);
        findViewById(R.id.btnEditOBack).setOnClickListener(this::onBack);

    }

    //pressing back button in the activity
    private void onBack(View view) {
        finish();
    }

    private void onSave(View view) {

        String txtEditNote = ((EditText) findViewById(R.id.txtEditONote)).getText().toString();

        if(txtEditNote.isEmpty()){
            //If user not entered anything in EditText
            Toast.makeText(this, "Note cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        else {
            //Initializing Database details
            String table = NotepadDb.notepad_info.TAB_NAME;
            String NoteTitle = NotepadDb.notepad_info.COL_TITLE;
            String NoteText = NotepadDb.notepad_info.COL_TEXT;
            String dbname = NotepadDb.DB_NAME;
            int dbversion = NotepadDb.DB_VERSION;

            String title = ((TextView) findViewById(R.id.txvEditOName)).getText().toString();

            //Initialising helper class for GETTING DATABASE DETAILS
            DbHelper helper = new DbHelper(this, dbname, null, dbversion);

            //Get database object for EXECUTING DB QUERIES
            SQLiteDatabase db = helper.getWritableDatabase();

            //Store updated title and text of note in Database.
            ContentValues cVals = new ContentValues();
            cVals.put(NoteText, txtEditNote);
            db.update(table, cVals, NoteTitle + " = ?", new String[]{String.valueOf(title)});
            db.close();

            Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();

            //Move to Home Activity
            Intent intent3 = new Intent(this, Home.class);
            startActivity(intent3);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
