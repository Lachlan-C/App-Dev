package com.example.foodrescuemobileapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.foodrescuemobileapp.model.Food;
import com.example.foodrescuemobileapp.model.User;
import com.example.foodrescuemobileapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " (" + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.USERNAME + " TEXT," + Util.PASSWORD + " TEXT," + Util.EMAIL + " TEXT," +  Util.PHONE + " INTEGER," + Util.ADDRESS + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_FOOD_TABLE = "CREATE TABLE " + Util.TABLE_NAME_FOOD + " (" + Util.FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.USER_ID + " TEXT," + Util.FOOD_TITLE + " TEXT," + Util.FOOD_DESC + " TEXT," +  Util.FOOD_DATE + " TEXT," + Util.PICKUP_TIMES + " TEXT," + Util.QUANTITY + " INTEGER," + Util.LOCATION + " TEXT," + Util.IMAGE + " BLOB)";
        sqLiteDatabase.execSQL(CREATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME_FOOD});

        onCreate(sqLiteDatabase);

    }

    public long insertUser (User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.PASSWORD, user.getPassword());
        contentValues.put(Util.EMAIL, user.getEmail());
        contentValues.put(Util.PHONE, user.getPhone());
        contentValues.put(Util.ADDRESS, user.getAddress());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }
    public long insertFood (Food food)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_ID, food.getUser_id());
        contentValues.put(Util.FOOD_TITLE, food.getTitle());
        contentValues.put(Util.FOOD_DESC, food.getDesc());
        contentValues.put(Util.FOOD_DATE, food.getDate());
        contentValues.put(Util.PICKUP_TIMES, food.getPick_up_times());
        contentValues.put(Util.QUANTITY, food.getQuantity());
        contentValues.put(Util.LOCATION, food.getLocation());
        contentValues.put(Util.IMAGE, food.getImage());
        long newRowId = db.insert(Util.TABLE_NAME_FOOD, null, contentValues);
        db.close();
        return newRowId;
    }

    public boolean fetchUser(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USERNAME + "=? and " + Util.PASSWORD + "=?",
                new String[] {username, password}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();
        if (numberOfRows > 0)
            return  true;
        else
            return false;
    }

    public int fetchUserID(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USERNAME + "=? and " + Util.PASSWORD + "=?",
                new String[] {username, password}, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public boolean IfUserExists(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USERNAME + "=?",
                new String[] {username}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0)
            return  true;
        else
            return false;
    }

    public List<Food> fetchAllFood (){
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME_FOOD;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setFood_id(cursor.getInt(0));
                food.setTitle(cursor.getString(2));
                food.setDesc(cursor.getString(3));
                food.setImage(cursor.getBlob(8));

                foodList.add(food);

            } while (cursor.moveToNext());

        }

        return foodList;
    }

    public List<Food> fetchAllMyFood (int userid){
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME_FOOD;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(1) == userid) {
                    Food food = new Food();
                    food.setFood_id(cursor.getInt(0));
                    food.setTitle(cursor.getString(2));
                    food.setDesc(cursor.getString(3));
                    food.setImage(cursor.getBlob(8));

                    foodList.add(food);
                }
            } while (cursor.moveToNext());

        }

        return foodList;
    }
}
