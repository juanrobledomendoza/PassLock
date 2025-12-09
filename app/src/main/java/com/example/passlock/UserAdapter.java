package com.example.passlock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface OnUserActionListener {
        void onDeleteUser(User user);
    }

    private final List<User> allUsers = new ArrayList<>();
    private final List<User> visibleUsers = new ArrayList<>();
    private final OnUserActionListener listener;

    public UserAdapter(OnUserActionListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<User> newUsers) {
        allUsers.clear();
        visibleUsers.clear();
        if (newUsers != null) {
            allUsers.addAll(newUsers);
            visibleUsers.addAll(newUsers);
        }
        notifyDataSetChanged();
    }

    public void filter(String query) {
        visibleUsers.clear();
        if (query == null || query.trim().isEmpty()) {
            visibleUsers.addAll(allUsers);
        } else {
            String lower = query.toLowerCase();
            for (User u : allUsers) {
                if (u.getUsername() != null && u.getUsername().toLowerCase().contains(lower)) {
                    visibleUsers.add(u);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_hacker, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = visibleUsers.get(position);
        holder.usernameTextView.setText(user.getUsername());

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onDeleteUser(user);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return visibleUsers.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.tv_username_hacker);
        }
    }
}
