package com.zoostudio.chart.linechart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;

import com.zoostudio.bean.CircleNodeData;
import com.zoostudio.bean.MyColor;
import com.zoostudio.chart.R;

@SuppressLint("DrawAllocation")
public class CircleNodeView extends ComponentChartView<CircleNodeData> {

	private Paint mPaint;
	private RectF rectF;
	private float x, y;
	private float radius;
	private int color;
	private float mStartOffset;

	public CircleNodeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CircleNodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleNodeView(Context context, Handler handler, MyColor color) {
		super(context, handler);
		radius = getResources().getDimensionPixelSize(R.dimen.default_radius_node);
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
		
		mStartOffset = screenW - data.paddingRight - distanceSeriesX/2;
		
		x = mStartOffset - (distanceSeriesX * (data.endOffset- data.indexStart));

		ratio = data.valueStart / data.step;
		y = mOrginY - (ratio * distanceSeriesY);

		x = x - radius / 2;
		y = y - radius / 2;
		rectF = new RectF(x, y, x + radius, y + radius);
	}

	private void initVariables() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(color);
		mPaint.setShadowLayer(2f, 0, 0, color);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (startDraw && !mDrawFinish) {
			canvas.drawArc(rectF, 0, 360, true, mPaint);
			mDrawFinish = true;
			if (index != -1) {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						lineObsever.onDrawFinishLitener();
					}
				}, 5);
			} else {
				if (null != mDrawChartListener) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							mDrawChartListener.onFinish();
						}
					});
				}
			}
		} else if (startDraw && mDrawFinish) {
			canvas.drawArc(rectF, 0, 360, true, mPaint);
		}
	}

	@Override
	public void onDrawFinishLitener() {
		startDraw = true;
		invalidate();
	}
}
