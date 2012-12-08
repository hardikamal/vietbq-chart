package com.zoostudio.bean;

public class LegendItem {
	private String title;
	private int color;

	public LegendItem(String title, int color) {
		this.title = title;
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public String getTitle() {
		return title;
	}
}
