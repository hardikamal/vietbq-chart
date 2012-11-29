package com.zoostudio.chart.linechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zoostudio.bean.CircleNodeData;
import com.zoostudio.bean.MyColor;

@SuppressLint("DrawAllocation")
public class CircleNodeView extends ComponentChartView<CircleNodeData> {

	private Paint mPaint;
	private RectF rectF;
	private float x, y;
	private float radius;
	private int color;

	public CircleNodeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CircleNodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleNodeView(Context context, MyColor color) {
		super(context);
		radius = 8f;
		this.color = color.getColor();
		initVariables();

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;

		mOrginX = data.paddingLeft;
		mOrginY = screenH - data.paddingBottom;

		distanceSeriesY = (mOrginY - data.paddingTop) / data.numberLine;
		distanceSeriesX = (screenW - data.paddingLeft - data.paddingRight)
				/ data.numberPieceXAxis;

		x = mOrginX + (distanceSeriesX * data.indexStart) + distanceSeriesX / 2;

		ratio = data.valueStart / data.step;
		y = mOrginY - (ratio * distanceSeriesY);

		x = x - radius / 2;
		y = y - radius / 2;
		rectF = new RectF(x, y, x + radius, y + radius);
		// mPaint.setShadowLayer(2f, x + radius / 2, y + radius / 2,
		// Color.GREEN);
	}

	private void initVariables() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(color);
		mPaint.setShadowLayer(2f, 0, 0,color);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!startDraw)
			return;
		canvas.drawArc(rectF, 0, 360, true, mPaint);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (null != lineListener)
					lineListener.onDrawFinishLitener();
			}
		}).start();
	}

	@Override
	public void onDrawFinishLitener() {
		startDraw = true;
		postInvalidate();
	}
}
