package com.example.scrap2cash.ui.home;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHomeBinding;
import com.example.scrap2cash.ui.historyhome.historyhome;
import com.example.scrap2cash.ui.stack.stackfragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class HomeFragment extends Fragment {
    private static final int REQUEST_IMAGE = 100;
    private Uri cameraImageUri;
    Uri imageUri;
    ImageView selectimg;
    private FragmentHomeBinding binding;
    Spinner ptspinner;
    Spinner Bspinner;
    Button pp;
    ArrayList<String> arrproducttyp= new ArrayList<>();
    ArrayList<String> arrb=new ArrayList<>();
    BottomNavigationView btmnv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        selectimg=binding.selectimg;
        ptspinner = binding.producttypeSpinner;
        Bspinner= binding.brandtypeSpinner;
        pp=binding.predictbtn;
        arrproducttyp.add("Laptop");
        arrproducttyp.add("Smartphone");
        arrproducttyp.add("TV");
        arrproducttyp.add("DSLR Camera");
        arrproducttyp.add("Electric Scooter");
        arrproducttyp.add("Air Conditioner");
        arrproducttyp.add("Washing Machine");
        arrproducttyp.add("Tablet");
        arrproducttyp.add("Microwave");
        arrproducttyp.add("Smartwatch");
        arrproducttyp.add("Gaming Console");
        arrproducttyp.add("Refrigerator");
        // Agar dynamic list chahiye:
        List<String> productTypes = Arrays.asList("Laptop", "Smartphone", "TV", "DSLR Camera", "Electric Scooter",
        "Air Conditioner" ,"Washing Machine" ,"Tablet" ,"Microwave", "Smartwatch",
        "Gaming Consol","Refrigerator");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_selected_item,         // selected item layout
                arrproducttyp                           // list of items
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_items); // dropdown layout
        ptspinner.setAdapter(adapter);
        ptspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional
            }
        });
        arrb.add("HP");
        ArrayList<String> arrb = new ArrayList<>();
        arrb.add("HP");
        arrb.add("OnePlus");
        arrb.add("Lenovo");
        arrb.add("Sony");
        arrb.add("TVS");
        arrb.add("Samsung");
        arrb.add("Fujifilm");
        arrb.add("Hero");
        arrb.add("Blue Star");
        arrb.add("Daikin");
        arrb.add("LG");
        arrb.add("Bajaj");
        arrb.add("Morphy Richards");
        arrb.add("Voltas");
        arrb.add("Noise");
        arrb.add("Apple");
        arrb.add("IFB");
        arrb.add("Canon");
        arrb.add("Vivo");
        arrb.add("Bosch");
        arrb.add("TCL");
        arrb.add("Amazfit");
        arrb.add("Acer");
        arrb.add("Huawei");
        arrb.add("Whirlpool");
        arrb.add("Panasonic");
        arrb.add("Microsoft");
        arrb.add("Ather");
        arrb.add("Dell");
        arrb.add("Ola");
        arrb.add("Nikon");
        arrb.add("Nintendo");
        arrb.add("Boat");
        arrb.add("Asus");
        arrb.add("Realme");
        arrb.add("Carrier");
        arrb.add("Xiaomi");
        arrb.add("Godrej");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_selected_item,         // selected item layout
                arrb                         // list of items
        );
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_items); // dropdown layout
        Bspinner.setAdapter(adapter2);
        Bspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
              ////                Toast.makeText(requireContext(), "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional
            }
        });
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                startActivityForResult(chooser,REQUEST_IMAGE);;
            }
        });

        binding.predictbtn.setOnClickListener(v->showPricePopup());
        binding.stackimg.setOnClickListener(v -> openstackFragment());
        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
//        binding.homeimg.setOnClickListener(v-> openHomeFragment());
         return root;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK ) {
            if (data == null|| data.getData()==null) {
                imageUri=cameraImageUri;}
            else{
                imageUri=data.getData();
            }
            Glide.with(requireContext()).load(imageUri).into(selectimg);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void openstackFragment() {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment_content_main
                    , new stackfragment());
            transaction.addToBackStack(null); // Optional: allows back navigation
            transaction.commit();

        }
    private void openHistoryFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, new historyhome());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void openHomeFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void showPricePopup() {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.result_popup, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setView(popupView);
//        AlertDialog dialog = builder.create();
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.result_popup);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        dialog.findViewById(R.id.das).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri!= null){
                Bundle result = new Bundle();
                result.putString("Model", "Aspire 14");
                result.putString("Brand", "Acer");
                result.putString("original price", "40000");
                result.putString("current", "20000");
                result.putParcelable("demo image", imageUri);

                new Handler(Looper.getMainLooper()).postDelayed(() ->{
                    getParentFragmentManager().setFragmentResult("datakey", result);
                    FragmentTransaction transaction=requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment_content_main,new stackfragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                        },300);

                }
                dialog.dismiss();
            }
        });
    }
}