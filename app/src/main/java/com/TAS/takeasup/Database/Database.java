package com.TAS.takeasup.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.TAS.takeasup.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "eatyum.db";
    private static final String TABLE_NAME = "OrderDetails";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PRICE";
    private static final String COL_4 = "QUANTITY";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,PRICE TEXT,QUANTITY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public List<Order> getCart() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null); //provides read-write access to result

        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(new Order(
                        cursor.getString(cursor.getColumnIndex("NAME")),
                        cursor.getString(cursor.getColumnIndex("PRICE")),
                        cursor.getString(cursor.getColumnIndex("QUANTITY")),
                        cursor.getInt(cursor.getColumnIndex("ID"))
                ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public boolean addToCart(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("select * from " + TABLE_NAME + " where NAME = ?");
        Cursor cursor = db.rawQuery(query, new String[]{order.getDishName()});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, order.getDishName());
            contentValues.put(COL_3, order.getDishPrice());
            contentValues.put(COL_4, order.getDishQuantity());
            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void updateCart(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE OrderDetails SET QUANTITY =%s WHERE ID = %d", order.getDishQuantity(), order.getID());
        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM " + TABLE_NAME);
        db.execSQL(query);
    }

    public void deleteFromCart(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "NAME=?", new String[]{name});
    }
}

