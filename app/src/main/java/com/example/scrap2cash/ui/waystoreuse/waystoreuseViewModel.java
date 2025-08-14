package com.example.scrap2cash.ui.waystoreuse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class waystoreuseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public waystoreuseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public waystoreuseViewModel(MutableLiveData<String> mText) {
        this.mText = mText;
    }

    public LiveData<String> getText() {
        return mText;
    }
}