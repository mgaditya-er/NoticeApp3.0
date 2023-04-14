package com.example.noteiceboard;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;



public class ListBatchAdapter2 extends RecyclerView.Adapter<ListBatchAdapter2.ViewHolder> {

    private Context context;
    private List<String> batchList;

    public ListBatchAdapter2(Context context, List<String> batchList) {
        this.context = context;
        this.batchList = batchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_batch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String batch = batchList.get(position);
//        holder.tvName.setText(batch.getName());
//        holder.tvCode.setText(String.valueOf(batch.getCode()));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Batch is clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



    @Override
    public int getItemCount() {
        return batchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_batch_name);
            tvCode = itemView.findViewById(R.id.tv_batch_code);
        }
    }
}
