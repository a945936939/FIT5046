package com.example.assignment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment.databinding.FragmentWalkActivityBinding;
import com.example.assignment.viewModel.SharedViewModel;

public class WalkActivityFragment extends Fragment {

    private FragmentWalkActivityBinding binding;
    public WalkActivityFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = FragmentWalkActivityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}