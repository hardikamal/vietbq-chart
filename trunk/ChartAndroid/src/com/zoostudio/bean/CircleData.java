package com.zoostudio.bean;

public class CircleData extends ChartData {
	protected float mPercent;
	protected int mColor;
	protected String mTitle;
	protected int mIndex;
	private float mStartDegree;
	private float mEndDegree;
	private boolean isSelected;
	
	public CircleData() {
		isSelected = false;
	}
	

	public CircleData(float mPercent, int color, String title, int id) {
		super(id);
		isSelected = false;
		this.mPercent = mPercent;
		this.mColor = color;
		this.mId = id;
		this.mTitle = title;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public int getColor() {
		return mColor;
	}

	public int getId() {
		return mId;
	}

	public float getPercent() {
		return mPercent;
	}

	public String toString() {
		return "" + mPercent;
	}

	public String getTitle() {
		return this.mTitle;
	}

	public void setDegree(float startDegree,float mSweepAngle) {
		mStartDegree = startDegree;
		mEndDegree = mStartDegree + mSweepAngle;
	}

	
	public float getStartDegree() {
		return mStartDegree;
	}
	
	public float getEndDegree() {
		return mEndDegree;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
