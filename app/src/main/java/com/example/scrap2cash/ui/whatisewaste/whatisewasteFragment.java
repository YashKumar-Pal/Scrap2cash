package com.example.scrap2cash.ui.whatisewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.databinding.FragmentWhatisewasteBinding;

public class whatisewasteFragment extends Fragment {

    private FragmentWhatisewasteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        whatisewasteViewModel galleryViewModel =
                new ViewModelProvider(this).get(whatisewasteViewModel.class);

        binding = FragmentWhatisewasteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}