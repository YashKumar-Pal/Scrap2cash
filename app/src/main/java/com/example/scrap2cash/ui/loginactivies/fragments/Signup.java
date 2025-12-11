package com.example.scrap2cash.ui.loginactivies.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentSignup2Binding;
import com.example.scrap2cash.databinding.FragmentSignupBinding;

public class Signup extends Fragment {
   private FragmentSignupBinding binding;
    private SignupViewModel mViewModel;
    EditText fname;
    EditText lname;
    EditText dob;
    Button continueb;

    public static Signup newInstance() {
        return new Signup();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_signup, container, false);
        binding=FragmentSignupBinding.inflate(inflater, container, false);
        View root=binding.getRoot();
        fname=binding.firstnameUsername2;
        lname=binding.lastUsername2;
        dob=binding.datebirth2;
        continueb=binding.buttoncontinue;
        continueb.setOnClickListener(v-> nextpage());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        // TODO: Use the ViewModel
    }
    public void nextpage(){
        if(fname.getText().toString().isEmpty() &&
           lname.getText().toString().isEmpty() &&
           dob.getText().toString().isEmpty()){
            fname.setError("Enter First Name");
            lname.setError("Enter Last Name");
            dob.setError("Enter Date of Birth");
        }else {
        signup2 signup2=new signup2();
        Bundle bundle=new Bundle();
        bundle.putString("fname",fname.getText().toString().trim());
        bundle.putString("lname",lname.getText().toString().trim());
        bundle.putString("dob",dob.getText().toString().trim());
        signup2.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.loginfl,signup2)
                .addToBackStack(null)
                .commit();}
    }

}