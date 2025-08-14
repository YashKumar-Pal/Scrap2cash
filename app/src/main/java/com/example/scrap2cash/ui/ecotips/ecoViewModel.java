package com.example.scrap2cash.ui.ecotips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ecoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ecoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is eco tips fragment");
    }

    public LiveData<String> getText() {return mText;
    }
}