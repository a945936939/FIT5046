package com.example.assignment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.assignment.entity.Student;

public class StudentListAdapter extends ListAdapter<Student, StudentViewHolder> {

    public StudentListAdapter(@NonNull DiffUtil.ItemCallback<Student> diffCallback){
        super(diffCallback);
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StudentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position){
        Student student = getItem(position);
        holder.bind(student.getStudentId(), student.getFirstName(), student.getLastName());
    }

    //identify if the two students are the same
    static class StudentDiff extends DiffUtil.ItemCallback<Student> {

        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem){
            return oldItem.getStudentId().equals(newItem.getStudentId());
        }
    }






}
