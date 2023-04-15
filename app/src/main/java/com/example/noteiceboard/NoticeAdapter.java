package com.example.noteiceboard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;




public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private List<Notice> notices;

    public NoticeAdapter(List<Notice> notices) {
        this.notices = notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_batch, parent, false);
        return new NoticeViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        Notice notice = notices.get(position);
        holder.tvName.setText(notice.getTitle());
        holder.tvCode.setText(notice.getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(), "notice clicked " + notice.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });

    }





    @Override
    public int getItemCount() {
        return notices.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvCode;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_batch_name);
            tvCode = itemView.findViewById(R.id.tv_batch_code);

        }
    }
}

