package com.todoapp.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.todoapp.Fragment.Home.model.todolist_model;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "todoDatabase";
    private static final Integer DB_VERSION = 1;

    private static final String TABLE_NAME = "todotask";
    private static final String ID = "id";
    private static final String TITLE = "title";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE+ " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    public void addTodoTask(String title){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITLE, title);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<todolist_model> readTodo()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorTodo
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<todolist_model> courseModalArrayList
                = new ArrayList<>();

        if (cursorTodo.moveToFirst()) {
            do {
                courseModalArrayList.add(new todolist_model(
                        cursorTodo.getInt(0),
                        cursorTodo.getString(1)));
            } while (cursorTodo.moveToNext());
        }
        cursorTodo.close();
        return courseModalArrayList;
    }

    public void deleteTodo(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }

    public void updateTodo(String title, String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE, title);

        db.update(TABLE_NAME, values, "id=?", new String[]{id});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
