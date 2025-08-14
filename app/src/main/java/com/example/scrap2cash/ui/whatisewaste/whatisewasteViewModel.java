package com.example.scrap2cash.ui.whatisewaste;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class whatisewasteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public whatisewasteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}