package com.example.scrap2cash.ui.howtorecycle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class howtorecycleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public howtorecycleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}