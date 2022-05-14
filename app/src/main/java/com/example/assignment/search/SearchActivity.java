package com.example.assignment.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.assignment.MainActivity;
import com.example.assignment.databinding.ActivitySearchBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private static final String API_KEY = "AIzaSyDDwdc_re42k_K9hSLI0KzdXNc3fKO2xBs";
    private static final String SEARCH_ID_cx = "e1fd7a4564394144d";
    private String keyword;
    private ActivitySearchBinding binding;
    private RetrofitInterface retrofitInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });

        retrofitInterface = RetrofitClient.getRetrofitService();
        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = binding.searchContent.getText().toString();
                Call<SearchResponse> callAsync =
                        retrofitInterface.customSearch(API_KEY,SEARCH_ID_cx, keyword);
                //makes an async request & invokes callback methods when the response returns
                callAsync.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call,
                                           Response<SearchResponse> response) {
                        if (response.isSuccessful()) {
                            List<Items> list = response.body().items;
                            //getting snippet from the object in the position 0
                            String result= list.get(0).getSnippet();
                            binding.searchResult.setText(result);
                        }
                        else {
                            Log.i("Error ","Response failed");
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t){
                        Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
