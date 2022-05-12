package com.example.assignment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment.databinding.FragmentHeartRateBinding;
import com.example.assignment.viewModel.SharedViewModel;

public class HeartRateFragment extends Fragment {
    private FragmentHeartRateBinding binding;
    public HeartRateFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHeartRateBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        SharedViewModel model = new
                ViewModelProvider(getActivity()).get(SharedViewModel.class);


        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editText.getText().toString();
                if (!message.isEmpty() ) {
                    model.setMessage(message);
                }
            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editText.setText("");
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}