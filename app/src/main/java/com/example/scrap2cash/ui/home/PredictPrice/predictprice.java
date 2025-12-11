package com.example.scrap2cash.ui.home.PredictPrice;

import static android.app.Activity.RESULT_OK;

import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.scrap2cash.Appstateloginviewmodel;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentPredictpriceBinding;
import com.example.scrap2cash.ui.home.SharedDataViewModel;
import com.example.scrap2cash.ui.loginactivies.loginactivities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class predictprice extends Fragment {
    String url="https://scrap2cash.onrender.com/predict";
    public Appstateloginviewmodel login2;
    Button signup,login,skip;
    Animation tapanim;
//    String url="http://scrap2cash.onrender.com";
    String result;
    ImageView closebtn;
    private static final int REQUEST_IMAGE = 100;
    private Uri cameraImageUri;
    Uri imageUri;
    ImageView selectimg;
   FragmentPredictpriceBinding binding;
    Spinner ptspinner;
    Spinner Bspinner;
    EditText model;
    EditText buildquality;
    EditText userlifespan;
    Spinner upspinner;
    EditText expiryyear;
    EditText condition;
    EditText originalprice;
    EditText usedduration;
//    selectitem to string
    String selectedproducttype;
    String selectbrand;
    String selectusagepattern;



    Button pp;
    private predictpriceViewModel mViewModel;
    SharedDataViewModel shareVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareVM= new ViewModelProvider(requireActivity()).get(SharedDataViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_predictprice, container, false);
        binding = FragmentPredictpriceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        login2 = new ViewModelProvider(requireActivity()).get(Appstateloginviewmodel.class);
        tapanim= AnimationUtils.loadAnimation(requireContext(),R.anim.tap_anim);
//        all variable ko value di
        ptspinner = binding.producttypeSpinner;
        Bspinner=binding.brandtypeSpinner;
        model=binding.modelid;
        buildquality=binding.buildquality;
        userlifespan=binding.userLifespan;
        upspinner=binding.usagepatternSpinner;
        expiryyear=binding.expiryyear;
        condition=binding.condition;
        originalprice=binding.originalprice;
        usedduration=binding.usedduration;
        selectimg=binding.selectimg;
        pp=binding.predictbtn;
//        give value to product type spinner
        ArrayList<String> arrproducttyp= new ArrayList<>();
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
                selectedproducttype=ptspinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional
            }
        });
