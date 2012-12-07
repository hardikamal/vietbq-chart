package com.zoostudio.bean;

import android.graphics.Color;

public class MyColor {
	public float hsv[] = new float[3];
	private String color;

	public MyColor(float hue, float sat, float value) {
		hsv[0] = hue;
		hsv[1] = sat;
		hsv[2] = value;
	}

	public MyColor(String item) {
		color = item;
	}

	public int getHSVColor() {
		return Color.HSVToColor(hsv);
	}

	public int getColor() {
		return Color.parseColor(color);
	}
}
