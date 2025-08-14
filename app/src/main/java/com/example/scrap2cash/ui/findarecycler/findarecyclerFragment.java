package com.example.scrap2cash.ui.findarecycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.databinding.FragmentFindarecyclerBinding;

public class findarecyclerFragment extends Fragment {

    private FragmentFindarecyclerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        findarecyclerViewModel recyclerViewModel =
                new ViewModelProvider(this).get(findarecyclerViewModel.class);

        binding = FragmentFindarecyclerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}