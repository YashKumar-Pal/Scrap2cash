package com.example.scrap2cash.ui.historyhome;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scrap2cash.R;
import com.example.scrap2cash.ui.home.HomeFragment;

public class historyhome extends Fragment {

    private HistoryhomeViewModel mViewModel;

    public static historyhome newInstance() {
        return new historyhome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historyhome, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoryhomeViewModel.class);
        // TODO: Use the ViewModel
    }

}