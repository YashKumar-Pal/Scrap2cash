package com.example.scrap2cash.ui.historyhome;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.scrap2cash.ui.home.StackDB;

import java.util.List;

public class HistoryhomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private StackDB db;

    public void init(Context context){
        if (db==null){
            db= new StackDB(context.getApplicationContext());
        }
    }
    public void saveProfile2(String name,String dob, String email,Uri Profileimage){
        db.saveProfile(name,dob, email, Profileimage);
    }
    public void updateprofile(int id , String name,String dob, String email, Uri imageuri){
        db.updateProfile(id, name,dob, email, imageuri);
    }
    public Cursor getProfile(){
        SQLiteDatabase readable= db.getReadableDatabase();
        return readable.rawQuery("SELECT * FROM " + StackDB.TABLE_PROFILE + " LIMIT 1", null);
    }
    public void deleteprofile(int id){
        db.deleteProfile(id);
    }


}