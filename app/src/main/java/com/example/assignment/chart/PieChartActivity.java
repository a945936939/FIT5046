package com.example.assignment.chart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.assignment.MainActivity;
import com.example.assignment.databinding.ActivityChartBinding;
import com.example.assignment.entity.User;
import com.example.assignment.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private ActivityChartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        List<User> userList = userViewModel.getAllUsersInList();

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PieChartActivity.this, MainActivity.class));
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PieChartActivity.this, BarChartActivity.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PieChartActivity.this, LineChartActivity.class));
            }
        });

        AnyChartView anyChartView = binding.anyChartView;
        anyChartView.setProgressBar(binding.progressBar);

        Pie pie = AnyChart.pie();

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(PieChartActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        // calculate the height of user by gender
        int maleNum = 0;
        int femaleNum = 0;

        for (User user: userList)
        {
            if (user.getHeight() >= 160)
            {
                if (user.getGender().equals("Male"))
                    maleNum++;
                else
                    femaleNum++;
            }
        }

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Male User", maleNum));
        data.add(new ValueDataEntry("Female User", femaleNum));

        pie.data(data);

        pie.title("The users who are over 165cm tall");
        pie.title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Male User and Female User")
                .fontColor("black")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

}
