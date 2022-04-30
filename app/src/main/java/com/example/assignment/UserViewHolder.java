package com.example.assignment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.databinding.RecyclerviewItemBinding;
import com.example.assignment.entity.User;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public RecyclerviewItemBinding binding;

    public UserViewHolder(RecyclerviewItemBinding binding){
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String userId, String firstName, String lastName){
        binding.textView.setText(userId + ": " + firstName + " " + lastName);
    }
    public void delete(User user){

    }

    public UserViewHolder onCreate(@NonNull ViewGroup parent){
        binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

}
