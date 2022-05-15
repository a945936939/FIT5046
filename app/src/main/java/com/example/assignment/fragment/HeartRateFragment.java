package com.example.assignment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.assignment.databinding.FragmentHeartRateBinding;

public class HeartRateFragment extends Fragment {

    private FragmentHeartRateBinding binding;
    public HeartRateFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHeartRateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}