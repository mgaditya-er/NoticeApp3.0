package com.example.noteiceboard;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteiceboard.databinding.ActivityAddBinding;

import java.util.List;




public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.BatchViewHolder> {

    private List<Batch> batches;

    public BatchAdapter(List<Batch> batches) {
        this.batches = batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_batch, parent, false);
        return new BatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchViewHolder holder, int position) {
        Batch batch = batches.get(position);
        holder.tvName.setText(batch.getName());
        holder.tvCode.setText(String.valueOf(batch.getCode()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BatchDetailsActivity.class);
                intent.putExtra("batchname", batch.getName()); // Pass the batch id to the new activity
                v.getContext().startActivity(intent);
            }
        });

    }





    @Override
    public int getItemCount() {
        return batches.size();
    }

    public static class BatchViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvCode;

        public BatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_batch_name);
            tvCode = itemView.findViewById(R.id.tv_batch_code);
        }
    }
}
