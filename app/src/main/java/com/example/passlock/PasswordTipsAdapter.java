package com.example.passlock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PasswordTipsAdapter extends RecyclerView.Adapter<PasswordTipsAdapter.TipViewHolder> {

    private final String[] tips;

    public PasswordTipsAdapter(String[] tips) {
        this.tips = tips;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        holder.textView.setText(tips[position]);
    }

    @Override
    public int getItemCount() {
        return tips.length;
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
