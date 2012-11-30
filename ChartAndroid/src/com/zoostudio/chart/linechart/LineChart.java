package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;

import com.zoostudio.bean.LineData;
import com.zoostudio.bean.SeriesX;
import com.zoostudio.bean.SeriesY;
import com.zoostudio.chart.DefaultChart;

public class LineChart extends DefaultChart<LineData> {
	public static enum TYPE {
		DAY, WEEK, MONTH, YEAR
	}

	float numberStep;
	float numberLine;

	LineChartConfig chartConfig;

	private float mOrginX, mOrginY;
	private Path mPathAxisX;
	private Path mPathAxisY;

	private Paint paintAxisX;
	private Paint paintAxisY;

	private float distanceSeriesY;
	private float distanceSeriesX;

	private Paint mPaintLine;

	private Paint mPaintSeriesY;
	public ArrayList<SeriesY> seriesY;
	public ArrayList<SeriesX> seriesX;
	private Paint mPaintSeriesX;
	private TYPE type;
	private int mStartOffset;
	private int mEndOffset;
	
	
	public LineChart(LineChart.TYPE type) {
		this.type = type;
	}

	@Override
	protected void initVariables() {
		mOrginX = chartConfig.paddingLeft;
		mOrginY = mHeight - chartConfig.paddingBottom;

		mPathAxisX = new Path();
		mPathAxisY = new Path();

		mPathAxisX.moveTo(mOrginX, mOrginY);
		mPathAxisY.moveTo(mOrginX, mOrginY);

		paintAxisX = new Paint();
		paintAxisX.setColor(chartConfig.colorAxisX);
		paintAxisX.setStrokeWidth(1);
		paintAxisX.setAntiAlias(true);
		paintAxisX.setStrokeCap(Paint.Cap.ROUND);
		paintAxisX.setStrokeJoin(Paint.Join.ROUND);
		paintAxisX.setStyle(Paint.Style.STROKE);
		paintAxisX.setShadowLayer(2, 0, 0, Color.DKGRAY);

		paintAxisY = new Paint();
		paintAxisY.setColor(chartConfig.colorAxisX);
		paintAxisY.setStrokeWidth(1);
		paintAxisY.setAntiAlias(true);
		paintAxisY.setStrokeCap(Paint.Cap.ROUND);
		paintAxisY.setStrokeJoin(Paint.Join.ROUND);
		paintAxisY.setStyle(Paint.Style.STROKE);
		paintAxisY.setShadowLayer(2, 0, 0, Color.DKGRAY);

		mPaintLine = new Paint();
		mPaintLine.setAntiAlias(true);
		mPaintLine.setColor(chartConfig.colorLine);
		mPaintLine.setStrokeWidth(0.5f);

		mPaintSeriesY = new Paint();
		mPaintSeriesY.setAntiAlias(true);
		mPaintSeriesY.setColor(chartConfig.colorSeriesY);
		mPaintSeriesY.setTypeface(Typeface.SANS_SERIF);
		mPaintSeriesY.setTextSize(chartConfig.fontSize);

		mPaintSeriesX = new Paint();
		mPaintSeriesX.setAntiAlias(true);
		mPaintSeriesX.setColor(chartConfig.colorSeriesX);
		mPaintSeriesX.setTypeface(Typeface.SANS_SERIF);
		mPaintSeriesX.setTextSize(chartConfig.fontSize);
		mPaintSeriesX.setShader(new RadialGradient(0, 0, 5, Color.GRAY,
				Color.BLACK, TileMode.CLAMP));

		// Khoang cach giua moi line
		float size = mEndOffset - mStartOffset;
		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;
		distanceSeriesX = (mWidth - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ size;
	}
	
	public void updateDistanceX(){
		float size = mEndOffset - mStartOffset;
		distanceSeriesX = (mWidth - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ size;
	}

	@Override
	protected void drawChart(Canvas canvas) {
		drawBackground(canvas);
		drawLine(canvas);
		drawAxis(canvas);
		drawSeriesY(canvas);
		drawSeriesX(canvas);
	}

	private void drawAxis(Canvas canvas) {
		// Ve truc X
		canvas.drawLine(mOrginX, mOrginY, mWidth - chartConfig.paddingRight,
				mOrginY, paintAxisX);

		// // Ve truc Y
		canvas.drawLine(mOrginX, mOrginY, mOrginX, chartConfig.paddingTop,
				paintAxisY);
	}

	private void drawLine(Canvas canvas) {
		float offset = mOrginY - distanceSeriesY;
		for (int i = 1; i <= numberLine; i++) {
			canvas.drawLine(mOrginX, offset, mWidth - chartConfig.paddingRight,
					offset, mPaintLine);
			offset -= distanceSeriesY;
		}
	}

	private void drawSeriesY(Canvas canvas) {
		float offset = mOrginY - distanceSeriesY;
		float padding, height;
		for (int i = 0; i < numberLine; i++) {
			padding = seriesY.get(i).padding;
			height = seriesY.get(i).height;
			canvas.drawLine(mOrginX - 4, offset, mOrginX, offset, mPaintSeriesY);
			canvas.drawText(seriesY.get(i).title, mOrginX - padding, offset
					+ height / 2, mPaintSeriesY);
			offset -= distanceSeriesY;
		}
	}

	private void drawSeriesX(Canvas canvas) {
		float offset = mOrginX;
		float centerX;
		float paddingRight;
		float padding;
		for (int i = mStartOffset; i < mEndOffset; i++) {
			centerX = seriesX.get(i).centerX;
			padding = seriesX.get(i).padding;
			paddingRight = distanceSeriesX / 2 - centerX;
			if (i > 0)
				canvas.drawLine(offset, mOrginY, offset, mOrginY + 4,
						mPaintSeriesX);
			canvas.drawText(seriesX.get(i).title, offset + paddingRight,
					mOrginY + seriesX.get(i).height - padding, mPaintSeriesX);
			offset += distanceSeriesX;
		}
	}

	public ArrayList<LineData> getChartData() {
		return mChartData;
	}

	public ArrayList<LineData>[] getChartDataSeries() {
		return mChartDataSeries;
	}

	public ArrayList<SeriesX> getSeriesX() {
		return null;
	}

	public ArrayList<SeriesY> getSeriesY() {
		return null;
	}

	public void setStartOffset(int mStartOffset) {
		this.mStartOffset = mStartOffset;
	}

	public void setEndOffset(int mEndOffset) {
		this.mEndOffset = mEndOffset;
	}
}
