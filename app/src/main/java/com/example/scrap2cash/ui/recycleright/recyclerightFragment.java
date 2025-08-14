package com.example.scrap2cash.ui.recycleright;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.databinding.FragmentRecyclerightBinding;

public class recyclerightFragment extends Fragment {

    private FragmentRecyclerightBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recyclerightViewModel homeViewModel =
                new ViewModelProvider(this).get(recyclerightViewModel.class);

        binding = FragmentRecyclerightBinding.inflate(inflater, container, false);
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