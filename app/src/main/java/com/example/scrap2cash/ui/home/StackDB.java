package com.example.scrap2cash.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.scrap2cash.ui.home.stack.stackmodel;

import java.util.ArrayList;

public class StackDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scrap2cash.db";
    private static final int DATABASE_VERSION = 1;  //(agar schema change hogi toh version bhi change hoga)

    // Scrap Items Table (yeh items ki table hai)
    private static final String TABLE_SCRAP = "scrap_items";
    public static final String COL_ID = "id";
    private static final String COL_MODEL = "model";
    private static final String COL_COMPANY = "company";
    private static final String COL_ORIGINAL_PRICE = "original_price";
    private static final String COL_USED_PRICE = "used_price";
    private static final String COL_IMAGE_URI = "image_uri";
    private static final String COL_SELL_LATER = "sell_later";

    // Profile Table  (yeh profile wale ke liye banaya hai abhi se) optional
    public static final String TABLE_PROFILE = "user_profile";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    private static final String COL_PH_NO= "ph_no";
    public static final String COL_PROFILE_URI = "profile_uri";
    private static final String TABLE_IDPASSWORD= "user_idpassword";
    private static final String COL_PASSWORD="password";

    public StackDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    public StackDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createScrapTable = "CREATE TABLE " + TABLE_SCRAP + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MODEL + " TEXT, "
                + COL_COMPANY + " TEXT, "
                + COL_ORIGINAL_PRICE + " TEXT, "
                + COL_USED_PRICE + " TEXT, "
//                + COL_IMAGE_URI + " TEXT, "
                + COL_SELL_LATER + " INTEGER)";
        db.execSQL(createScrapTable);

        String createProfileTable = "CREATE TABLE " + TABLE_PROFILE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_EMAIL + " TEXT, "
                +COL_PH_NO +"INTEGER , "
                + COL_PROFILE_URI + " TEXT)";
        db.execSQL(createProfileTable);


        String createIdPasswordTable=" CREATE TABLE " +TABLE_IDPASSWORD +"("
              + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EMAIL +"TEXT, "
              + COL_PASSWORD + "TEXT) ";
        db.execSQL(createIdPasswordTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCRAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }
    // Insert Scrap Item (yeh item daalne ke liye hai)
    public long insertScrap(stackmodel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MODEL, item.model);
        cv.put(COL_COMPANY, item.company);
        cv.put(COL_ORIGINAL_PRICE, item.originalprice);
        cv.put(COL_USED_PRICE, item.usedprice);
        cv.put(COL_IMAGE_URI, item.imageUri != null ? item.imageUri.toString() : null);
        cv.put(COL_SELL_LATER, item.selllater ? 1 : 0);
        return db.insert(TABLE_SCRAP, null, cv); // returns id of inserted row
    }

    // Get All Scrap Items (yeh saare items ko grap karega)
    public ArrayList<stackmodel> getAllScrap() {
        ArrayList<stackmodel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SCRAP, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String model = cursor.getString(cursor.getColumnIndexOrThrow(COL_MODEL));
                String company = cursor.getString(cursor.getColumnIndexOrThrow(COL_COMPANY));
                String originalPrice = cursor.getString(cursor.getColumnIndexOrThrow(COL_ORIGINAL_PRICE));
                String usedPrice = cursor.getString(cursor.getColumnIndexOrThrow(COL_USED_PRICE));
                String uriStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_URI));
                boolean sellLater = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SELL_LATER)) == 1;

                stackmodel item;
                if (uriStr != null) {
                    item = new stackmodel(id, Uri.parse(uriStr), model, company, originalPrice, usedPrice, sellLater);
                } else {
                    item = new stackmodel(id, 0, model, company, originalPrice, usedPrice, sellLater);
                }
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Delete Scrap Item by id (yeh delete ke liye hai)
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_SCRAP, COL_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    // Update Scrap (Item yeh item ke update ke liye hai)
    public void updateScrap(stackmodel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_MODEL, item.model);
        cv.put(COL_COMPANY, item.company);
        cv.put(COL_ORIGINAL_PRICE, item.originalprice);
        cv.put(COL_USED_PRICE, item.usedprice);
        cv.put(COL_IMAGE_URI, item.imageUri != null ? item.imageUri.toString() : null);
        cv.put(COL_SELL_LATER, item.selllater ? 1 : 0);

        db.update(TABLE_SCRAP, cv, COL_ID + "=?", new String[]{String.valueOf(item.id)});
    }

    // Optional: Insert/Update Profile (yeh abhi kaam ka nahi hai ajb tak tu next activiyt na bna le save profile ke liye.)
    public void saveProfile(String name, String email, Uri profileUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, null, null); // Only one profile

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_EMAIL, email);
        cv.put(COL_PROFILE_URI, profileUri != null ? profileUri.toString() : null);
        db.insert(TABLE_PROFILE, null, cv);
    }
    public void updateProfile(int id, String name, String email, Uri profileImage){
        SQLiteDatabase writableDb=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(StackDB.COL_NAME,name);
        cv.put(StackDB.COL_EMAIL,email);
        cv.put(StackDB.COL_PROFILE_URI,profileImage !=null ? profileImage.toString():null);
        writableDb.update(TABLE_PROFILE,cv,StackDB.COL_ID+"=?",new String[]{String.valueOf(id)});
    }
    public void deleteProfile(int id){
        SQLiteDatabase writable= this.getWritableDatabase();
        writable.delete(TABLE_PROFILE, StackDB.COL_ID+ "=?",new String[]{String.valueOf(id)});
    }

//    public Cursor getProfile() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery("SELECT * FROM " + TABLE_PROFILE + " LIMIT 1", null);
//    }
}
