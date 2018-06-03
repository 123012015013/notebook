package com.example.android.notebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.android.notebook.data.Note;
import com.example.android.notebook.database.NoteDbSchema.NoteTable;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

public class NoteUtil  {

    private static NoteUtil sNoteUtil;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private NoteBaseHelper mHelper;

    private NoteUtil(Context context){

        mContext = context.getApplicationContext();
        //mDatabase = new NoteBaseHelper(mContext).getWritableDatabase();
        mHelper = new NoteBaseHelper(mContext);
        mHelper.createDataBase();
        mDatabase = mHelper.getWritableDatabase();
    }

    private static ContentValues getContentValues(Note note){
        ContentValues values = new ContentValues();
        //values.put(BookTable.Cols.ID,book.getId());
        values.put(NoteTable.Cols.COLOR,note.getColor());
        values.put(NoteTable.Cols.TITLE,note.getTitle());
        values.put(NoteTable.Cols.CONTENT,note.getContent());
        values.put(NoteTable.Cols.MODIFYTIME,note.getModifyTime());

        return values;
    }

    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new NoteCursorWrapper(cursor);
    }

    private NoteCursorWrapper queryNotesByTitle(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new NoteCursorWrapper(cursor);
    }

    private NoteCursorWrapper orderNotes(String whereClause, String[] whereArgs,String orders){
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                orders
        );

        return new NoteCursorWrapper(cursor);
    }

    public static NoteUtil getsNoteUtil(Context context){
        if(sNoteUtil == null){
            sNoteUtil = new NoteUtil(context);
        }
        return sNoteUtil;
    }



    public void returnData(){
        mHelper.returnData();

    }
    public void addNote(Note note){
        Log.i("test","add Id " + note.getId());
        ContentValues values = getContentValues(note);
        mDatabase.insert(NoteTable.NAME,null,values);
    }

    public void updateNote(Note note){
        int id = note.getId();
        Log.i("test","update Id " + id);
        ContentValues values = getContentValues(note);
        mDatabase.update(NoteTable.NAME,values,NoteTable.Cols.ID +"=?",new String[]{Integer.toString(id)});
    }

    public void deleteNote(int id){
        Log.i("test","deleteBook Id " + id);
        mDatabase.delete(NoteTable.NAME,NoteTable.Cols.ID +"=?",new String[]{Integer.toString(id)});
    }

    public List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursor = queryNotes(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return notes;
    }

    public List<Note> orderBy(String orders){
        List<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursor = orderNotes(null,null,orders);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return notes;

    }

    public List<Note> search(String title){

        List<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursor = queryNotesByTitle(NoteTable.Cols.TITLE + " LIKE ?",new String[]{  "%" + title + "%" });
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return notes;
    }


}
