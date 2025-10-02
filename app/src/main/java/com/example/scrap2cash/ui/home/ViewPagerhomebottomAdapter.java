package com.example.scrap2cash.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.scrap2cash.ui.PredictPrice.predictprice;
import com.example.scrap2cash.ui.historyhome.historyhome;
import com.example.scrap2cash.ui.stack.stackfragment;

public class ViewPagerhomebottomAdapter extends FragmentStateAdapter {
    public ViewPagerhomebottomAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
    switch (position){
        case 0: return new predictprice();
        case 1: return new stackfragment();
        case 2: return  new historyhome();
        default: return new predictprice();
    }
    }

    @Override
    public int getItemCount() {return 3;}
}
