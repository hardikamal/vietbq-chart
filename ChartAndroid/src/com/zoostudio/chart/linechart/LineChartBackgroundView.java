package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import com.zoostudio.bean.SeriesY;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class LineChartBackgroundView extends View {

	private float distanceSeriesY;
	private float mOrginY;
	private float numberLine;
	private float mOrginX;
	private int color;
	private Paint mPaintLine;
	private float mWidth;
	private LineChartConfig chartConfig;

	public LineChartBackgroundView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public LineChartBackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineChartBackgroundView(Context context, int color) {
		super(context);
		this.color = color;
		init();
	}

	public void setConfig(LineChartConfig chartConfig, float numberLine) {
		this.chartConfig = chartConfig;
		this.numberLine = numberLine;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mOrginX = chartConfig.paddingLeft;
		mOrginY = h - chartConfig.paddingBottom;
		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;
	}

	public void updateLine(float numberLine) {
		this.numberLine = numberLine;
		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;
		invalidate();
	}

	private void init() {
		mPaintLine = new Paint();
		mPaintLine.setAntiAlias(true);
		mPaintLine.setColor(color);
		mPaintLine.setStrokeWidth(0.5f);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		drawLine(canvas);
	}

	private void drawLine(Canvas canvas) {
		float offset = mOrginY - distanceSeriesY;
		for (int i = 1; i <= numberLine; i++) {
			canvas.drawLine(mOrginX, offset, mWidth - chartConfig.paddingRight,
					offset, mPaintLine);
			offset -= distanceSeriesY;
		}
	}

}
