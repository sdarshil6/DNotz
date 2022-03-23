package com.company.dnotz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PassDataToDatabase extends SQLiteOpenHelper {


    private static final String NOTE_TABLE = "NOTE_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NOTE_TITLE = "NOTE_TITLE";
    private static final String COLUMN_NOTE_DESCRIPTION = "NOTE_DESCRIPTION";
    private static final String COLUMN_NOTE_FLAG = "NOTE_FLAG";








    public PassDataToDatabase(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + NOTE_TABLE
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1,  "
                + COLUMN_NOTE_TITLE + " TEXT,  "
                + COLUMN_NOTE_DESCRIPTION + " TEXT, "
                + COLUMN_NOTE_FLAG + " INTEGER DEFAULT 0" + ")";

        sqLiteDatabase.execSQL(createTableStatement);






    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {



    }

    public boolean addNote(DataItems dataItems){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTE_TITLE, dataItems.getNote_title());
        cv.put(COLUMN_NOTE_DESCRIPTION, dataItems.getNote_description());
        cv.put(COLUMN_NOTE_FLAG, 1);



        long insert = db.insert(NOTE_TABLE, null, cv);

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public long getRowCount(){

        long numRows = DatabaseUtils.queryNumEntries(this.getReadableDatabase(), NOTE_TABLE, null);

        return numRows;

    }

    public ArrayList<String> getNoteTitleList() {

        ArrayList<String> noteTitleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_NOTE_TITLE + " FROM " + NOTE_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

            if (cursor.moveToFirst()) {
                do {

                    noteTitleList.add(cursor.getString(0));

                } while (cursor.moveToNext());
            }



        cursor.close();
        db.close();

        return noteTitleList;

    }

    public ArrayList<Integer> getPrimaryIdList(){

        ArrayList<Integer> primaryIdList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_ID + " FROM " + NOTE_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{

                primaryIdList.add(cursor.getInt(0));

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return primaryIdList;
    }

    public ArrayList<String> getNoteDescriptionList() {

        ArrayList<String> noteDescriptionList  = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_NOTE_DESCRIPTION + " FROM " + NOTE_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {

                noteDescriptionList.add(cursor.getString(0));

            } while (cursor.moveToNext());

            return noteDescriptionList;

        }

        cursor.close();
        db.close();

        return noteDescriptionList;

    }

    public ArrayList<Integer> getFlagList(){

        ArrayList<Integer> flagList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_NOTE_FLAG + " FROM " + NOTE_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{

                flagList.add(cursor.getInt(0));

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return flagList;
    }

    public void deleteNote(int holderPos){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString1 = "DELETE FROM " + NOTE_TABLE + " WHERE " + COLUMN_ID + " = " + (holderPos);
        Cursor cursor1 = db.rawQuery(queryString1, null);
        if(cursor1.moveToFirst()){
            Log.i("index", "YES");
        }
        Log.i("index", "NO");

        MainActivity.getmInstanceActivity().showAllNotes();
    }

    public boolean updateNote(String updatedTitle, String updatedDescription, int primaryId){

        String queryString = "UPDATE " + NOTE_TABLE +
                " SET " + COLUMN_NOTE_TITLE + " = " + "'" + updatedTitle + "'" + ", " +
                COLUMN_NOTE_DESCRIPTION + " = " + "'" + updatedDescription + "'" +
                " WHERE " + COLUMN_ID + " = " + primaryId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){



        }
        return true;

    }
}
