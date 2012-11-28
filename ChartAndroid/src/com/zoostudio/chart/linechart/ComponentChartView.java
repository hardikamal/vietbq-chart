package com.zoostudio.chart.linechart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zoostudio.bean.ChartData;

public abstract class ComponentChartView<T extends ChartData> extends View implements OnFinishDrawLineListener{
	protected boolean startDraw = false;
	protected OnFinishDrawLineListener lineListener;
	protected T data;
	protected int screenW, screenH;
	protected float mOrginX, mOrginY;
	protected float distanceSeriesY;
	protected float distanceSeriesX;
	protected float ratio;
	public ComponentChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ComponentChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ComponentChartView(Context context) {
		super(context);
	}
	
	public void setOnAnimationFinishLitener(
			OnFinishDrawLineListener lineListener) {
		this.lineListener = lineListener;
	}
	public void setData(T data) {
		this.data = data;
	}
}
