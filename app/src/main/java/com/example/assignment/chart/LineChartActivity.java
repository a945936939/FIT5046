package com.example.assignment.chart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.assignment.MainActivity;
import com.example.assignment.databinding.ActivityChartBinding;
import com.example.assignment.entity.User;
import com.example.assignment.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class LineChartActivity extends AppCompatActivity {

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
                startActivity(new Intent(LineChartActivity.this, MainActivity.class));
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LineChartActivity.this, PieChartActivity.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LineChartActivity.this, BarChartActivity.class));
            }
        });

        AnyChartView anyChartView = binding.anyChartView;
        anyChartView.setProgressBar(binding.progressBar);

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Trend of average user height from 1990-2010.");
        cartesian.title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");

        cartesian.yAxis(0).title("The height of users");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        cartesian.xAxis(0).title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");
        cartesian.yAxis(0).title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");

        List<DataEntry> seriesData = new ArrayList<>();
            //{1998: [170, 175]}
        HashMap<String, ArrayList<Double>> userMap = new HashMap<>();

        int maleSum = 0; int maleCount = 0;
        int femaleNum = 0; int femaleCount = 0;

        for (int i = 1990; i <= 2005; i++)
        {
            String finalI = String.valueOf(i);
            double maleAvgHeight = userList.stream().filter(s -> s.getYearOfBirth().equals(finalI)
                    && s.getGender().equals("Male")).mapToDouble(User::getHeight).average().orElse(0);

            double femaleAvgHeight = userList.stream().filter(s -> s.getYearOfBirth().equals(finalI)
                    && s.getGender().equals("Female")).mapToDouble(User::getHeight).average().orElse(0);

            ArrayList<Double> doubles = new ArrayList<>();
            doubles.add(maleAvgHeight);
            doubles.add(femaleAvgHeight);

            userMap.put(finalI,doubles);
        }

        TreeMap<String, ArrayList<Double>> sortedMap = new TreeMap<>();
        sortedMap.putAll(userMap);
        sortedMap.forEach((key, value) -> seriesData.add(new CustomDataEntry(key, value.get(0), value.get(1))));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value1' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Male user");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Female user");
        series2.stroke("red");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

//        Line series3 = cartesian.line(series3Mapping);
//            series3.name("Tequila");
//            series3.hovered().markers().enabled(true);
//            series3.hovered().markers()
//                    .type(MarkerType.CIRCLE)
//                    .size(4d);
//            series3.tooltip()
//                    .position("right")
//                    .anchor(Anchor.LEFT_CENTER)
//                    .offsetX(5d)
//                    .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }
}
