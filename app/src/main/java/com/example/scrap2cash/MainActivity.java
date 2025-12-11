package com.example.scrap2cash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.scrap2cash.ui.historyhome.historyhome;
import com.example.scrap2cash.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scrap2cash.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    // Activity Result API launcher
    private ActivityResultLauncher<Intent> loginLauncher;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            boolean success = data.getBooleanExtra("loginSuccess", false);
            if (success) {
                openAccountFragment();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT); // Makes top bar transparent
//        }
//        requirecontext this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (this.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
//             Stop here until permission is granted
            }
        }

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setBackground(ContextCompat.getDrawable(this, R.drawable.toolbar_bg));
        binding.appBarMain.appbarOftoolbar.setElevation(0f);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.nav_1,R.id.nav_2,R.id.nav_3,R.id.nav_4,R.id.nav_5,R.id.login,/* R.id.nav_7,*/R.id.nav_8, R.id.nav_10/*,R.id.nav_11*/)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        ImageView profileIcon = findViewById(R.id.profile_icon);
        navigationView.setNavigationItemSelectedListener(item->{
            int id=item.getItemId();
            if(id==R.id.nav_8){
                showRateUsDialog();
                drawer.closeDrawers();
                return true;
            } else if (id==R.id.nav_10) {
               shareApkFromRaw();
            } else{
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                if (handled) drawer.closeDrawers();
                return handled;
            }
            return true;
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void openAccountFragment() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_login);

    }
    public void showRateUsDialog() {
        // Inflate custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rate_us, null);

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingbar);
        EditText etFeedback = dialogView.findViewById(R.id.etFeedback);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = etFeedback.getText().toString();
            String body = "User Rating: " + rating + " stars\n\nFeedback:\n" + feedback;


            Intent iemail=new Intent(Intent.ACTION_SEND);
            iemail.setType("message/rtc822");
            iemail.putExtra(Intent.EXTRA_EMAIL,new String[]{"bhaveshsharmabsr45@gmail.com","sharmaayush3219@gmail.com","yashkumar30095@gmail.com"});
            iemail.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
            iemail.putExtra(Intent.EXTRA_TEXT,body);
            if (iemail.resolveActivity(getPackageManager()) !=null){
                startActivity(iemail);
            }
            Toast.makeText(this, "Thanks for rating: " + rating + "⭐", Toast.LENGTH_SHORT).show();

            // TODO: send feedback to server or email
            dialog.dismiss();
        });

        dialog.show();
    }
    private void shareApkFromRaw() {
        try {
            // Copy raw file to cache
            InputStream inputStream = getResources().openRawResource(R.raw.myapp);
            File outFile = new File(getCacheDir(), "myapp.apk");
            FileOutputStream outputStream = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            // Get URI via FileProvider
            Uri apkUri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext().getPackageName() + ".provider",
                    outFile);

            // Share intent
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, apkUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(intent, "Share App via"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error sharing file", Toast.LENGTH_SHORT).show();
        }
    }

//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder exitDialog=new AlertDialog.Builder(this);
//        exitDialog.setTitle("Exit?");
//        exitDialog.setMessage("Are you sure want ot Exit?");
//        exitDialog.setIcon(R.drawable.exit_icon);
//        exitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                MainActivity.super.onBackPressed();
////              super.onBackPressed();  not allow
//            }
//        });
//        exitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "App close cancel!", Toast.LENGTH_SHORT).show();
//            }
//        });
////       exitDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
////           @Override
////           public void onClick(DialogInterface dialog, int which) {
////               Toast.makeText(MainActivity.this, "Operation cancelled!", Toast.LENGTH_SHORT).show();
////           }
////       });
//        exitDialog.show();
//
//
//    }

}