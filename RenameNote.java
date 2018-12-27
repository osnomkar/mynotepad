package com.conq.omkar.mynotepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RenameNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rename_note);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String title =  bundle.getString(ListNote.KEY_RENAME);

        ((TextView)findViewById(R.id.txvRenameName)).setText(title);

        Button btnRenameSave = findViewById(R.id.btnRenameSave);
        Button btnRenameBack = findViewById(R.id.btnRenameBack);


        btnRenameSave.setOnClickListener(this :: onRenameSave);
        btnRenameBack.setOnClickListener(this :: onRenameBack);
    }

    private void onRenameBack(View view) {
        clearText();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearText();
        finish();
    }

    private void onRenameSave(View view) {
        String title = ((TextView)findViewById(R.id.txvRenameName)).getText().toString();
        String newTitle =  ((EditText)findViewById(R.id.txtRename)).getText().toString();
        if(newTitle.isEmpty())
            Toast.makeText(this,"Title Cannot be Empty",Toast.LENGTH_SHORT).show();
        else if(newTitle.equals(title))
            Toast.makeText(this,"New Title is same as Old",Toast.LENGTH_SHORT).show();
        else {
            String table = NotepadDb.notepad_info.TAB_NAME;
            String notetitle = NotepadDb.notepad_info.COL_TITLE;
            String dbname = NotepadDb.DB_NAME;
            int dbversion = NotepadDb.DB_VERSION;

            DbHelper helper = new DbHelper(this,dbname,null,dbversion);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues cVals = new ContentValues();
            cVals.put("title", newTitle);
            db.update(table, cVals, notetitle + " = ?", new String[]{String.valueOf(title)});

            Toast.makeText(this,"Title Updated Successfully",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,Home.class);
            startActivity(intent);
        }
    }

    public void clearText(){
        ((EditText)findViewById(R.id.txtRename)).setText("");
    }
}
