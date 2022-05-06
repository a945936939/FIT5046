package com.example.assignment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.databinding.RecyclerviewItemBinding;
import com.example.assignment.entity.User;

public class UserListAdapter extends ListAdapter<User, UserViewHolder> {

    public UserListAdapter(@NonNull DiffUtil.ItemCallback<User> diffCallback){
        super(diffCallback);
    }

    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewItemBinding binding=
                RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding).onCreate(parent);
    }

    @Override
    public void onBindViewHolder(UserViewHolder viewHolder, int position){
        User user = getItem(position);
        viewHolder.bind(user.getUserId(), user.getFirstName(), user.getLastName(), user.getGender(), user.getAge(), user.getYearOfBirth(), user.getHeight());
    }

    //identify if the two users are the same
    public static class UserDiff extends DiffUtil.ItemCallback<User> {

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
