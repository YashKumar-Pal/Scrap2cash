package com.example.scrap2cash.ui.historyhome;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.scrap2cash.ui.home.StackDB;

public class HistoryhomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private StackDB db;
    public void init(Context context){
        if (db==null){
            db= new StackDB(context.getApplicationContext());
        }
    }
    public void saveProfile2(String name, String email,Uri Profileimage){
        db.saveProfile(name, email, Profileimage);
    }
    public void updateprofile(int id , String name, String email, Uri imageuri){
        db.updateProfile(id, name, email, imageuri);
    }
    public Cursor getProfile(){
        SQLiteDatabase readable= db.getWritableDatabase();
        return readable.rawQuery("SELECT * FROM " + StackDB.TABLE_PROFILE + " LIMIT 1", null);
    }
    public void deleteprofile(int id){
        db.deleteProfile(id);
    }
//    public void saveProfile(String name, String email, Uri profileImage){
//        SQLiteDatabase writableDb=db.getWritableDatabase();
//        writableDb.delete(StackDB.TABLE_PROFILE,null,null);
//        ContentValues cv= new ContentValues();
//        cv.put(StackDB.COL_NAME,name);
//        cv.put(StackDB.COL_EMAIL,email);
//        cv.put(StackDB.COL_PROFILE_URI, profileImage !=null? profileImage.toString():null);
//        writableDb.insert(StackDB.TABLE_PROFILE,null,cv);
//    }
//    public void updateProfile(int id, String name, String email, Uri profileImage){
//        SQLiteDatabase writableDb=db.getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put(StackDB.COL_NAME,name);
//        cv.put(StackDB.COL_EMAIL,email);
//        cv.put(StackDB.COL_PROFILE_URI,profileImage !=null ? profileImage.toString():null);
//        writableDb.update(StackDB.TABLE_PROFILE,cv,StackDB.COL_ID+"=?",new String[]{String.valueOf(id)});
//    }
//    public void deleteProfile(int id){
//        SQLiteDatabase writable= db.getWritableDatabase();
//        writable.delete(StackDB.TABLE_PROFILE, StackDB.COL_ID+ "=?",new String[]{String.valueOf(id)});
//    }

}