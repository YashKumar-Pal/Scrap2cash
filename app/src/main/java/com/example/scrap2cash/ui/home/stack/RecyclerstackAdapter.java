package com.example.scrap2cash.ui.home.stack;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.scrap2cash.R;
import com.example.scrap2cash.ui.home.StackDB;

import java.util.ArrayList;

public class RecyclerstackAdapter extends RecyclerView.Adapter<RecyclerstackAdapter.ViewHolder> {
    Context context;
    ArrayList<stackmodel> arrstack;
    int lastposi = -1;

    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(stackmodel model, int position);
    }

    final private OnItemClickListener listener;

    public RecyclerstackAdapter(Context context, ArrayList<stackmodel> arrstack, OnItemClickListener listener) {
        this.context = context;
        this.arrstack = arrstack;
        this.listener = listener;
    }

    @Override
    public RecyclerstackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stack_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerstackAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        stackmodel model = arrstack.get(position);

        // Image bind
        if (model.imageUri != null) {
            Glide.with(context).load(model.imageUri).into(holder.deviceimg);
        } else {
            holder.deviceimg.setImageResource(model.img);
        }

        // Text bind
        holder.modelname.setText(model.model);
        holder.companyname.setText(model.company);
        holder.originalprice.setText(model.originalprice);
        holder.usedprice.setText(model.usedprice);
        holder.selllater.setChecked(model.selllater);

        setAnimation(holder.itemView, position);

        // Normal click
        holder.mainll.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(arrstack.get(position), position);
                Log.d("RecyclerClick", "Item clicked: " + position);
            }
        });

        // Long click → Delete dialog
        holder.mainll.setOnLongClickListener(v -> {
            showDeleteDialog(position);
            return true;
        });
    }

    private void showDeleteDialog(int position) {
        stackmodel model = arrstack.get(position);

        new AlertDialog.Builder(context)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.icons8_delete_30)
                .setPositiveButton("Yes", (dialog, which) -> {
                    // 1. DB se delete
                    StackDB dbHelper = new StackDB(context);
                    dbHelper.deleteItem(model.getId()); // Database se delete

                    // 2. List se delete
                    arrstack.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrstack.size()); // RecyclerView update
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
        Log.d("DeleteCheck", "Deleting ID: " + model.getId());

    }

    @Override
    public int getItemCount() {
        return arrstack != null ? arrstack.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView deviceimg;
        TextView modelname, companyname, originalprice, usedprice;
        CheckBox selllater;
        LinearLayout mainll;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            deviceimg = itemview.findViewById(R.id.mainimg);
            modelname = itemview.findViewById(R.id.modelName2);
            companyname = itemview.findViewById(R.id.companyName2);
            originalprice = itemview.findViewById(R.id.originalPrice2);
            usedprice = itemview.findViewById(R.id.usedPrice2);
            selllater = itemview.findViewById(R.id.sellLater);
            mainll = itemview.findViewById(R.id.acardmainll);
        }
    }

    private void setAnimation(View v1, int position) {
        Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.alpha);
        if (position > lastposi) {
            v1.startAnimation(slideIn);
            lastposi = position;
        }
    }
    public void updateList(ArrayList<stackmodel> newList) {
        this.arrstack = newList;
        notifyDataSetChanged();
    }
}
