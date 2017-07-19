package com.zx.zxutils.views.MPChart.interfaces.dataprovider;

import com.zx.zxutils.views.MPChart.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
