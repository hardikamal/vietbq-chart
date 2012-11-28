package com.zoostudio.chart;

public class CircelChartConfig {

	private int mBorderColor;
	private float mBorderWidth;
	private long mDurationanim;
	private int mBoderColorSelected;

	
	public CircelChartConfig(int borderColor, int boderColorSelected,float borderWidth, long duration) {
		mBorderColor = borderColor;
		mBorderWidth = borderWidth;
		mDurationanim = duration;
		mBoderColorSelected =  boderColorSelected;
	}

	public int getBorderColor() {
		return mBorderColor;
	}
	
	public int getBoderColorSelected() {
		return mBoderColorSelected;
	}
	

	public float getBorderWidth() {
		return mBorderWidth;
	}

	public long getDurationanim() {
		return mDurationanim;
	}
	
	public void setBorderColor(int borderColor) {
		this.mBorderColor = borderColor;
	}
	
	public void setmDurationanim(long durationanim) {
		this.mDurationanim = durationanim;
	}
	
	public void setmBorderWidth(float borderWidth) {
		this.mBorderWidth = borderWidth;
	}
}
