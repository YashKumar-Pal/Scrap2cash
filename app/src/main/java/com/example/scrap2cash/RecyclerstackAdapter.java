package com.example.scrap2cash;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        this.listener=listener;
    }
    @Override
    public RecyclerstackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stack_row, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerstackAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        stackmodel model = (stackmodel) arrstack.get(position);
//        iski wajah se add new item main image aa rhi h
        if (model.imageUri != null) {
            Glide.with(context).load(model.imageUri).into(holder.deviceimg);
        } else {
            holder.deviceimg.setImageResource(model.img);
        }
//        sare item ko bind karta h
        holder.modelname.setText(model.model);
        holder.companyname.setText(model.company);
        holder.originalprice.setText(model.originalprice);
        holder.usedprice.setText(model.usedprice);
        holder.selllater.setChecked(model.selllater);
        setAnimation(holder.itemView, position);
        holder.mainll.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(arrstack.get(position) , position);
                Log.d("RecyclerClick", "Item clicked: " + position); // Debug log

            }
        });
//      delete function call karna
        holder.mainll.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
            showDeleteDialog(position);
             return true;
          }
        });
    }
//    count item function
    @Override
    public int getItemCount() {
        return arrstack != null ? arrstack.size() : 0;
    }
//recycler view main se item delete karne ke liye
   private void showDeleteDialog(int position) {
        stackmodel model = arrstack.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
          .setTitle("Delete Item")  .setMessage("Are you want to delete?") .setIcon(R.drawable.icons8_delete_30)
          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrstack.remove(position);
                        notifyItemRemoved(position);}
          })
          .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
          });
        builder.show();
   }
//    variable declaration class
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView deviceimg;
        TextView modelname;
        TextView companyname;
        TextView originalprice;
        TextView usedprice;
        CheckBox selllater;
        LinearLayout mainll;
//        bind karne ke liye variable declaration
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
//    to set animation
    private void setAnimation(View v1, int position) {
        Animation slideIn = AnimationUtils.loadAnimation(context, R.anim.alpha);
        if (position > lastposi) {
//        Animation slideIn= AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
//        Animation slideIn= AnimationUtils.loadAnimation(context,R.anim.alpha1);
            v1.startAnimation(slideIn);
            lastposi = position;
        }
    }
}
