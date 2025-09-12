package com.example.scrap2cash.ui.historyhome;

import static android.app.Activity.RESULT_OK;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHistoryhomeBinding;
import com.example.scrap2cash.databinding.FragmentStackBinding;
import com.example.scrap2cash.ui.home.HomeFragment;
import com.example.scrap2cash.ui.stack.stackfragment;

import java.io.File;

public class historyhome extends Fragment {
    private ImageView profileImage;
    private static final int REQUEST_IMAGE = 100;
    private Uri cameraImageUri;

    private EditText nameEditText, emailEditText;
    private Button saveButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private TextView selectimage;
    private FragmentHistoryhomeBinding binding;
    private ImageView accountimg;

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
        accountimg=root.findViewById(R.id.historyimg);
        selectimage=root.findViewById(R.id.changeImageText);
        selectimage.setOnClickListener(v -> showImagePickerdialog());
        profileImage.setOnClickListener(v -> showImagePickerdialog());
        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            if(name.isEmpty() || email.isEmpty()){
                Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }else{
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();}
            // You can save this data to SharedPreferences or Firebase here
        });
        binding.stackimg.setOnClickListener(v -> openstackFragment());
//        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
        binding.homeimg.setOnClickListener(v -> openHomeFragment());
        return root;
    }
    private void showImagePickerdialog(){
        Intent cameraintent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "camera_image_" + System.currentTimeMillis() + ".jpg");

        cameraImageUri = FileProvider.getUriForFile(requireContext(),
                "com.example.scrap2cash.fileprovider", imageFile);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        Intent galleryintent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryintent.setType("image/*");
        Intent chooser= Intent.createChooser(galleryintent,"Select Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{cameraintent});
        startActivityForResult(chooser,REQUEST_IMAGE);
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
//    private void openGallery() {
//        Intent gallery = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(gallery, PICK_IMAGE);
//    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            if (data == null|| data.getData()==null) {
            imageUri=cameraImageUri;}
            else{
                imageUri=data.getData();
            }
            profileImage.setImageURI(imageUri);
            Glide.with(requireContext())
                    .load(imageUri)
                    .circleCrop()
                    .into(accountimg);
        }
    }

}