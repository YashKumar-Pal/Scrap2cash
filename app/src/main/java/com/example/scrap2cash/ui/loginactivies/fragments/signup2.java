package com.example.scrap2cash.ui.loginactivies.fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.scrap2cash.Appstateloginviewmodel;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentSignup2Binding;
import com.example.scrap2cash.ui.historyhome.HistoryhomeViewModel;

public class signup2 extends Fragment {
    private FragmentSignup2Binding binding;

//    private Signup2ViewModel mViewModel;
    public HistoryhomeViewModel viewModel;
    public Appstateloginviewmodel login;
    String fname,lname,dob;
    EditText email,password;
    Button signupbtn;
    Uri demoimg;
    Uri imageUri;

    public static signup2 newInstance() {
        return new signup2();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_signup2, container, false);
        binding=FragmentSignup2Binding.inflate(inflater, container, false);
        View root=binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HistoryhomeViewModel.class);
        viewModel.init(requireContext());
        login = new ViewModelProvider(requireActivity()).get(Appstateloginviewmodel.class);
        if(getArguments() !=null){
            fname=getArguments().getString("fname");
            lname=getArguments().getString("lname");
            dob=getArguments().getString("dob");
//            Toast.makeText(requireContext(), "this"+" "+fname+" "+lname+" "+dob, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
        }
        email=binding.editTextUsername;
        password=binding.editTextPassword;
        signupbtn=binding.buttonSignup;
        imageUri = Uri.parse("android.resource://"
                + requireContext().getPackageName()
                + "/" + R.drawable.user_circle);
        signupbtn.setOnClickListener(v-> signupfuc());
        return root;
    }
    public void signupfuc(){
        String fullname=fname+" "+lname;
        viewModel.saveProfile2(fullname,dob,email.getText().toString(),imageUri);

        Intent resultIntent = new Intent();
        SharedPreferences prefs;
        prefs= requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        prefs.edit().putBoolean("isLoggedIn", true).apply();
        resultIntent.putExtra("loginSuccess", true);
        requireActivity().setResult(Activity.RESULT_OK, resultIntent);
        requireActivity().finish();  // वापस MainActivity पर जाएगा
        login.setBool(true);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(Signup2ViewModel.class);
        // TODO: Use the ViewModel
    }

}