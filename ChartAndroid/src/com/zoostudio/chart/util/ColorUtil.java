package com.zoostudio.chart.util;

import java.util.ArrayList;

import android.content.Context;

import com.zoostudio.bean.MyColor;
import com.zoostudio.chart.R;

public class ColorUtil {
	public static ArrayList<MyColor> getColor(int numberOfColor) {
		int count = 0;
		ArrayList<MyColor> alreadyChoosenColors = new ArrayList<MyColor>();
		float hue = 0;
		float value = 1f;
		float sat = 1f;
		COLOR: for (int k = 0; k <= 1; k++) {
			for (int i = 0; i <= 3; i++) {
				for (int j = 0; j <= 8; j++) {
					if (count == numberOfColor)
						break COLOR;
					MyColor color = new MyColor(hue, sat, value);
					alreadyChoosenColors.add(color);
					count ++;
					hue += 36;
				}
				hue = 0;
				value -= 0.25f;
			}
			hue = 0;
			value = 1f;
			sat -=0.2f;
		}
		return alreadyChoosenColors;
	}
	public static ArrayList<MyColor> getColorsByDefinded(int numberOfColor,Context context){
		ArrayList<MyColor> alreadyChoosenColors = new ArrayList<MyColor>();
		String[] cl = context.getResources().getStringArray(R.array.colours);
		for(String item : cl){
			MyColor color = new MyColor(item);
			alreadyChoosenColors.add(color);
		}
		return alreadyChoosenColors;
		
	}
}
