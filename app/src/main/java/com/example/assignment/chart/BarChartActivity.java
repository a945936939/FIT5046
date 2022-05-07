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
import com.anychart.core.axes.Linear;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.Column;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LabelsOverlapMode;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.assignment.MainActivity;
import com.example.assignment.databinding.ActivityChartBinding;
import com.example.assignment.entity.User;
import com.example.assignment.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

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
                startActivity(new Intent(BarChartActivity.this, MainActivity.class));
            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BarChartActivity.this, LineChartActivity.class));
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BarChartActivity.this, PieChartActivity.class));
            }
        });

        AnyChartView anyChartView = binding.anyChartView;
        anyChartView.setProgressBar(binding.progressBar);

        Cartesian cartesian = AnyChart.column();

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

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("The users who are over 165cm tall");
        cartesian.title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Gender");
        cartesian.yAxis(0).title("Height");
        cartesian.xAxis(0).title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");
        cartesian.yAxis(0).title().fontSize("20").fontStyle("bold").fontColor("black").fontFamily("sans");

        anyChartView.setChart(cartesian);
    }

}
