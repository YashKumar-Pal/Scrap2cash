package com.example.scrap2cash.ui.ecotips;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.databinding.FragmentEcotipsBinding;

public class ecotipsfragment extends Fragment {
    private FragmentEcotipsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ecoViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(ecoViewModel.class);
        binding = FragmentEcotipsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textEcotips;
//        ecoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
