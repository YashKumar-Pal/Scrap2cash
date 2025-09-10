package com.example.scrap2cash.ui.historyhome;

import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHistoryhomeBinding;
import com.example.scrap2cash.databinding.FragmentStackBinding;
import com.example.scrap2cash.ui.home.HomeFragment;
import com.example.scrap2cash.ui.stack.stackfragment;

public class historyhome extends Fragment {
    private ImageView profileImage;
    private EditText nameEditText, emailEditText;
    private Button saveButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private FragmentHistoryhomeBinding binding;

    private HistoryhomeViewModel mViewModel;

    public static historyhome newInstance() {
        return new historyhome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryhomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        profileImage = root.findViewById(R.id.profileImage);
        nameEditText = root.findViewById(R.id.nameEditText);
        emailEditText = root.findViewById(R.id.emailEditText);
        saveButton = root.findViewById(R.id.saveButton);
        profileImage.setOnClickListener(v -> openGallery());
        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            // You can save this data to SharedPreferences or Firebase here
        });
        binding.stackimg.setOnClickListener(v -> openstackFragment());
//        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
        binding.homeimg.setOnClickListener(v -> openHomeFragment());
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoryhomeViewModel.class);
        // TODO: Use the ViewModel
    }
    private void openHomeFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void openstackFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main
                , new stackfragment());
        transaction.addToBackStack(null); // Optional: allows back navigation
        transaction.commit();
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }

}