package com.zoostudio.bean;

public class ItemChart {
	private String mTitle;
	private float mValue;

	public ItemChart(String title, float value) {
		this.mTitle = title;
		this.mValue = value;
	}


	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public float getValue() {
		return this.mValue;
	}

}
