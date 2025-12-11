package com.example.scrap2cash.ui.historyhome;

import static android.app.Activity.RESULT_OK;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scrap2cash.Appstateloginviewmodel;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHistoryhomeBinding;
import com.example.scrap2cash.ui.loginactivies.loginactivities;
import com.example.scrap2cash.ui.home.StackDB;


import java.io.File;

public class historyhome extends Fragment {
    public Appstateloginviewmodel login2;
    private ImageView profileImage;
    private static final int REQUEST_IMAGE = 100;
    private Uri cameraImageUri;
    private ImageButton logout;
    private EditText nameEditText,dobedittext, emailEditText;
    private Button saveButton;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private TextView selectimagetext;
    private FragmentHistoryhomeBinding binding;
    private ImageView accountimg;

    private HistoryhomeViewModel mViewModel;
    HistoryhomeViewModel hviewmodel;
    int id;
    Animation tapanim;
    LinearLayout logoutll;

//    public static historyhome newInstance() {
//        return new historyhome();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryhomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        hviewmodel=new ViewModelProvider(this).get(HistoryhomeViewModel.class);
        hviewmodel.init(requireContext());
        login2 = new ViewModelProvider(requireActivity()).get(Appstateloginviewmodel.class);

        tapanim= AnimationUtils.loadAnimation(requireContext(),R.anim.tap_anim);
        profileImage=binding.profileImage;
        nameEditText=binding.nameEditText;
        dobedittext=binding.dobEditText;
        emailEditText=binding.emailEditText;
        saveButton=binding.saveButton;
        logout=binding.logoutbutton;
        logoutll=binding.logoutll;
        selectimagetext=binding.changeImageText;  // kaam main le sakte h
        id=-1;
        loadsavedprofile();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButton.startAnimation(tapanim);
                if(id!= -1){
                    updateprofile();
                }
                else {saveprofile();}
            }
        });
        selectimagetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimagetext.startAnimation(tapanim);
                showImagePickerdialog();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImage.startAnimation(tapanim);
                showImagePickerdialog();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != -1) {
                    logout.startAnimation(tapanim);
                  logoutbtnfun(id);}
            }
        });
        logoutll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != -1) {
                    logout.startAnimation(tapanim);
                    logoutbtnfun(id);}
            }

        });
//        demobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getContext(), loginactivities.class);
//                startActivity(intent);
//            }
//        });
        return root;
    }
    private void loadsavedprofile(){
        Cursor cursor= hviewmodel.getProfile();
        if(cursor != null && cursor.moveToFirst()){
            id=cursor.getInt(cursor.getColumnIndexOrThrow(StackDB.COL_ID));
            String namedemo=cursor.getString(cursor.getColumnIndexOrThrow(StackDB.COL_NAME));
            String dobdemo=cursor.getString(cursor.getColumnIndexOrThrow(StackDB.COL_DOB));
            String emaildemo=cursor.getString(cursor.getColumnIndexOrThrow(StackDB.COL_EMAIL));
            String uriStringdemo= cursor.getString(cursor.getColumnIndexOrThrow(StackDB.COL_PROFILE_URI));
            Uri imagedemo=uriStringdemo != null ? Uri.parse(uriStringdemo):null;
//        profileImage = root.findViewById(R.id.profileImage);
            if (namedemo != null && emaildemo != null && imagedemo != null && dobdemo !=null ){
                nameEditText.setText(namedemo);
                dobedittext.setText(dobdemo);
                emailEditText.setText(emaildemo);
                Glide.with(requireContext())
                        .load(imagedemo)
                        .circleCrop()
                        .into(profileImage);
                imageUri=imagedemo;
                selectimagetext.setText("Update Image");
                saveButton.setText("Update Profile");
//                profileImage.setImageURI(imagedemo);
            }
            else Toast.makeText(getContext(), "no data found", Toast.LENGTH_SHORT).show();}
        if(cursor !=null){
            cursor.close();
        }
    }
    private void saveprofile(){
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String dob=dobedittext.getText().toString();
        if(name.isEmpty() && email.isEmpty() && dob.isEmpty()){
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
//            return;
        } else if (imageUri ==null) {
            Toast.makeText(getContext(), "select image", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
            hviewmodel.saveProfile2(nameEditText.getText().toString(),dobedittext.getText().toString(), emailEditText.getText().toString(),imageUri);
            login2.setBool(true);
            selectimagetext.setText("Update Image");
            saveButton.setText("Update Profile");
//            id++;
            loadsavedprofile();
        }// You can save this data to SharedPreferences or Firebase here
    }
    private void updateprofile() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (imageUri == null) {
            Toast.makeText(getContext(), "select image", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
            hviewmodel.updateprofile(id, nameEditText.getText().toString(),dobedittext.getText().toString(), emailEditText.getText().toString(), imageUri);
                selectimagetext.setText("Update Image");
                saveButton.setText("Update Profile");
                login2.setBool(true);
           loadsavedprofile();
        }
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (imageUri != null) {
//            try {
//                requireContext().getContentResolver().takePersistableUriPermission(
//                        imageUri,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION
//                );
//            } catch (SecurityException e) {
//                e.printStackTrace();
//            }
//        }
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            if (data == null|| data.getData()==null) {
            imageUri=cameraImageUri;}
            else{
                imageUri=data.getData();
            }
//            profileImage.setImageURI(imageUri);
            Glide.with(requireContext())
                    .load(imageUri)
                    .circleCrop()
                    .into(binding.profileImage);
        }
    }
    public void logoutbtnfun(int id){
        Dialog dialog= new Dialog(requireContext());
        dialog.setContentView(R.layout.logout_popup);
        dialog.show();
        Button yesbtn=dialog.findViewById(R.id.yes);
        Button nobtn=dialog.findViewById(R.id.no);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesbtn.setAnimation(tapanim);
                hviewmodel.deleteprofile(id);
                login2.setBool(false);
                profileImage.setImageResource(R.drawable.user_circle);
                nameEditText.getText().clear();
                dobedittext.getText().clear();
                emailEditText.getText().clear();
                saveButton.setText("Save");
                dialog.dismiss();
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nobtn.setAnimation(tapanim);
                Toast.makeText(getContext(), "Profile delete cencel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistoryhomeViewModel.class);
        // TODO: Use the ViewModel
    }

}