package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.zoostudio.bean.ItemChart;
import com.zoostudio.bean.LineData;
import com.zoostudio.chart.exception.InvalidSeriesException;

public class LineChartView extends RelativeLayout implements
		OnAnimationDrawFinish {
	private LineChartControllerView controllerLineChart;
	private ArrayList<ArrayList<ComponentChartView<?>>> componentsChart;
	private ArrayList<PathLineView> linesChart;

	public LineChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineChartView(Context context) {
		super(context);
	}

	@SuppressWarnings("unchecked")
	public void setData(ArrayList<ItemChart> data) {
		LineChart lineChart = new LineChart(LineChart.TYPE.DAY);

		ArrayList<LineData> dataLineChart = new ArrayList<LineData>();
		dataLineChart.add(new LineData(1500, "01/12"));
		dataLineChart.add(new LineData(200, "02/12"));
		dataLineChart.add(new LineData(500, "03/12"));
		dataLineChart.add(new LineData(1500, "04/12"));
		dataLineChart.add(new LineData(1500, "05/12"));
		dataLineChart.add(new LineData(200, "06/12"));
		dataLineChart.add(new LineData(500, "07/12"));
		dataLineChart.add(new LineData(1500, "08/12"));
		dataLineChart.add(new LineData(200, "09/12"));
		dataLineChart.add(new LineData(500, "10/12"));
		dataLineChart.add(new LineData(600, "11/12"));
		dataLineChart.add(new LineData(850, "12/12"));
		dataLineChart.add(new LineData(2500, "13/12"));
		dataLineChart.add(new LineData(1520, "14/12"));
		dataLineChart.add(new LineData(1500, "15/12"));
		dataLineChart.add(new LineData(200, "16/12"));
		dataLineChart.add(new LineData(420, "17/12"));
		dataLineChart.add(new LineData(180, "18/12"));
		dataLineChart.add(new LineData(850, "19/12"));
		dataLineChart.add(new LineData(3200, "20/12"));
		dataLineChart.add(new LineData(2900, "21/12"));

		ArrayList<LineData> data1LineChart = new ArrayList<LineData>();
		data1LineChart.add(new LineData(1240, "12/01"));
		data1LineChart.add(new LineData(352, "12/02"));
		data1LineChart.add(new LineData(135, "12/03"));
		data1LineChart.add(new LineData(875, "12/04"));
		data1LineChart.add(new LineData(1500, "12/05"));
		data1LineChart.add(new LineData(200, "12/06"));
		data1LineChart.add(new LineData(500, "12/07"));
		data1LineChart.add(new LineData(1500, "12/08"));
		data1LineChart.add(new LineData(200, "12/09"));
		data1LineChart.add(new LineData(420, "12/10"));
		data1LineChart.add(new LineData(180, "12/11"));
		data1LineChart.add(new LineData(850, "12/12"));
		data1LineChart.add(new LineData(2870, "12/13"));
		data1LineChart.add(new LineData(2900, "12/14"));
		data1LineChart.add(new LineData(1500, "12/15"));
		data1LineChart.add(new LineData(200, "12/16"));
		data1LineChart.add(new LineData(345, "12/17"));
		data1LineChart.add(new LineData(143, "12/18"));
		data1LineChart.add(new LineData(321, "12/19"));
		data1LineChart.add(new LineData(762, "12/20"));
		data1LineChart.add(new LineData(1287, "12/21"));

		try {
			lineChart.setSeries(dataLineChart, data1LineChart);
			controllerLineChart = new LineChartControllerView(getContext(),
					lineChart);
			View bg = new View(getContext());
			bg.setBackgroundColor(Color.WHITE);
			addView(bg);
			componentsChart = controllerLineChart.getComponents();
			linesChart = controllerLineChart.getLines();
			for (PathLineView line : linesChart) {
				addView(line);
			}
			int n = 0;
			for (ArrayList<ComponentChartView<?>> item : componentsChart) {
				n = item.size();
				for (int i = 0; i < n; i++) {
					if (i < n - 1) {
						item.get(i).setOnAnimationFinishLitener(
								item.get(i + 1), i);
					}
				}

				for (int i = 1; i < n; i += 2) {
					addView(item.get(i));
				}

				for (int i = 0; i < n; i += 2) {
					addView(item.get(i));
				}
			}
			addView(controllerLineChart);

			int m = componentsChart.size() - 1;
			for (int i = 0; i <= m; i++) {
				componentsChart.get(i).get(0).onDrawFinishLitener();
			}

			componentsChart.get(m).get(n - 1)
					.setOnDrawChartFinishListener(this);

		} catch (InvalidSeriesException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish() {
		int n = componentsChart.get(0).size() * 2;
		for (int i = 0; i < n; i++) {
			removeViewAt(3);
		}
		for (PathLineView line : linesChart) {
			line.setVisibility(View.VISIBLE);
		}
	}
}
