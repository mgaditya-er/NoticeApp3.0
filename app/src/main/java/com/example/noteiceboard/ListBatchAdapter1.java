package com.example.noteiceboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListBatchAdapter1 extends RecyclerView.Adapter<ListBatchAdapter1.ViewHolder> {

    private List<String> emailList;

    public ListBatchAdapter1(List<String> emailList) {
        this.emailList = emailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String email = emailList.get(position);
        holder.emailTextView.setText(email);
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView emailTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.email_text_view);
        }
    }
}
