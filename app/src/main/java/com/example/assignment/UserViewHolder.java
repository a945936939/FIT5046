package com.example.assignment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.databinding.RecyclerviewItemBinding;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public static RecyclerviewItemBinding binding;

    public UserViewHolder(RecyclerviewItemBinding binding){
        super(binding.getRoot());
        UserViewHolder.binding = binding;
    }

    public void bind(String studentId, String firstName, String lastName){
        binding.textView.setText(studentId + ": " + firstName + " " + lastName);
    }

    public static UserViewHolder onCreate(@NonNull ViewGroup parent){
        binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

}
