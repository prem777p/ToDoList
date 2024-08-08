package com.prem.todolist.DB;

import android.content.Context;

public class NoteRepo {

    static NoteDao noteDao;


    public static NoteDao getInstance(Context context){
        if(noteDao == null){
            noteDao = NoteDb.getInstance(context).noteDao();
        }
        return noteDao;
    }
}
