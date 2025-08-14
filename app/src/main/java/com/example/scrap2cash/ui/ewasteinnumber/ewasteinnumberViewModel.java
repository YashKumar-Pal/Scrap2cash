package com.example.scrap2cash.ui.ewasteinnumber;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ewasteinnumberViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ewasteinnumberViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This fragment is e-waste in number" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}