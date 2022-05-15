package com.example.assignment.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment.databinding.FragmentSquatupBinding;

public class SquatupFragment extends Fragment {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private FragmentSquatupBinding binding;
    private double accelerationCurrentValue;
    private double accelerationPreviousValue;
    private int counter = -1;

    private double change;


    public SquatupFragment() {
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.e("Squatup fragment", "2");
        // Inflate the View for this fragment using the binding
        binding = FragmentSquatupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.progressBar.setMax(100);//sets the maximum value 100
        binding.startCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.textCount.setVisibility(View.VISIBLE);
                binding.counter.setVisibility(View.VISIBLE);
                binding.startCountButton.setVisibility(View.GONE);
                binding.shake.setVisibility(View.GONE);
            }


        });
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        return view;


    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelerationCurrentValue = Math.sqrt(x * x + y * y + z * z);
            change = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;
            if (change >= 3) {
                counter += 1;
            }
            binding.counter.setText("" + counter);
            binding.progressBar.setProgress(counter);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
}