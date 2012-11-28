package com.zoostudio.bean;

import java.util.ArrayList;

public class OtherData extends CircleData {
	private final static float MAX_PERCENT = 30;

	public OtherData() {
		this.mPercent = 0;
		this.otherData = new ArrayList<CircleData>();
		this.mTitle = "Other";
	}

	public OtherData(float mPercent, int color, String title,int id) {
		super(mPercent, color, title,id);
		this.mPercent = 0;
		this.otherData = new ArrayList<CircleData>();
		
	}

	public void setColor(int color) {
		this.mColor = color;
	}

	private ArrayList<CircleData> otherData;

	public boolean isFull() {
		if (mPercent >= MAX_PERCENT)
			return true;
		else
			return false;
	}

	public void addChartData(CircleData item) {
		this.otherData.add(item);
		mPercent += item.getPercent();
	}

	public CircleData getChartData(int position) {
		return this.otherData.get(position);
	}
}
