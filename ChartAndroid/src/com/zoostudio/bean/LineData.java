package com.zoostudio.bean;

public class LineData extends ChartData {
	private float value;
	private String title;
	private int mColor;

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public LineData(float value, String title) {
		this.value = value;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getColor() {
		return mColor;
	};

	public void setColor(int color) {
		mColor = color;
	}
}
