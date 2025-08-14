package com.example.scrap2cash.ui.ewasteinnumber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.databinding.FragmentEwasteinnumberBinding;


public class ewasteinnumberFragment extends Fragment {

    private FragmentEwasteinnumberBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ewasteinnumberViewModel slideshowViewModel =
                new ViewModelProvider(requireActivity()).get(ewasteinnumberViewModel.class);
        binding = FragmentEwasteinnumberBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textSlideshow;
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}