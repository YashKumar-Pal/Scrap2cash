package com.example.scrap2cash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.scrap2cash.ui.home.StackDB;

public class Appstateloginviewmodel extends AndroidViewModel {
    private StackDB db;
    private MutableLiveData<Boolean> boolLiveData = new MutableLiveData<>();

    public Appstateloginviewmodel(@NonNull Application application) {
        super(application);
        db = new StackDB(application);
        boolLiveData.setValue(db.getBool()); // initial load
    }
    public LiveData<Boolean> getBoolLiveData() {
        return boolLiveData;
    }

    public void setBool(boolean value) {
        db.setBool(value);
        boolLiveData.setValue(value);
    }


}
