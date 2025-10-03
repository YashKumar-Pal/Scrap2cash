package com.example.scrap2cash.ui.home.PredictPrice;

import static android.app.Activity.RESULT_OK;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentPredictpriceBinding;
import com.example.scrap2cash.ui.home.SharedDataViewModel;

import java.io.File;
import java.util.ArrayList;

public class predictprice extends Fragment {
    private static final int REQUEST_IMAGE = 100;
    private Uri cameraImageUri;
    Uri imageUri;
    ImageView selectimg;
   FragmentPredictpriceBinding binding;
    Spinner ptspinner;
    Spinner Bspinner;
    String selectbrand;
    EditText model;
    EditText originalprice;
    Button pp;
    ArrayList<String> arrproducttyp= new ArrayList<>();
    ArrayList<String> arrb=new ArrayList<>();


    private predictpriceViewModel mViewModel;
    SharedDataViewModel shareVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareVM= new ViewModelProvider(requireActivity()).get(SharedDataViewModel.class);

    }

//    public static predictprice newInstance() {
//        return new predictprice();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_predictprice, container, false);
        binding = FragmentPredictpriceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        model=binding.modelid;
        originalprice=binding.originalprice;
        selectimg=binding.selectimg;
        ptspinner = binding.producttypeSpinner;
        Bspinner=binding.brandtypeSpinner;
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
//        List<String> productTypes = Arrays.asList("Laptop", "Smartphone", "TV", "DSLR Camera", "Electric Scooter",
//        "Air Conditioner" ,"Washing Machine" ,"Tablet" ,"Microwave", "Smartwatch",
//        "Gaming Console","Refrigerator");
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
//                String selectedType = parent.getItemAtPosition(position).toString();
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
//        selectbrand=Bspinner.getSelectedItem().toString();
        Bspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectbrand=Bspinner.getSelectedItem().toString();
//                String selectedType = parent.getItemAtPosition(position).toString();
                ////                Toast.makeText(requireContext(), "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "Please Select Brand", Toast.LENGTH_SHORT).show();
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
                startActivityForResult(chooser,REQUEST_IMAGE);
            }
        });
        pp.setOnClickListener(v->showPricePopup());
//        root.findViewById(R.id.stackimg).setOnClickListener(v -> openstackFragment());
//        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(predictpriceViewModel.class);
        // TODO: Use the ViewModel
    }
    private void showPricePopup() {
//        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.result_popup, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setView(popupView);
//        AlertDialog dialog = builder.create();
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.result_popup);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        String modelvalue=model.getText().toString().trim();
        String originalpricevalue=originalprice.getText().toString().trim();
        dialog.findViewById(R.id.das).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getText().toString().isEmpty() && originalprice.getText().toString().isEmpty()){
                    model.setError("Enter Model name");
                    originalprice.setError("Enter Original Price");
                }
                else {
                    if (imageUri != null) {
                          shareVM.setModel(modelvalue);
                          shareVM.setBrand(selectbrand);
                          shareVM.setOP(originalpricevalue);
                          shareVM.setCP("20000");
                          shareVM.setImage(imageUri);
                        ViewPager2 viewPager2= requireActivity().findViewById(R.id.viewpager2);
                        viewPager2.setCurrentItem(1,true);
                        selectimg.setImageResource(R.drawable.selectimg);
                        model.setText("");
                        originalprice.setText("");
                    }else {
                        Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

    }

}