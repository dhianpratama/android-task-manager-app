package com.dhian.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;



public class TaskController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "tasks";

    public TaskController(Context contexto) {
        dataBaseHelper = new DataBaseHelper(contexto);
    }


    public int deleteTask(Task task) {

        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        String[] args = {String.valueOf(task.getId())};
        return dataBase.delete(TABLE_NAME, "id = ?", args);
    }

    public long newTask(Task task) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForInsert = new ContentValues();
        valuesForInsert.put("title", task.getTitle());
        valuesForInsert.put("description", task.getDescription());
        valuesForInsert.put("dueDate", task.getDueDate());
        return dataBase.insert(TABLE_NAME, null, valuesForInsert);
    }

    public int saveChanges(Task taskEdited) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put("title", taskEdited.getTitle());
        valuesForUpdate.put("description", taskEdited.getDescription());
        valuesForUpdate.put("dueDate", taskEdited.getDueDate());
        String fieldForUpdate = "id = ?";
        String[] argsForUpdate = {String.valueOf(taskEdited.getId())};
        return dataBase.update(TABLE_NAME, valuesForUpdate, fieldForUpdate, argsForUpdate);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dataBaseHelper.getReadableDatabase();
        String[] fields = {"title", "description", "id", "dueDate"};
        Cursor cursor = baseDeDatos.query(
                TABLE_NAME,
                fields,
                null,
                null,
                null,
                null,
                "dueDate"
        );

        if (cursor == null) {
            return tasks;

        }
        if (!cursor.moveToFirst()) return tasks;


        do {
            String title = cursor.getString(0);
            String description = cursor.getString(1);
            long id = cursor.getLong(2);
            String dueDate = cursor.getString(3);
            Task taskGetFromDB = new Task(title, description, id, dueDate);
            tasks.add(taskGetFromDB);
        } while (cursor.moveToNext());

        cursor.close();
        return tasks;
    }
}