package com.todoapp.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.todoapp.Fragment.Home.model.todolist_model;
import com.todoapp.taskView.model.DateWiseTaskModel;
import com.todoapp.taskView.model.TaskModel;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "todoDatabase.db";
    private static final Integer DB_VERSION = 1;

    private static final String TABLE_NAME = "todotask";
    private static final String TABLE_NAME_DATE_WISE_TASK = "datewisetask";
    private static final String TABLE_NAME_TASK = "tasktable";
    private static final String ID = "id";
    private static final String CATEGORY = "category";

    private static final String TITLE = "title";
    private static final String SUBTITLE = "subtitle";
    private static final String TIMING = "timing";
    private static final String DATE = "date";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE+ " TEXT)";

        sqLiteDatabase.execSQL(query);

        String query1 = "CREATE TABLE " + TABLE_NAME_DATE_WISE_TASK + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT,"
                + CATEGORY + " INTEGER)";

        sqLiteDatabase.execSQL(query1);

        String query2 = "CREATE TABLE " + TABLE_NAME_TASK + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT,"
                + SUBTITLE + " TEXT,"
                + TIMING + " TEXT,"
                + CATEGORY + " INTEGER)";

        sqLiteDatabase.execSQL(query2);
    }

    public void addTask(String title,String subtitle,String timing,int category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITLE, title);
        values.put(SUBTITLE, subtitle);
        values.put(TIMING, timing);
        values.put(CATEGORY, category);

        db.insert(TABLE_NAME_TASK, null, values);

        db.close();
    }
    public void addTodoTask(String title){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TITLE, title);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public void addDateTask(String date,int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DATE, date);
        values.put(CATEGORY, id);

        db.insert(TABLE_NAME_DATE_WISE_TASK, null, values);

        db.close();
    }

    public ArrayList<TaskModel> readTask(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorTodo
                = db.rawQuery("SELECT * FROM " + TABLE_NAME_TASK + " WHERE "+CATEGORY+" = '"+id+"' ", null);

        ArrayList<TaskModel> taskModalArrayList
                = new ArrayList<>();

        if (cursorTodo.moveToFirst()) {
            do {
                taskModalArrayList.add(new TaskModel(
                        cursorTodo.getInt(0),
                        cursorTodo.getString(1),
                        cursorTodo.getString(2),
                        cursorTodo.getString(3),
                        cursorTodo.getInt(4)));
            } while (cursorTodo.moveToNext());
        }
        cursorTodo.close();
        return taskModalArrayList;
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

    public ArrayList<DateWiseTaskModel> readDateTask(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursordatewise
                = db.rawQuery("SELECT * FROM " + TABLE_NAME_DATE_WISE_TASK + " WHERE "+CATEGORY+" = '"+id+"' ", null);

        ArrayList<DateWiseTaskModel> DateTaskModalArrayList
                = new ArrayList<>();

        if (cursordatewise.moveToFirst()) {
            do {
                DateTaskModalArrayList.add(new DateWiseTaskModel(
                        cursordatewise.getInt(0),
                        cursordatewise.getString(1),
                        cursordatewise.getInt(2)));
            } while (cursordatewise.moveToNext());
        }
        cursordatewise.close();
        return DateTaskModalArrayList;
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
