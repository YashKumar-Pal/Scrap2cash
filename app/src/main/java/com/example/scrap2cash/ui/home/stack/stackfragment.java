package com.example.scrap2cash.ui.home.stack;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.NOTIFICATION_SERVICE;

import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.scrap2cash.Appstateloginviewmodel;
import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentStackBinding;
import com.example.scrap2cash.ui.home.SharedDataViewModel;
import com.example.scrap2cash.ui.home.StackDB;
import com.example.scrap2cash.ui.loginactivies.loginactivities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;


public class stackfragment extends Fragment {
    public Appstateloginviewmodel login2;
    Button signup,login,skip;
    Animation tapanim;
    StackViewModel viewModel;
    Uri fimg=null;
    private static final int REQUEST_IMAGE = 100;
    private Uri cameraImageUri;
    private static final String CHHANEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;
    private StackViewModel mViewModel;
    private FragmentStackBinding binding;
    RecyclerView recyclerview;
    FloatingActionButton fbtn;
    ArrayList<stackmodel> arrstack;
    private RecyclerstackAdapter adapter;
    Uri imageUri;
    ImageView itemimg;
    Uri setimageUri;
    String fmodel=null;
    String fcompany=null;
    String foriginal=null;
    String fused=null;
    int setimageint;
    private StackDB stackDB;
//    for notification
    NotificationManager nm;
    Notification notificationtc;
    SharedDataViewModel shareVM;
        //    main function
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        stackDB = new StackDB(requireContext());
//        login2 = new ViewModelProvider(requireActivity()).get(Appstateloginviewmodel.class);
//        login2.getBoolLiveData().observe(getViewLifecycleOwner(), isLoggedIn -> {
//            if (isLoggedIn) {
//                Log.d("HomeFragment", "Login flag is TRUE");
//            } else {
//                showloginpopup();
//            }
//        });
        tapanim= AnimationUtils.loadAnimation(requireContext(),R.anim.tap_anim);
        viewModel= new ViewModelProvider(this).get(StackViewModel.class);
        viewModel.loadItemsFromDB(stackDB);
        shareVM=new ViewModelProvider(requireActivity()).get(SharedDataViewModel.class);
        shareVM.getModel().observe(getViewLifecycleOwner(),sharemodel->{
            fmodel=sharemodel;
        });
        shareVM.getBrand().observe(getViewLifecycleOwner(),sharebrand->{
            fcompany=sharebrand;
        });
        shareVM.getOP().observe(getViewLifecycleOwner(),shareop ->{
            foriginal=shareop;
        });
        shareVM.getCP().observe(getViewLifecycleOwner(),shareop->{
            fused=shareop;
        });
        shareVM.getImage().observe(getViewLifecycleOwner(),sharimage->{
            fimg=sharimage;
            tryAddItem();
        });









        recyclerview = binding.mainrecyclerview;
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        recycler view main item bind hone ke liye recycler adapter class call ho rhi h
        arrstack = new ArrayList<>();
        adapter = new RecyclerstackAdapter(getContext(), arrstack, (model, position) -> {
//            gallery se image pick ke lye function call ho rha h
            showUpdateDialog(model, position);
        });
//        recycler view main element ko content de rha h
        recyclerview.setAdapter(adapter);
        viewModel.getScrapItems().observe(getViewLifecycleOwner(), items -> {
            adapter.updateList(items); // adapter mein method hona chahiye
        });

        //        new item add button
        fbtn = binding.fbtn;
//            drawable se notification main image set karne ke liye
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.info_icon2, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap setLargeIcon2 = bitmapDrawable.getBitmap();
        nm = (NotificationManager) requireContext().getSystemService(NOTIFICATION_SERVICE);
//        notification set karne ke liye channel id se ki ne item add hua
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationtc = new Notification.Builder(requireContext())
                    .setLargeIcon(setLargeIcon2)
                    .setSmallIcon(R.mipmap.scrap2cash_icon3)
                    .setContentText("New Item Added")
                    .setSubText("Alert! Someone Add New Item")
                    .setChannelId(CHHANEL_ID)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHHANEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
        } else {
            notificationtc = new Notification.Builder(requireContext())
                    .setLargeIcon(setLargeIcon2)
                    .setSmallIcon(R.drawable.info_icon2)
                    .setContentText("New item Added")
                    .setSubText("Alert! Someone Add New Item")
                    .build();
        }
//        add button pe click hone par
        fbtn.setOnClickListener(v->fbtnadd());
        return root;
    }// khatam oncreate method
