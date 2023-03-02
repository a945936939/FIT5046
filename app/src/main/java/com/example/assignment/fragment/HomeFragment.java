package com.example.assignment.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.assignment.MainActivity;
import com.example.assignment.Root;
import com.example.assignment.WeatherApiInterface;
import com.example.assignment.databinding.HomeFragmentBinding;
import com.example.assignment.viewModel.SharedViewModel;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private SharedViewModel model;
    private HomeFragmentBinding binding;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareButton shareButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.facebook.com/profile.php?id=100081323015982&sk=about"))
                .build();

        binding.heartRateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.getFragment(new HeartRateFragment());
            }
        });

        binding.walkActivityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.getFragment(new WalkActivityFragment());
            }
        });

        binding.kcalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.getFragment(new KcalFragment());
            }
        });


        // https://api.openweathermap.org/data/2.5/weather?lat=-37.813629&lon=144.963058&appid=80273ca2896861a72eca02c8f231e796
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiInterface weatherApiInterface = retrofit.create(WeatherApiInterface.class);

        Call<Root> call = weatherApiInterface.getWeather();

        call.enqueue(new Callback<Root>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                double temp = root.getTemp() - 273.15;
                String format = new DecimalFormat("#.0").format(temp);
                int pressure = root.getPressure();
                int humidity = root.getHumidity();
                double windSpeed = root.getWindSpeed();
                String icon = root.getIcon();
                binding.tempTextView.setText("Clayton:  " + format + "°C");
                binding.pressureTextView.setText("Pressure:  " + pressure + " hPa");
                binding.humidityTextView.setText("Humidity:  " + humidity + " %rh");
                binding.windSpeedTextView.setText("Wind Speed:  " + windSpeed + " m/s");
                String url = "https://openweathermap.org/img/w/" + icon + ".png";
                Picasso.get().load(url).into(binding.imageView);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        shareButton = binding.shareFacebook;
        shareButton.setShareContent(content);
        Log.d("button",shareButton.getClass().toString());
//        ShareDialog.show(this, content);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
