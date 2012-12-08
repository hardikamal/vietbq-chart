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
	private float paddingRightText;
	private volatile boolean isBoundLeft;
	private volatile boolean isBoundRight;
	private int mTotalNode;
	private boolean mfound;
	private float offset;
	private float paddingSeriesY;
	private float heightSeriesY;

	@Override
	protected void initVariables() {
		isBoundRight = true;
		mTotalNode = seriesX.size();
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

		genDistanceY();

		distanceSeriesX = (mWidth - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ totalNodeX;

		boundRight = mWidth - chartConfig.paddingRight;
	}

	public void genDistanceY() {
		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;
	}

	public float getDistanceSeriesY() {
		return distanceSeriesY;
	}

	public void setTotalNodeX(int totalNodeX) {
		this.totalNodeX = totalNodeX;
	}

	public void updateDistanceX() {
		float size = mEndOffset - mStartOffset;
		distanceSeriesX = (mWidth - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ size;
	}

	@Override
	protected void drawChart(Canvas canvas) {
		// drawLine(canvas);
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


	private void drawSeriesY(Canvas canvas) {
		offset = mOrginY - distanceSeriesY;
		for (int i = 0; i < numberLine; i++) {
			paddingSeriesY = seriesY.get(i).padding;
			heightSeriesY = seriesY.get(i).height;
			canvas.drawLine(mOrginX - 4, offset, mOrginX, offset, mPaintSeriesY);
			canvas.drawText(seriesY.get(i).title, mOrginX - paddingSeriesY,
					offset + heightSeriesY / 2, mPaintSeriesY);
			offset -= distanceSeriesY;
		}
	}

	private void drawSeriesX(Canvas canvas) {
		for (int i = mTotalNode - 1; i >= 0; i--) {
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

	public boolean updateSeriesXMoveRight(float startOffsetX) {
		if (isBoundLeft)
			return false;
		float offsetX = Math.abs(startOffsetX)
				- ((mTotalNode - 1) * distanceSeriesX);
		if (offsetX > mOrginX) {
			float distance = offsetX - mOrginX;
			startOffsetX -= distance;
			isBoundLeft = true;
		}
		isBoundRight = false;
		getNewPosition(startOffsetX);
		return true;
	}

	private void getNewPosition(float startOffsetX) {
		mfound = false;
		for (int i = mTotalNode - 1; i >= 0; i--) {
			paddingRightText = distanceSeriesX / 2 - seriesX.get(i).centerX;
			seriesX.get(i).x = startOffsetX;
			seriesX.get(i).y = mStartOffsetSeriesY;
			seriesX.get(i).paddingRightText = paddingRightText;
			startOffsetX -= distanceSeriesX;
			if (seriesX.get(i).x - distanceSeriesX / 2 <= boundRight && !mfound) {
				mfound = true;
				mEndOffset = i;
			}

			if (seriesX.get(mStartOffset).x + distanceSeriesX / 2 > mOrginX
					&& mStartOffset > 0) {
				mStartOffset--;
			}
		}
		System.out.println("Title Start  = " + seriesX.get(mStartOffset).title
				+ "- End = " + seriesX.get(mEndOffset).title);
	}

	public boolean updateSeriesXMoveLeft(float startOffsetX) {
		if (isBoundRight)
			return false;
		float offsetX = startOffsetX;
		if (offsetX + distanceSeriesX < boundRight) {
			startOffsetX = boundRight - distanceSeriesX;
			isBoundRight = true;
		}
		isBoundLeft = false;
		getNewPosition(startOffsetX);
		return true;
	}

	public int getStartOffset() {
		return mStartOffset;
	}

	public int getEndOffset() {
		return mEndOffset;
	}

	public boolean isBoundRight() {
		return isBoundRight;
	}

	public boolean isBoundLeft() {
		return isBoundLeft;
	}

	public float validStartOffsetXMoveLeft(float startOffsetX) {
		return startOffsetX;
	}

	public float getFirstX() {
		return seriesX.get(mTotalNode - 1).x;
	}

	public float getDistanceSeriesX() {
		return distanceSeriesX;
	}

	public float getOffsetStartX() {
		return seriesX.get(mEndOffset).x + distanceSeriesX;
	}
}