private void fbtnadd(){
    Dialog dialog = new Dialog(requireContext());
    dialog.setContentView(R.layout.add_item);
    int width = LinearLayout.LayoutParams.MATCH_PARENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    dialog.getWindow().setLayout(width, height);
    dialog.show();
//                sare item grap kiye
    EditText modelname = dialog.findViewById(R.id.mname);
    EditText companyname = dialog.findViewById(R.id.cname);
    EditText originalprice = dialog.findViewById(R.id.oprice);
    EditText usedprice = dialog.findViewById(R.id.uprice);
    ImageView itemimgd = dialog.findViewById(R.id.itemimg);
    itemimg = itemimgd;
//                image add karne ke liye
    itemimgd.setOnClickListener(new View.OnClickListener() {
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
//              add item pe click karne par
    dialog.findViewById(R.id.additem).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//                        check all are fill
            if (modelname.getText().toString().isEmpty() | companyname.getText().toString().isEmpty() |
                    originalprice.getText().toString().isEmpty() | usedprice.getText().toString().isEmpty()) {
                modelname.setError("Enter Model Name");
                companyname.setError("Enter Company Name");
                originalprice.setError("Enter Original Price");
                usedprice.setError("Enter Used Price");
            } else {
                if (imageUri != null) {
                    stackmodel newItem = new stackmodel(
                            imageUri,
                            modelname.getText().toString(),
                            companyname.getText().toString(),
                            originalprice.getText().toString(),
                            usedprice.getText().toString(),
                            false
                    );
                    viewModel.addItem(newItem, stackDB);
                    nm.notify(NOTIFICATION_ID, notificationtc);
                    dialog.dismiss();
                    imageUri = null; // Reset for next item
                } else {
                    Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }

            }
        }
    });
}

    private void showUpdateDialog(stackmodel model, int position) {
//        stackmodel model = arrstack.get(position);
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.add_item);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
//        sare edittext or image grap kana
        EditText modelthis = dialog.findViewById(R.id.mname);
        EditText companythis = dialog.findViewById(R.id.cname);
        EditText opricethis = dialog.findViewById(R.id.oprice);
        EditText upricethis = dialog.findViewById(R.id.uprice);
        Button updatebtn = dialog.findViewById(R.id.additem);
        ImageView itemimgthis = dialog.findViewById(R.id.itemimg);
        TextView maintxt = dialog.findViewById(R.id.update);
        TextView subtext=dialog.findViewById(R.id.chooseimg);
//        pahli value set karna
        modelthis.setText(model.model);
        companythis.setText(model.company);
        opricethis.setText(model.originalprice);
        upricethis.setText(model.usedprice);
        if (model.imageUri != null) {
            Glide.with(requireContext()).load(model.imageUri).into(itemimgthis);
        } else {
            itemimgthis.setImageResource(model.img);
        }
        itemimg=itemimgthis;
        if (model.imageUri != null) {
            setimageUri=model.imageUri;
            imageUri=setimageUri;
            setimageint=0;
        } else {
            setimageint =model.img;
        }
        subtext.setText("Click Image to Update");
        updatebtn.setText("Update");
        maintxt.setText("Update Item ");
        itemimgthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setimageint=0;
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
// recycler view main dialog me button se update item karne ke liye
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ids=model.id;
                String updatedModel = modelthis.getText().toString().trim();
                String updatedCompany = companythis.getText().toString().trim();
                String updatedOPrice = opricethis.getText().toString().trim();
                String updatedUPrice = upricethis.getText().toString().trim();
                if (updatedModel.isEmpty() | updatedCompany.isEmpty() |
                        updatedOPrice.isEmpty() | updatedUPrice.isEmpty()) {
                    modelthis.setError("Enter Model Name");
                    companythis.setError("Enter Company Name");
                    opricethis.setError("Enter Original Price");
                    upricethis.setError("Enter Used Price");
                    return;}
                if(setimageint==0){
//                             update item for DB....
                    stackmodel updateitem = new stackmodel(
                            ids,
                            imageUri,
                            modelthis.getText().toString(),
                            companythis.getText().toString(),
                            opricethis.getText().toString(),
                            upricethis.getText().toString(),
                            false);
                    viewModel.updateItem(updateitem, stackDB);
                    adapter.notifyItemChanged(position);
//                    dialog.dismiss();
                } else {
                    stackmodel updateitem = new stackmodel(
                            ids,
                            imageUri,
                            modelthis.getText().toString(),
                            companythis.getText().toString(),
                            opricethis.getText().toString(),
                            upricethis.getText().toString(),
                            false);
                    viewModel.updateItem(updateitem, stackDB);
                    adapter.notifyItemChanged(position);
                }
                dialog.dismiss();;
                Toast.makeText(getContext(), "Item Update", Toast.LENGTH_SHORT).show();
            }
        });
    }
//     gallery se image lene ke liye function
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK ) {
            if (data == null|| data.getData()==null) {
                imageUri=cameraImageUri;}
            else{
                imageUri=data.getData();
            }
            Glide.with(requireContext()).load(imageUri).into(itemimg);
        }
    }
    //stack ke view model class ko use karne ke liye
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StackViewModel.class);
        // TODO: Use the ViewModel
    }
    private void tryAddItem(){
        if (fimg != null && fmodel != null && fcompany != null && foriginal != null && fused != null) {
            stackmodel additem = new stackmodel(fimg, fmodel, fcompany, foriginal, fused, false);
            viewModel.addItem(additem, stackDB);
            nm.notify(NOTIFICATION_ID, notificationtc);
            shareVM.cleanModel();
            shareVM.cleanBrand();
            shareVM.cleanOP();
            shareVM.cleanCP();
            shareVM.cleanImage();
        } else {
            Toast.makeText(getContext(), "Error: Missing data", Toast.LENGTH_SHORT).show();
        }
        viewModel.getScrapItems().observe(getViewLifecycleOwner(), list -> {
            adapter.updateList(list);
            recyclerview.scrollToPosition(arrstack.size() - 1);
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
