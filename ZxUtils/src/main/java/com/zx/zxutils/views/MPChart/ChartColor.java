package com.zx.zxutils.views.MPChart;

import com.zx.zxutils.R;

/**
 * Created by Xiangb on 2017/4/5.
 * 功能：
 */
public class ChartColor {
    public static int[] colors = new int[]{
            R.color.blue8c, R.color.ffd28c, R.color.red8e,
            R.color.peru, R.color.lawngreen, R.color.lightpink,
            R.color.cornflowerblue, R.color.darkkhaki, R.color.darkseagreen,
            R.color.lightsalmon, R.color.seagreen, R.color.mediumslateblue};

    public static int getColor(int num) {
        return colors[num < ChartColor.colors.length ? num : ((int) (Math.random() * ChartColor.colors.length))];
    }

    public static void setColor(int[] colorResIds) {
        colors = colorResIds;
    }

}
