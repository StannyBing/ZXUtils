package com.zx.zxutils.views.MPChart;

import android.content.Context;
import android.util.AttributeSet;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.views.MPChart.charts.LineChart;
import com.zx.zxutils.views.MPChart.components.AxisBase;
import com.zx.zxutils.views.MPChart.components.Description;
import com.zx.zxutils.views.MPChart.components.Legend;
import com.zx.zxutils.views.MPChart.components.XAxis;
import com.zx.zxutils.views.MPChart.data.Entry;
import com.zx.zxutils.views.MPChart.data.LineData;
import com.zx.zxutils.views.MPChart.data.LineDataSet;
import com.zx.zxutils.views.MPChart.formatter.IAxisValueFormatter;
import com.zx.zxutils.views.MPChart.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/5.
 * 功能：重写折线图
 */
public class ZXLineChart extends LineChart {
    private Context context;
    private String unit = "";
    private List<ILineDataSet> tempDataSets = new ArrayList<>();

    //应在X轴绘制的标签
    private List<String> labels = new ArrayList<>();

    public ZXLineChart(Context context) {
        super(context, null);
    }

    public ZXLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLineChart(context);
    }

    /**
     * 初始化LineChart
     *
     * @param context
     */
    private void initLineChart(Context context) {
        this.context = context;
        //设置标记
        Description description = new Description();
//        description.setText("重庆知行");
        description.setText("");
        description.setTextColor(ZXSystemUtil.transColor(R.color.lightgray));
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
        getAxisLeft().setGridColor(ZXSystemUtil.transColor( R.color.gray_de));//横向背景色
        getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int) value) + unit;
            }
        });
        //图例设置
//        getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//图例的横向位置
//        getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例的纵向位置
//        getLegend().setDrawInside(true);
        getLegend().setForm(Legend.LegendForm.LINE);//图例的样式
        getLegend().setWordWrapEnabled(true);
//        getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例纵向排列
    }

    /**
     * 添加data
     *
     * @param lineDataList
     * @param legendName
     */
    public ZXLineChart addData(List<ChartKeyValue> lineDataList, String legendName) {
        List<Entry> entrys = new ArrayList<>();
        if (lineDataList.size() > labels.size()) {
            labels.clear();
            for (int i = 0; i < lineDataList.size(); i++) {//添加label
                labels.add(lineDataList.get(i).getKey());
            }
        }
        for (int i = 0; i < lineDataList.size(); i++) {
            entrys.add(new Entry(i, lineDataList.get(i).getValue()));
        }
        LineDataSet dataSet = new LineDataSet(entrys, legendName);
        tempDataSets.add(dataSet);
        return this;
    }

    public void clear() {
        labels.clear();
        tempDataSets.clear();
        addComplete(1);
    }

    /**
     * 数据添加完成
     *
     * @param animMillis
     */
    public ZXLineChart addComplete(int animMillis) {
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.addAll(tempDataSets);
        LineData lineData = new LineData(dataSets);
        XAxis xAxis = getXAxis();
        xAxis.setGranularity(1f);  //最小轴步骤（间隔）为1
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
        for (int i = 0; i < dataSets.size(); i++) {
            LineDataSet dataSet = (LineDataSet) dataSets.get(i);
            dataSet.setLineWidth(2.0f);//线宽
            dataSet.setCircleRadius(3.5f);//圆形大小
            dataSet.setColor(ZXSystemUtil.transColor(ChartColor.getColor(i)));//颜色
            dataSet.setCircleColor(ZXSystemUtil.transColor( ChartColor.getColor(i)));//圆形颜色
            dataSet.setHighLightColor(ZXSystemUtil.transColor( ChartColor.getColor(i)));//高亮线颜色
        }
        super.setData(lineData);
        tempDataSets.clear();
        animateY(animMillis);
        return this;
    }

    /**
     * 设置单位
     *
     * @param unit
     */
    public ZXLineChart setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
