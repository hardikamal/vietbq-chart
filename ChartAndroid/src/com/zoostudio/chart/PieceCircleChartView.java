package com.zoostudio.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

import com.zoostudio.bean.CircleData;
import com.zoostudio.chart.callback.IPieceCircleChartListener;

public class PieceCircleChartView extends View implements ObserverAnimation {
	private DefaultChart mChart;
	private Animation animation;
	private Handler handler;
	private Animation animSelected;
	private Animation animUnSelected;
	private long mDuration;
	public PieceCircleChartView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		handler = new Handler();
	}

	public PieceCircleChartView(Context context,long duration) {
		super(context);
		mDuration = duration;
		handler = new Handler();
		animation = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(mDuration);

		animSelected = new ScaleAnimation(1f, 1.1f, 1f, 1.1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animSelected.setFillAfter(true);
		animSelected.setDuration(mDuration);

		animUnSelected = new ScaleAnimation(1.1f, 1f, 1.1f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animUnSelected.setFillAfter(true);
		animUnSelected.setDuration(mDuration);
	}

	public PieceCircleChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		handler = new Handler();
	}

	public void setData(int index, CircleData data, float degree,
			IPieceCircleChartListener listener,CircelChartConfig config) {
		mChart = new PieceCircleChart(config);
		((PieceCircleChart) mChart).setCircleData(data, degree);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mChart.setDimen(w, h);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		mChart.draw(canvas);
	}

	@Override
	public void onFinish() {
		setVisibility(View.VISIBLE);
		startAnimation(animation);
	}

	public void setObserverAnimation(ObserverAnimation observerAnimation) {
		this.animation.setAnimationListener(new MyAnimationListener(
				observerAnimation));
	}

	private class MyAnimationListener implements AnimationListener {
		private ObserverAnimation obsever;

		public MyAnimationListener(ObserverAnimation obsever) {
			this.obsever = obsever;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					MyAnimationListener.this.obsever.onFinish();
				}
			});
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}
	}

	public void selectedPiece() {
		startAnimation(animSelected);
	}

	public void unSelectedPiece() {
		startAnimation(animUnSelected);
	}

	public void setChartConfig(CircelChartConfig chartConfig) {
		
	}
}
