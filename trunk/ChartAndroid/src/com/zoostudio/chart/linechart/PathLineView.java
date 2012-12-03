package com.zoostudio.chart.linechart;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zoostudio.bean.LineData;
import com.zoostudio.bean.MyColor;
import com.zoostudio.bean.PaddingChart;
import com.zoostudio.chart.R;

public class PathLineView extends View {
	protected OnFinishDrawLineListener lineObsever;
	protected ArrayList<?> data;
	private int screenW, screenH;
	private float mOrginX, mOrginY;
	private float distanceSeriesY;
	private float distanceSeriesX;
	private float ratio;
	private PaddingChart chartConfig;
	private float numberLine;
	private float numberPieceXAxis;
	private Path path;
	private Paint paint;
	private List<LineData> listData;
	private ArrayList<PointF> points;
	private float step;
	private float radius = 8f;
	private ArrayList<RectF> nodes;
	private Paint paintCircle;
	private float mStartX;
	private float y;
	private float x;
	private float borderLine;

	public PathLineView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PathLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PathLineView(Context context, MyColor color) {
		super(context);
		radius = getResources().getDimensionPixelSize(R.dimen.default_radius_node);
		nodes = new ArrayList<RectF>();
		paint = new Paint();
		paint.setColor(color.getColor());
		paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.default_border_with));
		paint.setAntiAlias(true);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setShadowLayer(1.2f, 0, 0, color.getColor());
		path = new Path();

		paintCircle = new Paint();
		paintCircle.setShadowLayer(1.2f, 0, 0, color.getColor());
		paintCircle.setAntiAlias(true);
		paintCircle.setColor(color.getColor());
	}

	public void setConfigLine(PaddingChart paddingChart, float numberLine,
			float step, float numberPieceXAxis) {
		this.chartConfig = paddingChart;
		this.numberLine = numberLine;
		this.step = step;
		this.numberPieceXAxis = numberPieceXAxis;
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		calculatePoints();
	}

	private void calculatePoints() {
		mOrginX = chartConfig.paddingLeft;
		mOrginY = screenH - chartConfig.paddingBottom;

		distanceSeriesY = (mOrginY - chartConfig.paddingTop) / numberLine;

		distanceSeriesX = (screenW - chartConfig.paddingLeft - chartConfig.paddingRight)
				/ numberPieceXAxis;
		points = new ArrayList<PointF>();
		
		mStartX = mOrginX;
		x = mOrginX + distanceSeriesX / 2;
		

		for (int i = 0, n = listData.size(); i < n; i++) {
			ratio = listData.get(i).getValue() / step;
			y = mOrginY - (ratio * distanceSeriesY);
			points.add(new PointF(x, y));
			x += distanceSeriesX;
		}
		float xCircle, yCircle;

		xCircle = points.get(0).x - radius / 2;
		yCircle = points.get(0).y - radius / 2;
		RectF rectF0 = new RectF(xCircle, yCircle, xCircle + radius, yCircle
				+ radius);
		nodes.add(rectF0);
		path.moveTo(points.get(0).x, points.get(0).y);

		for (int i = 1, n = points.size(); i < n; i++) {
			xCircle = points.get(i).x - radius / 2;
			yCircle = points.get(i).y - radius / 2;
			RectF rectFCircle = new RectF(xCircle, yCircle, xCircle + radius,
					yCircle + radius);
			nodes.add(rectFCircle);
			path.lineTo(points.get(i).x, points.get(i).y);

		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(path, paint);
		for (RectF node : nodes) {
			canvas.drawArc(node, 0, 360, true, paintCircle);
		}
	}

	public void setData(List<LineData> subList) {
		listData = subList;
	}
	
	public float getStartX() {
		return mStartX;
	}
	
	public void updateLine(float mStartX) {
		this.mStartX = mStartX + distanceSeriesX / 2;
		x = mStartX;
		points.clear();
		nodes.clear();
		path.reset();
		for (int i = 0, n = listData.size(); i < n; i++) {
			ratio = listData.get(i).getValue() / step;
			y = mOrginY - (ratio * distanceSeriesY);
			points.add(new PointF(x, y));
			x += distanceSeriesX;
		}
		
		float xCircle, yCircle;

		xCircle = points.get(0).x - radius / 2;
		yCircle = points.get(0).y - radius / 2;
		RectF rectF0 = new RectF(xCircle, yCircle, xCircle + radius, yCircle
				+ radius);
		nodes.add(rectF0);
		path.moveTo(points.get(0).x, points.get(0).y);

		for (int i = 1, n = points.size(); i < n; i++) {
			xCircle = points.get(i).x - radius / 2;
			yCircle = points.get(i).y - radius / 2;
			RectF rectFCircle = new RectF(xCircle, yCircle, xCircle + radius,
					yCircle + radius);
			nodes.add(rectFCircle);
			path.lineTo(points.get(i).x, points.get(i).y);

		}
		invalidate();
	}
}
