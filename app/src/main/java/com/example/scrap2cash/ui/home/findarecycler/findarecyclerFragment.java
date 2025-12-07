package com.example.scrap2cash.ui.home.findarecycler;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.scrap2cash.R;
import com.example.scrap2cash.databinding.FragmentFindarecyclerBinding;

public class findarecyclerFragment extends Fragment {

    private FragmentFindarecyclerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // ViewModel (agar aap use kar rahe ho toh theek hai)
        findarecyclerViewModel recyclerViewModel =
                new ViewModelProvider(this).get(findarecyclerViewModel.class);

        // Binding inflate
        binding = FragmentFindarecyclerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//from this to : bhavesh
        // LinearLayouts IDs find karna
        LinearLayout linear1 = root.findViewById(R.id.linear1);
        LinearLayout linear2 = root.findViewById(R.id.linear2);
        LinearLayout linear3 = root.findViewById(R.id.linear3);
        LinearLayout linear4 = root.findViewById(R.id.linear4);
        LinearLayout linear5 = root.findViewById(R.id.linear5);
        LinearLayout linear6 = root.findViewById(R.id.linear6);
        LinearLayout linear7 = root.findViewById(R.id.linear7);

        // Click listeners
        linear1.setOnClickListener(v -> showdialogformap("28.707373,77.275528")); //bharat recycling pvt ltd
        linear2.setOnClickListener(v -> showdialogformap("19.0760,72.8777")); // Mumbai
        linear3.setOnClickListener(v -> showdialogformap("12.9716,77.5946")); // Bangalore
        linear4.setOnClickListener(v -> openMap("28.552006,77.250454")); // New India network
        linear5.setOnClickListener(v -> openMap("28.524730,77.279234")); // E-Waste recyclers India
        linear6.setOnClickListener(v -> openMap("26.9124,75.7873")); // Jaipur
        linear7.setOnClickListener(v -> openMap("28.637012,77.292287")); // E- Waste recycling in India

        return root;
    }
    private void showdialogformap(String destination) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogformap, null);

        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogView);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();

        // No -> dismiss only
        btnNo.setOnClickListener(v -> dialog.dismiss());

        // Yes -> wait 3 sec then open map
        btnYes.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                openMap(destination);
                dialog.dismiss();
            }, 1000);
        });
    }
    private void openMap(String destination) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            String url = "https://www.google.com/maps/dir/?api=1&destination=" + destination;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
    }
//    till this: bhavesh

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}