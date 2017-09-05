package com.stanny.demo.ui;

import android.os.Bundle;

import com.stanny.demo.R;
import com.zx.zxutils.views.MPChart.ChartKeyValue;
import com.zx.zxutils.views.MPChart.ZXBarChart;
import com.zx.zxutils.views.MPChart.ZXLineChart;
import com.zx.zxutils.views.MPChart.ZXPieChart;
import com.zx.zxutils.views.SwipeBack.ZXSwipeBackHelper;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends BaseActivity {

    private ZXLineChart linechart;
    private ZXPieChart pieChart;
    private ZXBarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXSwipeBackHelper.onCreate(this)
                .setSwipeBackEnable(true)
                .setSwipeRelateEnable(true);
        setContentView(R.layout.activity_chart);
        initLineChart();
        initPieChart();
        initBarChart();
    }

    private void initBarChart() {
        barChart = (ZXBarChart) findViewById(R.id.mp_barchart);

        List<ChartKeyValue> chartKeyValue1 = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            chartKeyValue1.add(new ChartKeyValue("第" + j + "个", (float) (Math.random() * 100)));
        }
        List<ChartKeyValue> chartKeyValue2 = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            chartKeyValue2.add(new ChartKeyValue("第" + j + "ge", (float) (Math.random() * 100)));
        }
        List<ChartKeyValue> chartKeyValue3 = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            chartKeyValue3.add(new ChartKeyValue("第" + j + "个", (float) (Math.random() * 100)));
        }

        barChart.setUnit("元")
                .addData(chartKeyValue1, "Company A")
                .addData(chartKeyValue2, "Company B")
                .addData(chartKeyValue3, "Company C")
                .addComplete(2000);
    }

    private void initPieChart() {
        pieChart = (ZXPieChart) findViewById(R.id.mp_piechart);
        List<ChartKeyValue> pieDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            pieDataList.add(new ChartKeyValue("第" + i + "个", (int) (Math.random() * 1000)));
        }
        pieChart.addData(pieDataList, "MPAndroidChart")
                .addComplete(2000);
    }

    private void initLineChart() {
        linechart = (ZXLineChart) findViewById(R.id.mp_linechart);
        List<ChartKeyValue> chartKeyValue1 = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            chartKeyValue1.add(new ChartKeyValue("第" + j + "个", (float) (Math.random() * 100)));
        }
        List<ChartKeyValue> chartKeyValue2 = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            chartKeyValue2.add(new ChartKeyValue("第" + j + "ge", (float) (Math.random() * 100)));
        }
        linechart.setUnit("¥")
                .addData(chartKeyValue1, "测试1")
                .addData(chartKeyValue2, "测试2")
                .addComplete(2000);
//        linechart.invalidate();//refresh
    }
}
