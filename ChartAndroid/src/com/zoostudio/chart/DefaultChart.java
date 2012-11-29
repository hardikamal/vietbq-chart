package com.zoostudio.chart;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.zoostudio.bean.ChartData;
import com.zoostudio.chart.exception.InvalidSeriesException;

public abstract class DefaultChart<T extends ChartData> {

	protected int mWidth;
	protected int mHeight;
	protected Paint mPaintDefault;
	protected ArrayList<T> mChartData;
	protected ArrayList<T>[] mChartDataSeries;
	protected boolean isFirstTime = true;

	public DefaultChart() {
		this.mWidth = -1;
		this.mHeight = -1;
		mPaintDefault = new Paint();
		mPaintDefault.setColor(Color.BLACK);
		mChartData = new ArrayList<T>();
	}

	protected abstract void initVariables();

	public DefaultChart(int width, int heigth) {
		this.mWidth = width;
		this.mHeight = heigth;
		mPaintDefault = new Paint();
		mPaintDefault.setColor(Color.BLACK);
		mChartData = new ArrayList<T>();
	}

	protected void draw(Canvas canvas) {
		drawBackground(canvas);
		drawChart(canvas);
	}

	protected abstract void drawChart(Canvas canvas);

	protected void drawBackground(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
	}

	public void setSeries(ArrayList<T>... chartDataSeries)
			throws InvalidSeriesException {
		mChartDataSeries = chartDataSeries;
		this.mChartData = chartDataSeries[0];
		validSeries();
	}

	private void validSeries() throws InvalidSeriesException {
		if (mChartDataSeries.length == 1)
			return;
		for (int i = 0; i < mChartDataSeries.length - 1; i++) {
			if (mChartDataSeries[i].size() != mChartDataSeries[i + 1].size()) {
				throw new InvalidSeriesException();
			}
		}
	}

	public void setDimen(int width, int height) {
		if (!isFirstTime)
			return;
		isFirstTime = false;
		this.mWidth = width;
		this.mHeight = height;
		initVariables();
	}

	public void onTouchDown(MotionEvent motionEvent) {

	}

	public void onTouchMove(MotionEvent event) {
		Log.i("Chart", "onTouchMove");
	}

	public void onTouchUp(MotionEvent event) {

	}
}
