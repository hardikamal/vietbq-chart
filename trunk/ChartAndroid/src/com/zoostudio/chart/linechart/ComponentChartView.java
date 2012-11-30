package com.zoostudio.chart.linechart;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.zoostudio.bean.ChartData;

public abstract class ComponentChartView<T extends ChartData> extends View implements OnFinishDrawLineListener{
	protected boolean startDraw = false;
	protected OnFinishDrawLineListener lineObsever;
	protected T data;
	protected int screenW, screenH;
	protected float mOrginX, mOrginY;
	protected float distanceSeriesY;
	protected float distanceSeriesX;
	protected float ratio;
	protected int index=-1;
	protected boolean mDrawFinish;
	protected Handler handler;
	protected OnAnimationDrawFinish mDrawChartListener;
	public ComponentChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ComponentChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ComponentChartView(Context context, Handler handler) {
		super(context);
		this.handler = handler;
	}
	
	public void setOnAnimationFinishLitener(
			OnFinishDrawLineListener obsever,int index) {
		this.lineObsever = obsever;
		this.index = index;
	}
	public void setData(T data) {
		this.data = data;
	}

	public void setOnDrawChartFinishListener(OnAnimationDrawFinish listener) {
		mDrawChartListener = listener;
	}
}
