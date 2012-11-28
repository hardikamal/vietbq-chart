package com.zoostudio.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class GraphicView extends View {
	private int mWidth;
	private int mHeight;
	private DefaultChart mChart;

	public GraphicView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GraphicView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GraphicView(Context context, int width, int height) {
		super(context);
		this.mWidth = width;
		this.mHeight = height;
		mChart = new CircleChart();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mChart.draw(canvas);
	}
	
	public void setChart(DefaultChart chart){
		this.mChart = chart;
	}
	
	public void getDimentionChart(int[] dim){
		dim[0] = this.mWidth;
		dim[1] = this.mHeight;
	}

	public DefaultChart getChart() {
		return mChart;
	}
}
