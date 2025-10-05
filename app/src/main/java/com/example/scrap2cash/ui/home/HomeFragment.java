package com.example.scrap2cash.ui.home;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHomeBinding;
import com.example.scrap2cash.ui.loginactivies.loginactivities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
//bottom_menu
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private BottomNavigationView bnview;
    private ViewPager2 viewpager2;
    int id;
    Animation tapanim;
    Button signup;
    Button login;
    ImageView closedialog;
    Button cwgoogle;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bnview=binding.bottomnav;
        viewpager2=binding.viewpager2;
        tapanim= AnimationUtils.loadAnimation(requireContext(),R.anim.tap_anim);
        ViewPagerhomebottomAdapter adapter= new ViewPagerhomebottomAdapter(requireActivity());
        viewpager2.setAdapter(adapter);
        viewpager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewpager2.setOffscreenPageLimit(3);
        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position){
                switch (position){
                    case 0: bnview.setSelectedItemId(R.id.nav_home);break;
                    case 1: bnview.setSelectedItemId(R.id.nav_stack);break;
                    case 2: bnview.setSelectedItemId(R.id.nav_login);break;
                }
            }
        });
        bnview.setOnNavigationItemSelectedListener(menuItem -> {
            id= menuItem.getItemId();
            if (id==R.id.nav_home){
                viewpager2.setCurrentItem(0);return true;
            } else if (id==R.id.nav_stack) {
                viewpager2.setCurrentItem(1);return true;
            }else{ viewpager2.setCurrentItem(2);return true;}
        });
        Dialog dialog= new Dialog(requireContext());
        dialog.setContentView(R.layout.login_alertdialog);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        cwgoogle=dialog.findViewById(R.id.cwgoogle);
        SpannableString spannableString=new SpannableString("   Continue with Google");
        Drawable icon= ContextCompat.getDrawable(requireContext(),R.drawable.google_g_icon);
        icon.setBounds(0,0,icon.getIntrinsicWidth(),icon.getIntrinsicHeight());
        ImageSpan imageSpan=new ImageSpan(icon, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(imageSpan,1,2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        cwgoogle.setText(spannableString);
        signup=dialog.findViewById(R.id.signup);
        login=dialog.findViewById(R.id.login);
        closedialog=dialog.findViewById(R.id.closebtn);
        cwgoogle.setOnClickListener(view-> cwgoogle());
        signup.setOnClickListener(view-> signup());
        login.setOnClickListener(view->login());
        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (id == R.id.nav_home) {
                    AlertDialog.Builder exitDialog = new AlertDialog.Builder(requireActivity());//, androidx.appcompat.R.style.Theme_AppCompat_Dialog);
                    exitDialog.setTitle("Exit?");
                    exitDialog.setMessage("Are you sure want ot Exit?");
                    exitDialog.setIcon(R.drawable.exit_icon);
                    exitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        requireActivity().finish();
                      }
                    });
                    exitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "App close cancel!", Toast.LENGTH_SHORT).show();
                      }
                    });
                    exitDialog.show();

                } else if (id==R.id.nav_stack) {
                    viewpager2.setCurrentItem(0);
                }else {
                    viewpager2.setCurrentItem(0);
                }
            }


        });
        return root;

    }
    public void login(){
        Intent intent= new Intent(getContext(), loginactivities.class);
        login.startAnimation(tapanim);
        intent.putExtra("fragmentno",1);
        startActivity(intent);
    }
    public void signup(){
        signup.startAnimation(tapanim);
        Intent intent=new Intent(getContext(), loginactivities.class);
        intent.putExtra("fragmentno",2);
        startActivity(intent);
    }
    public void cwgoogle(){
        cwgoogle.startAnimation(tapanim);
        Intent intent=new Intent(getContext(), loginactivities.class);
        intent.putExtra("fragmentno",3);
        startActivity(intent);
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

}