//        give value to Brand spinner
//        arrb.add("HP");
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
//        give value to usage pattern
        ArrayList<String> arrup=new ArrayList<>();
        arrup.add("Light");
        arrup.add("Heavy");
        arrup.add("Moderate");
        ArrayAdapter<String> adapter3=new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_selected_item,
                arrup
        );
        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_items);
        upspinner.setAdapter(adapter3);
        upspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectusagepattern=upspinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//select image
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
        pp.setOnClickListener(v->logincondition());
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
    public void logincondition(){
        login2.getBoolLiveData().observe(getViewLifecycleOwner(), isLoggedIn -> {
            if (Boolean.TRUE.equals(isLoggedIn)) {
                extra();
            } else {
                Toast.makeText(getContext(), "Login First", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void extra(){
        if (buildquality.getText().toString().isEmpty() && userlifespan.getText().toString().isEmpty() &&
                expiryyear.getText().toString().isEmpty() &&
                condition.getText().toString().isEmpty() &&
                originalprice.getText().toString().isEmpty() &&
                usedduration.getText().toString().isEmpty() &&
                model.getText().toString().isEmpty()) {
            buildquality.setError("Enter Build Quality");
            userlifespan.setError("Enter User Lifespan");
            expiryyear.setError("Enter Expiry Year");
            condition.setError("Enter Condition");
            usedduration.setError("Enter Used Duration");
            model.setError("Enter Model name");
            originalprice.setError("Enter Original Price");
        }else {
            if(imageUri != null){
                serverrequest();
            }
            else {
                Toast.makeText(getContext(), "Please Upload Image", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void serverrequest() {
//        for active server
        ProgressDialog loadingDialog = new ProgressDialog(requireContext());
        loadingDialog.setMessage("Predicting price... (This may take up to 1 min)");
        loadingDialog.setCancelable(true);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
//            if server responce
                    @Override
                    public void onResponse(String s) {
                        try{
                        JSONObject jsonObject=new JSONObject(s) ;
                        result=jsonObject.getString("predicted_price");
//                            Toast.makeText(requireContext(), "result", Toast.LENGTH_SHORT).show();
                           showpredictprice();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
//                if server not responce
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                        volleyError.printStackTrace();

                        // Show the error message in the Toast (if available)
                        String message = volleyError.getMessage();
                        if (message == null && volleyError.networkResponse != null) {
                            message = "Status Code: " + volleyError.networkResponse.statusCode;
                        }
                        Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_LONG).show();
                    }

        }){
//            give parameter to model
         @Override
         protected Map<String,String> getParams() {
             Map<String, String> params = new HashMap<String, String>();
                     params.put("Product_Type", selectedproducttype);
                     params.put("Brand", selectbrand);
                     params.put("Build_Quality", buildquality.getText().toString().trim());
                     params.put("User_Lifespan", userlifespan.getText().toString().trim());
                     params.put("Usage_Pattern", selectusagepattern);
                     params.put("Expiry_Years", expiryyear.getText().toString().trim());
                     params.put("Condition", condition.getText().toString().trim());
                     params.put("Original_price", originalprice.getText().toString().trim());
                     params.put("Used_Duration", usedduration.getText().toString().trim());
                     return params;
         }
        };
//        retry server to active
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, // timeout in ms (10 seconds)
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue =Volley.newRequestQueue(requireContext());
        queue.add(stringRequest);
    }
    public void showpredictprice(){
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.result_popup);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        TextView result_massage=dialog.findViewById(R.id.result_message);
        result_massage.setText("your device current price is:"+result);
        String modelvalue=model.getText().toString().trim();
        closebtn=dialog.findViewById(R.id.closeresultpop);
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
                        shareVM.setCP(result);
                        shareVM.setImage(imageUri);
                        ViewPager2 viewPager2= requireActivity().findViewById(R.id.viewpager2);
                        viewPager2.setCurrentItem(1,true);
                        selectimg.setImageResource(R.drawable.selectimg);
                        model.setText("");
                        originalprice.setText("");
                        buildquality.setText("");
                        userlifespan.setText("");
                        expiryyear.setText("");
                        condition.setText("");
                        usedduration.setText("");
                    }else {
                        Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
                    }
                }
                closebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.dismiss(); }
                });
                dialog.dismiss();
            }
        });
    }
    private void showloginpopup(){
        Dialog dialog= new Dialog(requireContext());
        dialog.setContentView(R.layout.login_alertdialog);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        skip=dialog.findViewById(R.id.skip2);
//        SpannableString spannableString=new SpannableString("   Continue with Google");
//        Drawable icon= ContextCompat.getDrawable(requireContext(),R.drawable.google_g_icon);
//        icon.setBounds(0,0,icon.getIntrinsicWidth(),icon.getIntrinsicHeight());
//        ImageSpan imageSpan=new ImageSpan(icon, ImageSpan.ALIGN_BOTTOM);
//        spannableString.setSpan(imageSpan,1,2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        cwgoogle.setText(spannableString);
        signup=dialog.findViewById(R.id.signup);
        login=dialog.findViewById(R.id.login);
        signup.setOnClickListener(view-> signup());
        login.setOnClickListener(view->login());
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    public void login(){
        Intent intent= new Intent(getContext(), loginactivities.class);
        login.startAnimation(tapanim);
        intent.putExtra("fragmentno",1);
        startActivityForResult(intent,100);
    }
    public void signup(){
        signup.startAnimation(tapanim);
        Intent intent=new Intent(getContext(), loginactivities.class);
        intent.putExtra("fragmentno",2);
        startActivityForResult(intent,100);
    }

}