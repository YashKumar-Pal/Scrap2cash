package com.example.scrap2cash.ui.home;

import android.media.Image;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedDataViewModel extends ViewModel {
    private  MutableLiveData<String> shareModel= new MutableLiveData<>();
    private MutableLiveData<String> shareBrand= new MutableLiveData<>();
    private  MutableLiveData<String> shareOP= new MutableLiveData<>();
    private  MutableLiveData<String> shareCP= new MutableLiveData<>();
    private MutableLiveData<Uri> shareImage=new MutableLiveData<>();

    public void setModel(String model){ shareModel.setValue(model);}
    public LiveData<String> getModel(){ return shareModel;}
    public void setBrand(String brand){ shareBrand.setValue(brand);}
    public LiveData<String> getBrand(){ return shareBrand;}
    public void setOP(String op){ shareOP.setValue(op);}
    public LiveData<String> getOP(){ return shareOP;}
    public void setCP(String cp){ shareCP.setValue(cp);}
    public LiveData<String> getCP(){ return shareCP;}
    public void setImage(Uri image){ shareImage.setValue(image);}
    public LiveData<Uri> getImage(){ return shareImage;}
    public void cleanModel(){ shareModel=new MutableLiveData<>();}
    public void cleanBrand(){ shareBrand=new MutableLiveData<>();}
    public void cleanOP(){ shareOP=new MutableLiveData<>();}
    public void cleanCP(){ shareCP=new MutableLiveData<>();}
    public void cleanImage(){ shareImage=new MutableLiveData<>();}
}
