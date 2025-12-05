package com.example.passlock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        // Set an OnClickListener for the button.
        // For now, it will just show a toast message.
        holder.feedbackButton.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Feedback for " + passLock.getServiceName() + " coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return passLockList.size();
    }

    static class PassLockViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView;
        TextView usernameTextView;
        Button feedbackButton;

        public PassLockViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.textViewServiceName);
            usernameTextView = itemView.findViewById(R.id.textViewUsername);
            feedbackButton = itemView.findViewById(R.id.buttonFeedback);
        }
    }
}
