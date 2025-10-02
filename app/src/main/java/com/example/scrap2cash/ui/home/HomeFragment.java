package com.example.scrap2cash.ui.home;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private BottomNavigationView bnview;
    private ViewPager2 viewpager2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bnview=binding.bottomnav;
        viewpager2=binding.viewpager2;
        ViewPagerhomebottomAdapter adapter= new ViewPagerhomebottomAdapter(requireActivity());
        viewpager2.setAdapter(adapter);
        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position){
                switch (position){
                    case 0: bnview.setSelectedItemId(R.id.predictf);break;
                    case 1: bnview.setSelectedItemId(R.id.stackf);break;
                    case 2: bnview.setSelectedItemId(R.id.account);break;
                }
            }
        });
        bnview.setOnNavigationItemSelectedListener(menuItem -> {
            int id= menuItem.getItemId();
            if (id==R.id.predictf){
                viewpager2.setCurrentItem(0);
                return true;
            } else if (id==R.id.stackf) {
                viewpager2.setCurrentItem(1);
                return true;
            }else {
                viewpager2.setCurrentItem(2);
                return true;
            }
        });
        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}