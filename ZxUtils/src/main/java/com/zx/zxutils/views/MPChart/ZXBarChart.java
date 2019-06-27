package com.zx.zxutils.views.MPChart;

import android.content.Context;
import android.util.AttributeSet;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.views.MPChart.charts.BarChart;
import com.zx.zxutils.views.MPChart.components.AxisBase;
import com.zx.zxutils.views.MPChart.components.Description;
import com.zx.zxutils.views.MPChart.components.Legend;
import com.zx.zxutils.views.MPChart.components.XAxis;
import com.zx.zxutils.views.MPChart.data.BarData;
import com.zx.zxutils.views.MPChart.data.BarDataSet;
import com.zx.zxutils.views.MPChart.data.BarEntry;
import com.zx.zxutils.views.MPChart.formatter.IAxisValueFormatter;
import com.zx.zxutils.views.MPChart.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/6.
 * 功能：重写柱状图
 */
public class ZXBarChart extends BarChart {
    private Context context;
    private String unit = "";
    //应在X轴绘制的标签
    private List<String> labels = new ArrayList<>();
    private List<IBarDataSet> tempDataSets = new ArrayList<>();

    public ZXBarChart(Context context) {
        super(context, null);
    }

    public ZXBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBarChart(context);
    }

    private void initBarChart(Context context) {
        this.context = context;
        //设置标记
        Description description = new Description();
//        description.setText("重庆知行");
        description.setText("");
        description.setTextColor(ZXSystemUtil.transColor( R.color.lightgray));
        description.setYOffset(10);
        setDescription(description);

        //属性设置
        setNoDataText("暂未获取到数据");
        setNoDataTextColor(ZXSystemUtil.transColor( R.color.deepskyblue));
        setAutoScaleMinMaxEnabled(true);
        setScaleYEnabled(false);//禁止Y轴滑动
        getAxisRight().setDrawGridLines(false);//隐藏右边坐标轴横向网格线
        getXAxis().setDrawGridLines(false);//隐藏X轴的竖向网格线
        getAxisRight().setDrawLabels(false);//隐藏右边的坐标
        getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴label的位置
        getAxisRight().setDrawAxisLine(false);//隐藏右边坐标轴
        setDrawBorders(false);//设置是否加边框
        getXAxis().setGranularity(1f);


        getXAxis().setCenterAxisLabels(false);
        getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int) value) + unit;
            }
        });
        getAxisLeft().setGridColor(ZXSystemUtil.transColor(R.color.gray_de));//横向背景色
        //图例设置
//        getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//图例的横向位置
//        getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例的纵向位置
//        getLegend().setDrawInside(true);
        getLegend().setForm(Legend.LegendForm.CIRCLE);//图例的样式
        getLegend().setWordWrapEnabled(true);
//        getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例纵向排列
    }

    /**
     * 添加data
     *
     * @param pieDataList
     * @param legendName
     */
    public ZXBarChart addData(List<ChartKeyValue> pieDataList, String legendName) {
        List<BarEntry> entrys = new ArrayList<>();
        if (pieDataList.size() > labels.size()) {
            labels.clear();
            for (int i = 0; i < pieDataList.size(); i++) {//添加label
                labels.add(pieDataList.get(i).getKey());
            }
        }
        for (int i = 0; i < pieDataList.size(); i++) {
            entrys.add(new BarEntry(i, pieDataList.get(i).getValue()));
        }
        BarDataSet dataSet = new BarDataSet(entrys, legendName);
        tempDataSets.add(dataSet);
        return this;
    }

    public void clear() {
        labels.clear();
        tempDataSets.clear();
        addComplete(1);
    }

    public ZXBarChart addComplete(int animMillis) {
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.addAll(tempDataSets);
        BarData lineData = new BarData(dataSets);

        XAxis xAxis = getXAxis();
        xAxis.setTextSize(7);
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try {
                    return labels.get((int) value);
                } catch (Exception e) {
                    e.printStackTrace();
                    return value + "";
                }
            }
        };
        xAxis.setValueFormatter(formatter);
        //重设颜色
        int entryCount = 0;
        for (int i = 0; i < dataSets.size(); i++) {
            BarDataSet dataSet = (BarDataSet) dataSets.get(i);
            entryCount = entryCount > dataSet.getEntryCount() ? entryCount : dataSet.getEntryCount();
            dataSet.setColor(ZXSystemUtil.transColor( ChartColor.getColor(i)));//颜色
            dataSet.setHighLightColor(ZXSystemUtil.transColor( R.color.halfTransparent));//高亮线颜色
        }
        super.setData(lineData);
        float groupSpace = 0.15f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = (1 - groupSpace) / lineData.getDataSets().size() - barSpace; // x4 DataSet
        // (barWidth + barSpace) * dataset.size + groupSpace = 1.00 -> interval per "group" 必须满足
        lineData.setBarWidth(barWidth);
        if (lineData.getDataSets().size() > 1) {
            getXAxis().setCenterAxisLabels(true);
            getXAxis().setAxisMinimum(0);
            getXAxis().setAxisMaximum(0 + lineData.getGroupWidth(groupSpace, barSpace) * entryCount);
            groupBars(0, groupSpace, barSpace);
        }
        animateY(animMillis);
        return this;
    }

    /**
     * 设置单位
     *
     * @param unit
     */
    public ZXBarChart setUnit(String unit) {
        this.unit = unit;
        return this;
    }

}
