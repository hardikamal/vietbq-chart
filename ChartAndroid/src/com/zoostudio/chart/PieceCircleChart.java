package com.zoostudio.chart;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.zoostudio.bean.CircleData;

public class PieceCircleChart extends CircleChart {
	private CircleData circleData;

	public PieceCircleChart(CircelChartConfig config) {
		super(config);
	}

	public void setCircleData(CircleData circleData, float degree) {
		this.circleData = circleData;
		degree_offset = degree;

	}

	@Override
	protected void draw(Canvas canvas) {
		mPaintArc.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaintArc.setColor(circleData.getColor());
		mSweepAngle = (circleData.getPercent() * 360) / 100;
		canvas.drawArc(mOval, degree_offset, mSweepAngle, true, mPaintArc);
		canvas.drawArc(mBorder, degree_offset, mSweepAngle, true, mPaintBorder);
		mPaintArc.setAntiAlias(true);
	}

}
