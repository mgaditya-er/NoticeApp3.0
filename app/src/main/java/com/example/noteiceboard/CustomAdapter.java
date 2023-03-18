package com.example.noteiceboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList batch_id, batch_name, batch_code;

    CustomAdapter(Activity activity, Context context, ArrayList batch_id, ArrayList batch_name, ArrayList batch_code
    ){
        this.activity = activity;
        this.context = context;
        this.batch_id = batch_id;
        this.batch_name = batch_name;
        this.batch_code = batch_code;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.batch_id_txt.setText(String.valueOf(batch_id.get(position)));
        holder.batch_name_txt.setText(String.valueOf(batch_name.get(position)));
        holder.batch_code_txt.setText(String.valueOf(batch_code.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BatchHome.class);
                intent.putExtra("id", String.valueOf(batch_id.get(position)));
                intent.putExtra("name", String.valueOf(batch_name.get(position)));
                intent.putExtra("code", String.valueOf(batch_code.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return batch_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView batch_id_txt, batch_name_txt, batch_code_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            batch_id_txt = itemView.findViewById(R.id.book_id_txt);
            batch_name_txt = itemView.findViewById(R.id.book_title_txt);
            batch_code_txt = itemView.findViewById(R.id.book_pages_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}