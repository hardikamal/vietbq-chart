package com.zoostudio.chart;

import com.zoostudio.bean.CircleData;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class CircleChart extends DefaultChart<CircleData> {
	private Paint mPaintCirle;
	protected float mCenterX;
	protected float mCenterY;
	protected float mRadius;
	private final static float START_DEGREE = 180;
	protected float degree_offset;
	protected RectF mOval;
	protected Paint mPaintArc;
	protected Paint mPaintBorder;
	protected float mSweepAngle;
	protected RectF mBorder;
	protected CircelChartConfig chartConfig;

	public CircleChart() {
		chartConfig = new CircelChartConfig(Color.WHITE, Color.WHITE, 0, 0);
	}

	public CircleChart(CircelChartConfig chartConfig) {
		this.chartConfig = chartConfig;
	}

	public void initVariables() {
		mPaintCirle = new Paint();
		mPaintCirle.setAntiAlias(true);
		mPaintCirle.setColor(Color.GREEN);
		mCenterX = mWidth / 2;
		mCenterY = mHeight / 2;

		mPaintBorder = new Paint();
		mPaintBorder.setAntiAlias(true);
		mPaintBorder.setStyle(Style.STROKE);
		mPaintBorder.setColor(chartConfig.getBorderColor());
		mPaintBorder.setStrokeWidth(chartConfig.getBorderWidth());

		mPaintArc = new Paint();
		mPaintArc.setAntiAlias(true);
		mPaintArc.setStyle(Style.FILL);
		if (mWidth > mHeight) {
			this.mRadius = mHeight / 2;
		} else {
			this.mRadius = mWidth / 2;
		}
		float padding = mRadius * .1f;
		mRadius = mRadius * 0.9f;
		mOval = new RectF(mCenterX - mRadius, padding, mCenterX + mRadius,
				mHeight - padding);
		mBorder = new RectF(mCenterX - mRadius, padding, mCenterX + mRadius,
				mHeight - padding);
	}

	@Override
	protected void drawChart(Canvas canvas) {
		degree_offset = START_DEGREE;
		for (int i = 0, n = mChartData.size(); i < n; i++) {
			mPaintArc.setColor(mChartData.get(i).getColor());
			mSweepAngle = (mChartData.get(i).getPercent() * 360) / 100;
			if (i == n - 1) {
				if ((degree_offset + mSweepAngle) / 360 != 0) {
					degree_offset = 540 - mSweepAngle;
				}
			}
			canvas.drawArc(mOval, degree_offset, mSweepAngle, true, mPaintArc);
			mPaintArc.setAntiAlias(true);
			degree_offset += mSweepAngle;
		}
	}

}
