package com.example.scrap2cash.ui.loginactivies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

//import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.scrap2cash.R;
import com.example.scrap2cash.ui.loginactivies.fragments.Login;
import com.example.scrap2cash.ui.loginactivies.fragments.Signup;
import com.example.scrap2cash.ui.loginactivies.fragments.cwgoogle;

public class loginactivities extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    int fragmentno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginactivities);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent fromact=getIntent();
        fragmentno=fromact.getIntExtra("fragmentno",1);
       openf(fragmentno);

    }
    private void openGallery() {
            Intent gallery = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
    }
    public void openf(int value){
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        if (fragmentno==1){
           ft.add(R.id.loginfl,new cwgoogle());
        } else if (fragmentno==2) {
           ft.add(R.id.loginfl,new Signup());
        }else if(fragmentno==3) {
           ft.add(R.id.loginfl,new Login());
        }else{
            Toast.makeText(this, "fragment error!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                imageUri = data.getData();
//                profileImage.setImageURI(imageUri);
            }
    }
}