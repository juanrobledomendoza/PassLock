package com.example.passlock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passlock.data.User;

import java.util.ArrayList;
import java.util.List;

public class UserSuggestionAdapter extends RecyclerView.Adapter<UserSuggestionAdapter.ViewHolder> {

    public interface OnSuggestionToggleListener {
        void onSuggestionToggled(User user, boolean enabled);
    }

    private final List<User> users = new ArrayList<>();
    private final OnSuggestionToggleListener listener;

    public UserSuggestionAdapter(OnSuggestionToggleListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<User> newUsers) {
        users.clear();
        if (newUsers != null) {
            users.addAll(newUsers);
        }
        notifyDataSetChanged();
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.switchSuggestions.setOnCheckedChangeListener(null);
        holder.switchSuggestions.setChecked(user.isSuggestionsEnabled());

        holder.switchSuggestions.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onSuggestionToggled(user, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        Switch switchSuggestions;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.tv_username);
            switchSuggestions = itemView.findViewById(R.id.switch_suggestions_enabled);
        }
    }
}
