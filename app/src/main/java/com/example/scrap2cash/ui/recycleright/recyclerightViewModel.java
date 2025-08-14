package com.example.scrap2cash.ui.recycleright;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class recyclerightViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public recyclerightViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}