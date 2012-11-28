package com.zoostudio.chart.linechart;

import com.zoostudio.bean.PieceLineData;
import com.zoostudio.chart.DefaultChart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Path.FillType;

public class PieceLineChart extends DefaultChart<PieceLineData> {
	private Paint paint;
	private PointF mStartPoint;
	private PointF mEndPoint;
	private Path mPath;
	private float mOrginX, mOrginY;
	private PieceLineData lineData;

	@Override
	protected void initVariables() {
		mOrginX = lineData.paddingLeft;
		mOrginY = mHeight - lineData.paddingBottom;

		mPath = new Path();
		paint = new Paint();
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		mStartPoint = new PointF(mWidth / 2, mHeight / 2);
		mEndPoint = new PointF(mWidth, mHeight);
		mPath.moveTo(mStartPoint.x, mStartPoint.y);
		mPath.lineTo(mEndPoint.x, mEndPoint.y);
	}

	@Override
	protected void drawChart(Canvas canvas) {
		canvas.drawPath(mPath, paint);
	}

	@Override
	protected void drawBackground(Canvas canvas) {
	}
}
