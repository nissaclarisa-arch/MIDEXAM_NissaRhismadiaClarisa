
package com.example.nissarhismadiaclarisa_midexam20261;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "students.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "students";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" +
                "student_id TEXT PRIMARY KEY, " +
                "student_name TEXT NOT NULL, " +
                "major TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public boolean addStudent(String id, String name, String major) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_id", id);
        values.put("student_name", name);
        values.put("major", major);
        return db.insert(TABLE, null, values) != -1;
    }

    public boolean updateStudent(String id, String name, String major) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("student_name", name);
        values.put("major", major);
        return db.update(TABLE, values, "student_id = ?",
                new String[]{id}) > 0;
    }

    public boolean deleteStudent(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, "student_id = ?",
                new String[]{id}) > 0;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE,
                new String[]{"student_id", "student_name", "major"},
                null, null, null, null, "student_name ASC");
    }
}
