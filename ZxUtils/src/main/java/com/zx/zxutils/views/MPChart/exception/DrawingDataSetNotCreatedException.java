package com.zx.zxutils.views.MPChart.exception;

public class DrawingDataSetNotCreatedException extends RuntimeException {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DrawingDataSetNotCreatedException() {
		super("Have to create a new drawing set first. Call ChartData's createNewDrawingDataSet() method");
	}

}
