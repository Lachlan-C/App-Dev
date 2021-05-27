package com.example.restaurantmapmobileapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import com.example.restaurantmapmobileapp.model.Location;
import com.example.restaurantmapmobileapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Util.LOCATION_NAME + " TEXT," + Util.LOCATION_LAT+ " REAL, " + Util.LOCATION_LONG + " REAL)";
        sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_NOTE_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

    }

    public long insertLoc (Location location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.LOCATION_NAME, location.getName());
        contentValues.put(Util.LOCATION_LAT, location.getLat());
        contentValues.put(Util.LOCATION_LONG, location.getLng());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public List<Location> fetchAllLocations (){
        List<Location> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setName(cursor.getString(1));
                location.setLat(cursor.getInt(2));
                location.setLng(cursor.getInt(3));

                locationList.add(location);

            } while (cursor.moveToNext());

        }

        return locationList;
    }
}
