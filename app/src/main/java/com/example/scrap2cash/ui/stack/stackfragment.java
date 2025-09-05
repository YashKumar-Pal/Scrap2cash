package com.example.scrap2cash.ui.stack;

import static android.content.Context.NOTIFICATION_SERVICE;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.scrap2cash.R;
import com.example.scrap2cash.RecyclerstackAdapter;
import com.example.scrap2cash.databinding.FragmentStackBinding;
import com.example.scrap2cash.stackmodel;
import com.example.scrap2cash.ui.historyhome.historyhome;
import com.example.scrap2cash.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class stackfragment extends Fragment {
    private static final String CHHANEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 100;
    private StackViewModel mViewModel;
    private FragmentStackBinding binding;
    RecyclerView recyclerview;
    FloatingActionButton fbtn;
    ArrayList<stackmodel> arrstack;
    private RecyclerstackAdapter adapter;
    private static final int IMAGE_PICK_CODE = 1001;
    private static final int IMAGE_PICK_CODE_ADD = 1001; // For adding new item
    private static final int IMAGE_PICK_CODE_UPDATE = 1002; // For updating item
    Uri imageUri;
    ImageView itemimg;
    Uri setimageUri;
    int setimageint;
//
    @FunctionalInterface
    public interface ImagePickCallback {
        void onRequestImagePick(ImageView targetImageView, int position);
    }
    //    main function
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        recycler view grap hua
        recyclerview = binding.mainrecyclerview;
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        arrstack = new ArrayList<>();
//        sare element ko value mil rhi h runtime se pahle
        arrstack.add(new stackmodel(R.drawable.pavilion, "HP Pavilion", "HP", "55,000", "32,000", true));
        arrstack.add(new stackmodel(R.drawable.thinkpad, "ThinkPad", "Lenovo", "65,000", "45,000", true));
        arrstack.add(new stackmodel(R.drawable.samsungtab, "Tab S6", "Samsung", "45,000", "25,000", false));
        arrstack.add(new stackmodel(R.drawable.canon, "EOS 1500D", "Canon", "38,000", "20,000", false));
//        recycler view main item bind hone ke liye recycler adapter class call ho rhi h
        adapter = new RecyclerstackAdapter(getContext(), arrstack, (model, position) -> {
//            gallery se image pick ke lye function call ho rha h
            showUpdateDialog(model, position);
        });
//        recycler view main element ko content de rha h
        recyclerview.setAdapter(adapter);
//        new item add button
        fbtn = binding.fbtn;
//        notification permission lene ke liye agar nhi mili ho to
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (requireContext().checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
//             Stop here until permission is granted
            }
        }
//            drawable se notification main image set karne ke liye
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.info_icon2, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap setLargeIcon2 = bitmapDrawable.getBitmap();
        NotificationManager nm = (NotificationManager) requireContext().getSystemService(NOTIFICATION_SERVICE);
        Notification notificationtc;
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
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, IMAGE_PICK_CODE);
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
                                arrstack.add(new stackmodel(
                                        imageUri,
                                        modelname.getText().toString(),
                                        companyname.getText().toString(),
                                        originalprice.getText().toString(),
                                        usedprice.getText().toString(),
                                        false
                                ));
                                adapter.notifyItemInserted(arrstack.size() - 1);
                                recyclerview.scrollToPosition(arrstack.size() - 1);
//                                Toast.makeText(requireContext(), "Item Added", Toast.LENGTH_SHORT).show();
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
        });
//        binding.stackimg.setOnClickListener(v -> openstackFragment());
        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
        binding.homeimg.setOnClickListener(v -> openHomeFragment());
        return root;
    }
//     gallery se image lene ke liye function
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null && itemimg != null) {
                Glide.with(requireContext()).load(imageUri).into(itemimg);
            }
        }
    }
    //stack ke view model class ko use karne ke liye
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StackViewModel.class);
        // TODO: Use the ViewModel
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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });
// recycler view main dialog me button se update item karne ke liye
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                             arrstack.get(position).imageUri = imageUri;
                             arrstack.get(position).model = updatedModel;
                             arrstack.get(position).company = updatedCompany;
                             arrstack.get(position).originalprice = updatedOPrice;
                             arrstack.get(position).usedprice = updatedUPrice;
                             adapter.notifyItemChanged(position);
                             dialog.dismiss();
                         } else {
                             arrstack.get(position).img=setimageint;
                             arrstack.get(position).model = updatedModel;
                             arrstack.get(position).company = updatedCompany;
                             arrstack.get(position).originalprice = updatedOPrice;
                             arrstack.get(position).usedprice = updatedUPrice;
                         }
                        adapter.notifyItemChanged(position);
                        dialog.dismiss();;
                Toast.makeText(getContext(), "Item Update", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
