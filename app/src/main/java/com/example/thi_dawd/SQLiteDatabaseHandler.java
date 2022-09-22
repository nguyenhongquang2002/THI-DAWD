package com.example.thi_dawd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "employeeData";
    // Country table name
    private static final String TABLE_EMPLOYEE = "Employee";
    // Country Table Columns names
    private static final String KEY_ID = "id";
    private static final String NAME = "Name";
    private static final String DESIGNATION = "Designation";
    private static final String SALARY = "Salary";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT UNIQUE,"
                + DESIGNATION + " TEXT,"
                + SALARY + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new country
    void add(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, employee.getName());
        values.put(DESIGNATION, employee.getDesignation());
        values.put(SALARY, employee.getSalary());

        // Inserting Row
        db.insert(TABLE_EMPLOYEE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single country
    Employee get(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEE, new String[]{KEY_ID, NAME, DESIGNATION, SALARY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Employee employee = new Employee(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getLong(2));
                cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
        // return country
        return employee;
    }

    // Getting All Countries
    public List getAll() {
        List<Employee> list = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(cursor.getString(0)));
                employee.setName(cursor.getString(1));
                employee.setDesignation(cursor.getString(2));
                employee.setSalary(cursor.getDouble(3));
                list.add(employee);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int update(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, employee.getName());
        values.put(DESIGNATION, employee.getDesignation());
        values.put(SALARY, employee.getSalary());

        return db.update(TABLE_EMPLOYEE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});
    }

    public void delete(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, KEY_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE, null, null);
        db.close();
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EMPLOYEE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
}
