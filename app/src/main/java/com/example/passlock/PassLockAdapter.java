package com.example.passlock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.PassLock;

import java.util.List;

public class PassLockAdapter extends RecyclerView.Adapter<PassLockAdapter.PassLockViewHolder> {

    private List<PassLock> passLockList;

    public PassLockAdapter(List<PassLock> passLockList) {
        this.passLockList = passLockList;
    }

    @NonNull
    @Override
    public PassLockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passlock_item, parent, false);
        return new PassLockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassLockViewHolder holder, int position) {
        PassLock passLock = passLockList.get(position);
        holder.serviceNameTextView.setText(passLock.getServiceName());
        holder.usernameTextView.setText(passLock.getUsername());

        // Check if the PassLock has been scored.
        if (passLock.getScore() != -1) {
            holder.scoreTextView.setText("Score: " + passLock.getScore());
            holder.feedbackTextView.setText("Feedback: " + passLock.getFeedback());
            holder.scoreTextView.setVisibility(View.VISIBLE);
            holder.feedbackTextView.setVisibility(View.VISIBLE);
        } else {
            holder.scoreTextView.setVisibility(View.GONE);
            holder.feedbackTextView.setVisibility(View.GONE);
        }

        // Set an OnClickListener for the feedback button.
        holder.feedbackButton.setOnClickListener(v -> {
            Context context = v.getContext();
            // Use the intentFactory to start the test activity, now with the PassLock ID.
            Intent intent = PasswordTestActivity.intentFactory(context, passLock.getPassword(), passLock.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return passLockList.size();
    }

    static class PassLockViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView;
        TextView usernameTextView;
        TextView scoreTextView;
        TextView feedbackTextView;
        Button feedbackButton;

        public PassLockViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.textViewServiceName);
            usernameTextView = itemView.findViewById(R.id.textViewUsername);
            scoreTextView = itemView.findViewById(R.id.textViewScore);
            feedbackTextView = itemView.findViewById(R.id.textViewFeedback);
            feedbackButton = itemView.findViewById(R.id.buttonFeedback);
        }
    }
}
