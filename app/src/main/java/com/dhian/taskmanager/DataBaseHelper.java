package com.dhian.taskmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String NAME_DATA_BASE = "tasks_db",
            NAME_TABLE_TASK = "tasks";
    private static final int VERSION_DATA_BASE = 1;

    public DataBaseHelper(Context context) {
        super(context, NAME_DATA_BASE, null, VERSION_DATA_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement," +
                " title text, description text, dueDate text)", NAME_TABLE_TASK));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
