package com.conq.omkar.mynotepad;

/*
* This is NewNote Activity.
* This activity will be created for
* getting title from user.
* Content of the Activity :-
* 1. TextView
* 2. EditText
* 3. Save Button
* 4. Back Button
* */

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class NewNote extends AppCompatActivity {

    public static final String TITLE = "KeyOne";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        //Save Button for save the title of new note.
        Button btnNewSave = findViewById(R.id.btnNewSave);
        btnNewSave.setOnClickListener(this :: onNewSave);

        //Back Button for comeback to previous activity.
        Button btnNewBack = findViewById(R.id.btnNewBack);
        btnNewBack.setOnClickListener(this::onNewBack);
    }


    //After pressing built-in back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearText();
        finish();
    }

    private void onNewSave(View view) {

        Class classEditNote = EditNoteN.class;
        //Get title from EditText
        String txtName =((EditText)findViewById(R.id.txtName)).getText().toString();

        if((txtName).isEmpty()){
            //If user not entered anything in EditText
            Toast.makeText(this, "Name Cannot be Empty",Toast.LENGTH_SHORT).show();

        }
        else {

            //Initializing Database details
            String table = NotepadDb.notepad_info.TAB_NAME;
            String notetitle = NotepadDb.notepad_info.COL_TITLE;
            String dbname = NotepadDb.DB_NAME;
            int dbversion = NotepadDb.DB_VERSION;

            //Initialising helper class for GETTING DATABASE DETAILS
            DbHelper helper = new DbHelper(this,dbname,null,dbversion);

            //Get database object for EXECUTING DB QUERIES
            SQLiteDatabase db = helper.getWritableDatabase();

            //Declare List for Storing ResultSet
            ArrayList<HashMap<String, String>> titleList = new ArrayList<>();

            //Database Query
            String query = "SELECT "+notetitle+" FROM "+ table;

            //Cursor for converts database ResultSet to Java readable data
            Cursor cursor = db.rawQuery(query,null);

            //Getting all list titles in database
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    HashMap<String, String> title = new HashMap<>();
                    title.put("title", cursor.getString(cursor.getColumnIndex(notetitle)));
                    titleList.add(title);
                }
            }
            cursor.close();

            //Getting User Entered Title
            HashMap<String, String> test = new HashMap<>();
            test.put("title",txtName);

            //Toast Dialog for User to change title if it saved previously in Database
            if(titleList.contains(test))
                Toast.makeText(this, "Name Already Used",Toast.LENGTH_SHORT).show();

            else {
                //Send Unique Title to next Activity
                Intent intent = new Intent(this, classEditNote);
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, txtName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

    }


    //pressing back button in the activity
    private void onNewBack(View view) {
        clearText();
        finish();
    }

    //Clear EditText field before going to previous activity
    public void clearText(){
        ((EditText)findViewById(R.id.txtName)).setText("");
    }

}
