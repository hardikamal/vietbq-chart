package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
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

	private RectF mRectPanelLeft;
	private RectF mRectPanelRight;

	private Paint paintPanelLeft;
	private float mStartOffsetSeriesY;
	private float aHalf;

	private float boundRight;
	private float mLastX;

	/**
	 * Tong so node tren truc X
	 */
	private int totalNodeX;

	/**
	 * Vi tri x cua phan tu cuoi cung trong mang
	 */
	private float X_LAST_POSTION;
	private float boundLeft;
	private float distanceX;
	private float paddingRightText;

	public LineChart(LineChart.TYPE type) {
		this.type = type;
	}

	@Override
	protected void initVariables() {
		mOrginX = chartConfig.paddingLeft;
		mOrginY = mHeight - chartConfig.paddingBottom;
		mStartOffsetSeriesY = mOrginY;

		mRectPanelLeft = new RectF(0, 0, mOrginX, mHeight);
		mRectPanelRight = new RectF(mWidth - chartConfig.paddingRight, 0,
				mWidth, mHeight);
		mPathAxisX = new Path();
		mPathAxisY = new Path();

		mPathAxisX.moveTo(mOrginX, mOrginY);
		mPathAxisY.moveTo(mOrginX, mOrginY);

		paintPanelLeft = new Paint();
		paintPanelLeft.setColor(Color.WHITE);

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
		totalNodeX = mEndOffset - mStartOffset;

		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;
		distanceSeriesX = (mWidth - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ totalNodeX;

		boundRight = mWidth - chartConfig.paddingRight;
		boundLeft = mOrginX;

		X_LAST_POSTION = boundRight;
	}

	public void updateDistanceX() {
		float size = mEndOffset - mStartOffset;
		distanceSeriesX = (mWidth - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ size;
	}

	@Override
	protected void drawChart(Canvas canvas) {
		drawLine(canvas);
		drawSeriesX(canvas);
		drawPanel(canvas);
		drawAxis(canvas);
		drawSeriesY(canvas);

	}

	private void drawPanel(Canvas canvas) {
		canvas.drawRect(mRectPanelLeft, paintPanelLeft);
		canvas.drawRect(mRectPanelRight, paintPanelLeft);
	}

	@Override
	protected void drawBackground(Canvas canvas) {
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
		for (int i = mStartOffset; i < mEndOffset; i++) {
			canvas.drawLine(seriesX.get(i).x, mStartOffsetSeriesY,
					seriesX.get(i).x, mStartOffsetSeriesY + 4, mPaintSeriesX);
			canvas.drawText(seriesX.get(i).title,
					seriesX.get(i).x + seriesX.get(i).paddingRightText,
					seriesX.get(i).y + seriesX.get(i).height, mPaintSeriesX);
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

	public synchronized void updateSeriesX(float startOffsetX, boolean needRecal) {
		if (needRecal) {
			if (mStartOffset > 0) {
				System.out.println("Recalculate");
				startOffsetX -= distanceSeriesX;
				mStartOffset--;
			}
		}
		for (int i = mStartOffset; i < mEndOffset; i++) {
			paddingRightText = distanceSeriesX / 2 - seriesX.get(i).centerX;
			seriesX.get(i).x = startOffsetX;
			seriesX.get(i).y = mStartOffsetSeriesY;
			seriesX.get(i).paddingRightText = paddingRightText;
			startOffsetX += distanceSeriesX;
		}
	}

	public synchronized void updateSeriesXByRight(float startOffsetX) {
		if (mStartOffset > 0) {
			System.out.println("Recalculate");
			startOffsetX -= distanceSeriesX;
			mStartOffset--;
			mEndOffset--;
		}
		for (int i = mStartOffset; i < mEndOffset; i++) {
			paddingRightText = distanceSeriesX / 2 - seriesX.get(i).centerX;
			seriesX.get(i).x = startOffsetX;
			seriesX.get(i).y = mStartOffsetSeriesY;
			seriesX.get(i).paddingRightText = paddingRightText;
			startOffsetX += distanceSeriesX;
		}
	}

	public synchronized void updateSeriesXByLeft(float startOffsetX) {
		if (mEndOffset < totalNodeX) {
			System.out.println("Recalculate");
			mStartOffset++;
			mEndOffset++;
		}
		for (int i = mStartOffset; i < mEndOffset; i++) {
			paddingRightText = distanceSeriesX / 2 - seriesX.get(i).centerX;
			seriesX.get(i).x = startOffsetX;
			seriesX.get(i).y = mStartOffsetSeriesY;
			seriesX.get(i).paddingRightText = paddingRightText;
			startOffsetX += distanceSeriesX;
		}
	}

	public boolean isBoundRight() {
		if (seriesX.get(mEndOffset - 1).x == boundRight)
			return true;
		return false;
	}

	public float validStartOffsetXMoveLeft(float startOffsetX) {
		return startOffsetX;
	}

	public synchronized boolean validMoveRight() {
		if (seriesX.get(mStartOffset).x >= mOrginX) {
			return true;
		}
		return false;
	}

	public boolean validMoveLeft() {
		System.out.println("X = " + seriesX.get(mEndOffset-1).x + distanceSeriesX);
		if (seriesX.get(mEndOffset-1).x + distanceSeriesX<= boundRight) {
			System.out.println("BUzz");
			return true;
		}
		return false;
	}

	public float getFirstX() {
		return seriesX.get(mStartOffset).x;
	}

}
