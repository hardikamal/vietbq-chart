package com.zoostudio.chart.linechart;

import android.graphics.Color;

public class LineChartConfig {
	float paddingLeft;
	int paddingTop;
	int paddingRight;
	float paddingBottom;

	int colorAxisX;
	int colorAxisY;

	int colorLine;
	int colorTitle;

	int colorSeriesX;
	int colorSeriesY;

	int fontSize;
	int fontColor;

	public LineChartConfig() {
		colorAxisX = Color.BLACK;
		colorAxisY = Color.BLACK;
		colorSeriesY = Color.BLACK;
		colorSeriesX = Color.BLACK;
		colorLine = Color.parseColor("#C1C1C1");
		paddingBottom = 15;
		paddingLeft = 40;
		paddingTop = 15;
		paddingRight = 15;
		fontColor = Color.BLACK;
		fontSize = 12;
	}
}
