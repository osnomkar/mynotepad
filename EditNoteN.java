package com.conq.omkar.mynotepad;

/*
* This is EditNoteN activity.
* It means separate edit activity when we create New Note.
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditNoteN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //Getting Saved title from previous activity
        Intent intentResponsible = getIntent();
        Bundle bundleResponsible = intentResponsible.getExtras();
        String txvEditName = bundleResponsible.getString(NewNote.TITLE);

        //Setting Entered Title to TextView
        ((TextView)findViewById(R.id.txvEditNName)).setText(txvEditName);

        //Back Button for comeback to previous activity.
        Button BtnEditBack = findViewById(R.id.btnEditNBack);
        BtnEditBack.setOnClickListener(this::onEditBack);

        //Save Button for save the title and note to the database.
        Button BtnEditSave = findViewById(R.id.btnEditNSave);
        BtnEditSave.setOnClickListener(this::onEditSave);

    }

    //After pressing built-in back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearText();
        finish();
    }

    private void onEditSave(View view) {

        //Get text from EditText
        String txtEditNote = ((EditText) findViewById(R.id.txtEditNNote)).getText().toString();


        if (txtEditNote.isEmpty()) {
            //If user not entered anything in EditText
            Toast.makeText(this, "Note cannot be Empty", Toast.LENGTH_SHORT).show();
        } else {

            //Initializing Database details
            String table = NotepadDb.notepad_info.TAB_NAME;
            String NoteTitle = NotepadDb.notepad_info.COL_TITLE;
            String NoteText = NotepadDb.notepad_info.COL_TEXT;
            String dbname = NotepadDb.DB_NAME;
            int dbversion = NotepadDb.DB_VERSION;

            String  title = ((TextView)findViewById(R.id.txvEditNName)).getText().toString();

            //Initialising helper class for GETTING DATABASE DETAILS
            DbHelper helper = new DbHelper(this, dbname, null, dbversion);

            //Get database object for EXECUTING DB QUERIES
            SQLiteDatabase db = helper.getWritableDatabase();

            //Store title and text of note in Database.
            ContentValues cValues = new ContentValues();
            cValues.put(NoteTitle, title);
            cValues.put(NoteText, txtEditNote);
            db.insert(table, null, cValues);
            db.close();

            Toast.makeText(this, "Note Saved Successfully", Toast.LENGTH_SHORT).show();

            //Move to Home Activity
            Intent intent2 = new Intent(this, Home.class);
            startActivity(intent2);

        }

    }

    //pressing back button in the activity
    private void onEditBack(View view) {
        clearText();
        finish();
    }

    //Clear EditText field before going to previous activity
    public void clearText() {
        ((EditText) findViewById(R.id.txtEditNNote)).setText("");
    }

}


