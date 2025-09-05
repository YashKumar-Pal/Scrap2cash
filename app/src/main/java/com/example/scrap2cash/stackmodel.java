package com.example.scrap2cash;

import android.net.Uri;
import android.widget.ImageView;

public class stackmodel {
    public int img;
    public String model;
    public String company;
    public String originalprice;
    public String usedprice;
    boolean selllater;
    public Uri imageUri;

    public stackmodel(Object image,String model,String company,String originalprice
            ,String usedprice,boolean selllater){
        if (image instanceof Integer) {
            this.img = (Integer) image;
            this.imageUri = null;
        } else if (image instanceof Uri) {
            this.imageUri = (Uri) image;
            this.img = 0;
        }
        this.model=model;
        this.company=company;
        this.originalprice=originalprice;
        this.usedprice=usedprice;
        this.selllater=selllater;
    }
}
