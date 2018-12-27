package com.conq.omkar.mynotepad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ListNote extends AppCompatActivity {

    public static final String KEY = "key";
    public static final String KEY_RENAME ="keyrename";
    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

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
        String query = "SELECT "+notetitle+" FROM "+ table;

        //Cursor for converts database ResultSet to Java readable data
        Cursor cursor = db.rawQuery(query,null);
        //Getting all list titles in database
        while (cursor.moveToNext()){
            HashMap<String,String> title = new HashMap<>();
            title.put("title",cursor.getString(cursor.getColumnIndex(notetitle)));
            titleList.add(title);
        }
        cursor.close();

        if(titleList.isEmpty()) {
            ((TextView) findViewById(R.id.txvL)).setText(getString(R.string.Empty_List));
            findViewById(R.id.txvL).setVisibility(View.VISIBLE);
            findViewById(R.id.linearLayout4).setVisibility(View.INVISIBLE);
        }
        else {
            ListAdapter adapter = new SimpleAdapter(this,
                    titleList,
                    R.layout.my_list_item1,
                    new String[]{"title"},
                    new int[]{R.id.txvListName}
            );

            ((ListView) findViewById(R.id.ListView)).setAdapter(adapter);
            ((ListView) findViewById(R.id.ListView)).setOnItemClickListener(this::onItemClick);
            ((ListView) findViewById(R.id.ListView)).setOnItemLongClickListener(this :: onLongItemClick);
        }

        findViewById(R.id.btnListBack).setOnClickListener(this :: onListBack);

    }

    private void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Initializing Database details
        String table = NotepadDb.notepad_info.TAB_NAME;
        String notetitle = NotepadDb.notepad_info.COL_TITLE;
        String notetext = NotepadDb.notepad_info.COL_TEXT;
        String dbname = NotepadDb.DB_NAME;
        int dbversion = NotepadDb.DB_VERSION;

        String titleI = ((TextView)(view.findViewById(R.id.txvListName))).getText().toString();

        //Initialising helper class for GETTING DATABASE DETAILS
        DbHelper helper = new DbHelper(this,dbname,null,dbversion);

        //Get database object for EXECUTING DB QUERIES
        SQLiteDatabase db = helper.getWritableDatabase();

        //Declare List for Storing ResultSet
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        //Cursor for converts database ResultSet to Java readable data
        Cursor cursor = db.query(table, new String[]{notetitle,notetext}, notetitle + "=?",new String[]{String.valueOf(titleI)},null, null, null, null);

        //Get title and text of note in database
        if (cursor.moveToFirst()){
            HashMap<String,String> user = new HashMap<>();
            user.put("title",cursor.getString(cursor.getColumnIndex(notetitle)));
            user.put("text",cursor.getString(cursor.getColumnIndex(notetext)));
            userList.add(user);
        }
        cursor.close();

        //Move to Edit Note old activity for edit old note.
        Intent intent = new Intent(this,EditNoteO.class);
        intent.putExtra(KEY,userList);
        startActivity(intent);
    }

    /*
    On long click of list item visible and enable two buttons
    1. Rename
    2. Delete
    */
    private boolean onLongItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        findViewById(R.id.btnListRename).setVisibility(View.VISIBLE);
        findViewById(R.id.btnListRename).setEnabled(true);

        findViewById(R.id.btnListDelete).setVisibility(View.VISIBLE);
        findViewById(R.id.btnListDelete).setEnabled(true);

        title = ((TextView)(view.findViewById(R.id.txvListName))).getText().toString();

        findViewById(R.id.btnListRename).setOnClickListener(this::onRename);
        findViewById(R.id.btnListDelete).setOnClickListener(this::onDelete);

        return true;
    }

    private void onDelete(View view) {

        //Initializing Database details
        String table = NotepadDb.notepad_info.TAB_NAME;
        String notetitle = NotepadDb.notepad_info.COL_TITLE;
        String dbname = NotepadDb.DB_NAME;
        int dbversion = NotepadDb.DB_VERSION;

        //String title = ((TextView)findViewById(R.id.txvListName)).getText().toString();

        //Initialising helper class for GETTING DATABASE DETAILS
        DbHelper helper = new DbHelper(this,dbname,null,dbversion);

        //Get database object for EXECUTING DB QUERIES
        SQLiteDatabase db = helper.getWritableDatabase();

        //Delete database using unique title name
        db.delete(table, notetitle+" = ?",new String[]{String.valueOf(title)});
        db.close();
        Toast.makeText(this, "Note Deleted Successfully",Toast.LENGTH_SHORT).show();

        //Move to Home Activity
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    //Move to the RenameNote activity with old title
    private void onRename(View view) {
        //String title = ((TextView)findViewById(R.id.txvListName)).getText().toString();
        Intent intent = new Intent(this,RenameNote.class);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RENAME,title);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //pressing back button in the activity
    private void onListBack(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
