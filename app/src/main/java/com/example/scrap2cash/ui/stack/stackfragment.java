package com.example.scrap2cash.ui.stack;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHomeBinding;
import com.example.scrap2cash.databinding.FragmentStackBinding;
import com.example.scrap2cash.ui.historyhome.historyhome;
import com.example.scrap2cash.ui.home.HomeFragment;

public class stackfragment extends Fragment {

    private StackViewModel mViewModel;
    private FragmentStackBinding binding;

    public static stackfragment newInstance() {
        return new stackfragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.stackimg.setOnClickListener(v -> openstackFragment());
        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
        binding.homeimg.setOnClickListener(v-> openHomeFragment());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StackViewModel.class);
        // TODO: Use the ViewModel
    }
    private void openstackFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main
                , new stackfragment());
        transaction.addToBackStack(null); // Optional: allows back navigation
        transaction.commit();
    }
    private void openHistoryFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, new historyhome());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void openHomeFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}