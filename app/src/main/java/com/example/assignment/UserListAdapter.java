package com.example.assignment;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.assignment.entity.User;
import com.example.assignment.viewModel.UserViewModel;

public class UserListAdapter extends ListAdapter<User, UserViewHolder> {
    private UserViewModel userViewModel;

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback){
        super(diffCallback);
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserViewHolder.onCreate(parent);
    }

    @Override
    public void onBindViewHolder(UserViewHolder viewHolder, int position){
        User user = getItem(position);
        viewHolder.bind(user.getUserId(), user.getFirstName(), user.getLastName());
        UserViewHolder.binding.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.delete(user);
                notifyDataSetChanged();
            }
        });
    }

    //identify if the two users are the same
    static class UserDiff extends DiffUtil.ItemCallback<User> {

        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem){
            return oldItem.getUserId().equals(newItem.getUserId());
        }
    }
}
