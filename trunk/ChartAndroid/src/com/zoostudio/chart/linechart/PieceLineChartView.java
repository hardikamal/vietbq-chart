package com.zoostudio.chart.linechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;

import com.zoostudio.bean.MyColor;
import com.zoostudio.bean.PieceLineData;
import com.zoostudio.chart.R;

@SuppressLint("ViewConstructor")
public class PieceLineChartView extends ComponentChartView<PieceLineData> {
	private Paint paint;

	private float X, Y;
	private Path path;
	private float speedX;
	private float speedY;
	private boolean finishLastDraw;
	private float EndX;
	private float EndY;

	public PieceLineChartView(Context context, Handler handler, MyColor color) {
		super(context, handler);
		paint = new Paint();
		paint.setColor(color.getColor());
		paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.default_border_line));
		speedX = getResources().getDimensionPixelSize(R.dimen.default_speed);
		paint.setAntiAlias(true);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		float boderShadow = getResources().getDimensionPixelSize(R.dimen.default_border_shader);
		paint.setShadowLayer(boderShadow, 0, 0, color.getColor());
		path = new Path();
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;

		mOrginX = data.paddingLeft;
		mOrginY = screenH - data.paddingBottom;

		distanceSeriesY = (mOrginY - data.paddingTop) / data.numberLine;

		distanceSeriesX = (screenW - data.paddingLeft - data.paddingRight)
				/ data.numberPieceXAxis;

		X = mOrginX + (distanceSeriesX * data.indexStart) + distanceSeriesX / 2;

		ratio = data.valueStart / data.step;
		Y = mOrginY - (ratio * distanceSeriesY);

		EndX = mOrginX + (distanceSeriesX * data.indexEnd) + distanceSeriesX
				/ 2;
		ratio = data.valueEnd / data.step;

		EndY = mOrginY - (ratio * distanceSeriesY);

		float hRect = EndY - Y;
		float wRect = EndX - X;
		ratio = hRect / wRect;
		
		speedY = ratio * speedX;

		path.moveTo(X, Y);
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (startDraw && !mDrawFinish) {
			if (!finishLastDraw) {
				path.lineTo(X, Y);
				X += speedX;
				Y += speedY;
				if (X > EndX) {
					speedX = X - EndX;
					speedY = ratio * speedX;
					X += speedX;
					Y += speedY;
					finishLastDraw = true;
					// paint.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
					canvas.drawPath(path, paint);
					invalidate();
					return;
				}
				canvas.drawPath(path, paint);
				invalidate();
			} else {
				path.lineTo(EndX, EndY);
				canvas.drawPath(path, paint);
				startDraw = false;
				mDrawFinish = true;
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						lineObsever.onDrawFinishLitener();
					}
				}, 10);
			}
		} else if (mDrawFinish) {
			canvas.drawPath(path, paint);
		}
	}

	public void onDrawFinishLitener() {
		startDraw = true;
		invalidate();
	}

}
