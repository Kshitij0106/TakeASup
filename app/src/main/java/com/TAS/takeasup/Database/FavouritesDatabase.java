package com.TAS.takeasup.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.TAS.takeasup.Model.FavouritesRestaurantsList;

import java.util.ArrayList;
import java.util.List;

public class FavouritesDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "FAVOURITES.db";
    private static final String TABLE_NAME = "FavouriteRestaurants";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "TYPE";
    private static final String COL_4 = "COST";

    public FavouritesDatabase(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER ,NAME TEXT PRIMARY KEY ,TYPE TEXT, COST TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addToFavourites(String RestName, String RestType, String RestCost) {
        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_2, RestName);
            values.put(COL_3, RestType);
            values.put(COL_4, RestCost);
            db.insert(TABLE_NAME, null, values);
    }

    public void removeFromFavourites(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "NAME = ?", new String[]{name});
    }

    public List<FavouritesRestaurantsList> showFavourites() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<FavouritesRestaurantsList> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(new FavouritesRestaurantsList(cursor.getString(cursor.getColumnIndex("NAME")),
                        cursor.getString(cursor.getColumnIndex("TYPE")),
                        cursor.getString(cursor.getColumnIndex("COST"))));
            }while (cursor.moveToNext());
        }
        return result;
    }

    public void cleanFavourites() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("DELETE FROM " + TABLE_NAME);
        db.execSQL(query);
    }
}
