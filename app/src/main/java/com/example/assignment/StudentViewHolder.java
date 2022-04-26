package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    private final TextView studentItemView;

    private StudentViewHolder(View itemView){
        super(itemView);
        studentItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String studentId, String firstName, String lastName){
        studentItemView.setText(studentId + ":" + firstName + " " + lastName);
    }

    static StudentViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent,false);
        return new StudentViewHolder(view);
    }

}
