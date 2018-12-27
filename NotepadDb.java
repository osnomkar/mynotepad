package com.conq.omkar.mynotepad;

public interface NotepadDb {
    String DB_NAME = "notepad_info.sqlite";
    int DB_VERSION = 1;

    interface notepad_info{
        String TAB_NAME = "notepad_info";
        String COL_REG_ID = "reg_id";
        String COL_TITLE = "title";
        String COL_TEXT = "text";
    }
}
