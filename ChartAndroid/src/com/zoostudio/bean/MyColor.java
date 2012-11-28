package com.zoostudio.bean;

import android.graphics.Color;

public class MyColor {
	public float hsv[] = new float[3];
	
	public MyColor(float hue, float sat, float value) {
		hsv[0] = hue;
		hsv[1] = sat;
		hsv[2] = value;
	}
	
	public int getColor(){
		return Color.HSVToColor(hsv);
	}
}
