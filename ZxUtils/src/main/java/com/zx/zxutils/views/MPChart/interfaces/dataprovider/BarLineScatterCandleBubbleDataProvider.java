package com.zx.zxutils.views.MPChart.interfaces.dataprovider;

import com.zx.zxutils.views.MPChart.components.YAxis.AxisDependency;
import com.zx.zxutils.views.MPChart.data.BarLineScatterCandleBubbleData;
import com.zx.zxutils.views.MPChart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
