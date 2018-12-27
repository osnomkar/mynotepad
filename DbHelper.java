package com.conq.omkar.mynotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+NotepadDb.notepad_info.TAB_NAME+"("+NotepadDb.notepad_info.COL_REG_ID+" int, "+NotepadDb.notepad_info.COL_TITLE +" text, "+NotepadDb.notepad_info.COL_TEXT+" text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertNotepadDetails(String title, String text){

        String NoteTitle = NotepadDb.notepad_info.COL_TITLE;
        String NoteText = NotepadDb.notepad_info.COL_TEXT;
        String Tabel = NotepadDb.notepad_info.TAB_NAME;

        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(NoteTitle, title);
        cValues.put(NoteText, text);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Tabel,null, cValues);
        db.close();
    }

    ArrayList<HashMap<String, String>> GetNoteTitle(){
        String table = NotepadDb.notepad_info.TAB_NAME;
        String notetitle = NotepadDb.notepad_info.COL_TITLE;
        String dbname = NotepadDb.DB_NAME;
        int dbversion = NotepadDb.DB_VERSION;

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> titleList = new ArrayList<>();
        String query = "SELECT "+notetitle+" FROM "+ table;

        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> title = new HashMap<>();
            title.put("title",cursor.getString(cursor.getColumnIndex(notetitle)));
            titleList.add(title);
        }
        cursor.close();
        return  titleList;
    }
    public ArrayList<HashMap<String, String>> GetUserByTitle(String title){

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();

        String table = NotepadDb.notepad_info.TAB_NAME;
        String notetitle = NotepadDb.notepad_info.COL_TITLE;
        String text = NotepadDb.notepad_info.COL_TEXT;

        String query = "SELECT "+notetitle+" "+text+" FROM "+ table;

        Cursor cursor = db.query(table, new String[]{notetitle,text}, notetitle + "=?",new String[]{String.valueOf(title)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("title",cursor.getString(cursor.getColumnIndex(notetitle)));
            user.put("text",cursor.getString(cursor.getColumnIndex(text)));
            userList.add(user);
        }
        cursor.close();
        return  userList;
    }

}


