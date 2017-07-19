package com.zx.zxutils.views.MPChart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.zx.zxutils.R;
import com.zx.zxutils.views.MPChart.charts.PieChart;
import com.zx.zxutils.views.MPChart.components.Description;
import com.zx.zxutils.views.MPChart.components.Legend;
import com.zx.zxutils.views.MPChart.data.PieData;
import com.zx.zxutils.views.MPChart.data.PieDataSet;
import com.zx.zxutils.views.MPChart.data.PieEntry;
import com.zx.zxutils.views.MPChart.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/5.
 * 功能：重写饼状图
 */
public class ZXPieChart extends PieChart {
    private Context context;

    private PieData pieData;

    public ZXPieChart(Context context) {
        super(context, null);
    }

    public ZXPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPieChart(context);
    }

    private void initPieChart(Context context) {
        this.context = context;
        //设置标记
        Description description = new Description();
        description.setText("重庆知行");
        description.setTextColor(ContextCompat.getColor(context, R.color.lightgray));
        description.setYOffset(10);
        setDescription(description);

        //属性设置
        setNoDataText("暂未获取到数据");
        setNoDataTextColor(ContextCompat.getColor(context, R.color.deepskyblue));
        setDrawEntryLabels(true);//将此设置为true以将x值文本绘制到饼图切片中
        setUsePercentValues(true);//如果启用此选项，图表中的值将以百分比形式绘制，而不是以原始值绘制。ValueFormatter为格式提供的值随后以百分比形式提供。
        setHoleRadius(40);//设置大圆里面小圆半径，和洞不是一个圆
        setTransparentCircleRadius(43);

        //图例设置
        getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//图例的横向位置
        getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例的纵向位置
        getLegend().setDrawInside(true);
        getLegend().setForm(Legend.LegendForm.CIRCLE);//图例的样式
        getLegend().setWordWrapEnabled(true);
        getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例纵向排列
    }

    /**
     * 添加data
     *
     * @param pieDataList
     * @param label
     */
    public ZXPieChart addData(List<ChartKeyValue> pieDataList, String label) {
        setCenterText(label);
        List<PieEntry> entrys = new ArrayList<>();
        for (int i = 0; i < pieDataList.size(); i++) {
            entrys.add(new PieEntry(pieDataList.get(i).getValue(), pieDataList.get(i).getKey()));
        }
        PieDataSet dataSet = new PieDataSet(entrys, "");
        pieData = new PieData(dataSet);
        return this;
    }

    /**
     * 添加结束
     *
     * @return
     */
    public ZXPieChart addComplete(int animMillis) {
        PieDataSet dataSet = (PieDataSet) pieData.getDataSet();
        //重设颜色
        int[] color = new int[pieData.getDataSet().getEntryCount()];
        for (int i = 0; i < pieData.getDataSet().getEntryCount(); i++) {
            color[i] = ContextCompat.getColor(context, ChartColor.getColor(i));
        }
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setValueTextSize(10);
        if (dataSet.getEntryCount() > 7) {
            dataSet.setValueTextColor(ContextCompat.getColor(context, R.color.cadetblue));
            dataSet.setValueLineColor(ContextCompat.getColor(context, R.color.white));
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        } else {
            dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
            dataSet.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        }
        dataSet.setColors(color);
        dataSet.setSliceSpace(4);//块间距
        dataSet.setSelectionShift(10);//选中状态多出的
        pieData.getDataSet().setDrawValues(true);//是否显示百分比
        super.setData(pieData);
        animateY(animMillis);
        return this;
    }

}
