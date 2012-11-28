package com.zoostudio.chart;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zoostudio.bean.CircleData;
import com.zoostudio.chart.callback.IPieceCircleChartListener;

public class CircleControllerView extends View {
	private ArrayList<CircleData> data;
	int action;
	private float mCenterX;
	private float mCenterY;
	private float mRadius;
	private int mWidth;
	private int mHeight;
	private boolean isFirstTime;
	private CircleData circleData;
	private float startDegree;
	private IPieceCircleChartListener listener;
	private float endDegree;
	private int mLastSelected;

	public CircleControllerView(Context context,
			IPieceCircleChartListener listener) {
		super(context);
		isFirstTime = true;
		mLastSelected = -1;
		this.listener = listener;
	}

	public void setData(ArrayList<CircleData> data) {
		this.data = data;
	}

	private void initVariables() {
		isFirstTime = false;
		mWidth = getWidth();
		mHeight = getHeight();

		mCenterX = mWidth / 2;
		mCenterY = mHeight / 2;
		if (mWidth > mHeight) {
			this.mRadius = mHeight / 2;
		} else {
			this.mRadius = mWidth / 2;
		}
		mRadius = mRadius * 0.9f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isFirstTime)
			initVariables();
		action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			processTouchDown(event);
			return true;
		case MotionEvent.ACTION_MOVE:
			prcessTouchMove(event);
			return true;
		case MotionEvent.ACTION_UP:
			processTouchUp(event);
			return true;

		default:
			return super.onTouchEvent(event);
		}
	}

	private void prcessTouchMove(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (!isInsideCircle(x, y)) {
			if (mLastSelected != -1)
				if (data.get(mLastSelected).isSelected()) {
					Log.e("CircelController", "Index = " + mLastSelected);
					data.get(mLastSelected).setSelected(false);
					listener.onOutsidePiece(mLastSelected);
				}
			return;
		}

		double degree = getDegreeTouch(x, y);
		for (int i = 0, n = data.size(); i < n; i++) {
			circleData = data.get(i);
			startDegree = circleData.getStartDegree();
			endDegree = circleData.getEndDegree();

			if (degree >= startDegree && degree < endDegree
					&& !circleData.isSelected()) {
				listener.onInsidePiece(i);
				if (mLastSelected != -1 && mLastSelected != i
						&& data.get(mLastSelected).isSelected()) {
					listener.onOutsidePiece(mLastSelected);
					data.get(mLastSelected).setSelected(false);
				}
				data.get(i).setSelected(true);
				mLastSelected = i;
				break;
			}
		}
	}

	private void processTouchUp(MotionEvent event) {
		if (mLastSelected == -1)
			return;

		if (!data.get(mLastSelected).isSelected())
			return;
		data.get(mLastSelected).setSelected(false);
		listener.onOutsidePiece(mLastSelected);
	}

	private void processTouchDown(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (!isInsideCircle(x, y))
			return;

		double degree = getDegreeTouch(x, y);
		for (int i = 0, n = data.size(); i < n; i++) {
			circleData = data.get(i);
			startDegree = circleData.getStartDegree();
			endDegree = circleData.getEndDegree();
			if (degree >= startDegree && degree < endDegree
					&& !circleData.isSelected()) {
				listener.onInsidePiece(i);
				mLastSelected = i;
				data.get(i).setSelected(true);
				break;
			}
		}
	}

	private boolean isInsideCircle(float x, float y) {
		double distance = Math.sqrt((Math.pow(x - mCenterX, 2) + Math.pow(y
				- mCenterY, 2)));
		if (distance > mRadius) {
			return false;
		}
		return true;
	}

	private double getDegreeTouch(float x, float y) {
		float k = x - mCenterX;
		float d = y - mCenterY;

		double degree = Math.atan2(k, d);
		degree = Math.toDegrees(degree);

		if (degree > 0) {
			double extendDegree = 180 - degree;
			degree = -180 - extendDegree;
		}
		degree = Math.abs(degree);
		degree -= 90;

		if (degree < 0)
			degree = 360 - (90 + degree);
		return degree;
	}

}
