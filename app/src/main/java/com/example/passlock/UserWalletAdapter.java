package com.example.passlock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserWalletAdapter extends RecyclerView.Adapter<UserWalletAdapter.ViewHolder> {

    public static class UserWalletSummary {
        public final String username;
        public final double averageScore;
        public final String grade;

        public UserWalletSummary(String username, double averageScore, String grade) {
            this.username = username;
            this.averageScore = averageScore;
            this.grade = grade;
        }
    }

    private final List<UserWalletSummary> items = new ArrayList<>();

    public void setItems(List<UserWalletSummary> summaries) {
        items.clear();
        if (summaries != null) {
            items.addAll(summaries);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_wallet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserWalletSummary summary = items.get(position);
        holder.usernameTextView.setText(summary.username);
        holder.scoreTextView.setText("Average score: " + String.format("%.1f", summary.averageScore));
        holder.gradeTextView.setText("Grade: " + summary.grade);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView scoreTextView;
        TextView gradeTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.tv_wallet_username);
            scoreTextView = itemView.findViewById(R.id.tv_wallet_score);
            gradeTextView = itemView.findViewById(R.id.tv_wallet_grade);
        }
    }
}
