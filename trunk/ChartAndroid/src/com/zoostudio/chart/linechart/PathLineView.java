package com.zoostudio.chart.linechart;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.zoostudio.bean.LineData;
import com.zoostudio.bean.MyColor;
import com.zoostudio.bean.PaddingChart;

public class PathLineView extends View {
	protected OnFinishDrawLineListener lineObsever;
	protected ArrayList<?> data;
	private int screenW, screenH;
	private float mOrginX, mOrginY;
	private float distanceSeriesY;
	private float distanceSeriesX;
	private float ratio;
	private int index = -1;
	private PaddingChart chartConfig;
	private float numberLine;
	private float numberPieceXAxis;
	private Path path;
	private Paint paint;
	private List<LineData> listData;
	private ArrayList<PointF> points;
	private float step;

	public PathLineView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PathLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PathLineView(Context context, MyColor color) {
		super(context);
		paint = new Paint();
		paint.setColor(color.getColor());
		paint.setStrokeWidth(1.5f);
		paint.setAntiAlias(true);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		path = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	public void setConfigLine(PaddingChart paddingChart, float numberLine,
			float step) {
		this.chartConfig = paddingChart;
		this.numberLine = numberLine;
		this.step = step;
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;

		mOrginX = chartConfig.paddingLeft;
		mOrginY = screenH - chartConfig.paddingBottom;

		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;

		distanceSeriesX = (screenW - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ numberPieceXAxis;
		points = new ArrayList<PointF>();
		float x;
		float y;

		for (int i = 0, n = listData.size(); i < n; i++) {
			x = mOrginX + (distanceSeriesX * i) + distanceSeriesX / 2;

			ratio = listData.get(i).getValue() / step;
			y = mOrginY - (ratio * distanceSeriesY);

			points.add(new PointF(x, y));
		}
		path.moveTo(points.get(0).x, points.get(0).y);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText("OK CON DE", 100, 100, paint);
	}

	public void setData(List<LineData> subList) {
		listData = subList;
	}
}
