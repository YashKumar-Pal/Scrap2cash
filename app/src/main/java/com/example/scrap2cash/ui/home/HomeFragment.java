package com.example.scrap2cash.ui.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentHomeBinding;
import com.example.scrap2cash.ui.historyhome.historyhome;
import com.example.scrap2cash.ui.stack.stackfragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    Spinner ptspinner;
    Spinner Bspinner;
    ArrayList<String> arrproducttyp= new ArrayList<>();
    ArrayList<String> arrb=new ArrayList<>();
    BottomNavigationView btmnv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


//       View view=inflater.inflate(R.layout.fragment_home, container, false);

        ptspinner = binding.producttypeSpinner;
        Bspinner= binding.brandtypeSpinner;
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
////                Toast.makeText(requireContext(), "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
//
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Optional
            }
        });
//
//
//        Bspinner=view.findViewById(R.id.brandtype_spinner);
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

        binding.stackimg.setOnClickListener(v -> openstackFragment());
        binding.historyimg.setOnClickListener(v -> openHistoryFragment());
        binding.homeimg.setOnClickListener(v-> openHomeFragment());
         return root;

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

    private void showPricePopup(String product, String brand, int price) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.result_popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(popupView);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView title = popupView.findViewById(R.id.result_title);
        TextView message = popupView.findViewById(R.id.result_message);
//        Button closeBtn = popupView.findViewById(R.id.close_button);

        title.setText("Predicted Price");
        message.setText("Product: " + product + "\nBrand: " + brand + "\nEstimated Price: ₹" + price);
//        closeBtn.setOnClickListener(v -> dialog.dismiss());
    }



}