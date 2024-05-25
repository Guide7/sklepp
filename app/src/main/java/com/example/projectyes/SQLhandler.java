package com.example.projectyes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLhandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "zamowienia";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "email";
    private static final String COLUMN_NAME1 = "ammount";
    private static final String COLUMN_NAME2 = "price";
    private static final String COLUMN_NAME3 = "add1";
    private static final String COLUMN_NAME4 = "add2";
    private static final String COLUMN_NAME5 = "add3";

    public void insertData(String email, int amount, int price, boolean add1, boolean add2, boolean add3) {
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("ammount", amount);
        values.put("price", price);
        values.put("add1", add1 ? 1 : 0);
        values.put("add2", add2 ? 1 : 0);
        values.put("add3", add3 ? 1 : 0);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("zamowienia", null, values);
        db.close();
    }

    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete(TABLE_NAME, null, null);
            Log.d("Database", "All data deleted from table " + TABLE_NAME);
        } catch (Exception e) {
            Log.e("DatabaseError", "Error deleting data from table: " + e.getMessage());
        } finally {
            db.close();
        }
    }


//    public void readData() {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = null;
//
//        try {
//            String[] projection = {
//                    COLUMN_ID,
//                    COLUMN_NAME,
//                    COLUMN_NAME1,
//                    COLUMN_NAME2,
//                    COLUMN_NAME3,
//                    COLUMN_NAME4,
//                    COLUMN_NAME5
//            };
//
//            String selection = null;
//            String[] selectionArgs = null;
//
//            cursor = db.query(
//                    TABLE_NAME,
//                    projection,
//                    selection,
//                    selectionArgs,
//                    null,
//                    null,
//                    null
//            );
//
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
//                    String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
//                    int amount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME1));
//                    int price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME2));
//
//                    Log.d("Database", "ID: " + id + ", Email: " + email + ", Amount: " + amount + ", Price: " + price);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            Log.e("DatabaseError", "Error reading data from database: " + e.getMessage());
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            db.close();
//        }
//    }

    public List<DataItem> readData() {
        List<DataItem> dataItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    int amount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME1));
                    int price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME2));

                    DataItem dataItem = new DataItem(id, email, amount, price);
                    dataItems.add(dataItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error reading data from database: " + e.getMessage());
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return dataItems;
    }


    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_NAME1 + " INTEGER," +
                    COLUMN_NAME2 + " REAL," +
                    COLUMN_NAME3 + " BOOLEAN," +
                    COLUMN_NAME4 + " BOOLEAN," +
                    COLUMN_NAME5 + " BOOLEAN)";

    public SQLhandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
