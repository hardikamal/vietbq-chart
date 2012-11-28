package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import com.zoostudio.bean.ItemChart;
import com.zoostudio.bean.LineData;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class LineChartView extends RelativeLayout {
	private LineChartControllerView axisView;
	private ArrayList<ComponentChartView<?>> componentsChart;

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
		LineChart lineChart = new LineChart();

		ArrayList<LineData> dataLineChart = new ArrayList<LineData>();
		dataLineChart.add(new LineData(1500, "Week 1"));
		dataLineChart.add(new LineData(200, "Week 2"));
		dataLineChart.add(new LineData(500, "Week 3"));
		dataLineChart.add(new LineData(600, "Week 4"));
		dataLineChart.add(new LineData(850, "Week 8"));
		dataLineChart.add(new LineData(2500, "Week 12"));
		dataLineChart.add(new LineData(1520, "Week 15"));

		ArrayList<LineData> data1LineChart = new ArrayList<LineData>();
		dataLineChart.add(new LineData(1500, "Week 2"));
		dataLineChart.add(new LineData(300, "Week 5"));
		dataLineChart.add(new LineData(450, "Week 6"));
		dataLineChart.add(new LineData(180, "Week 8"));
		dataLineChart.add(new LineData(850, "Week 9"));
		dataLineChart.add(new LineData(3000, "Week 11"));
		dataLineChart.add(new LineData(1520, "Week 14"));

		lineChart.setSeries(dataLineChart, data1LineChart);
		axisView = new LineChartControllerView(getContext(), lineChart);
		componentsChart = axisView.getComponents();
		addView(axisView);

		for (int i = 0, n = componentsChart.size(); i < n; i++) {
			if (i < n - 1) {
				componentsChart.get(i).setOnAnimationFinishLitener(
						componentsChart.get(i + 1));
			}
		}

		componentsChart.get(0).onDrawFinishLitener();

		for (int i = 1, n = componentsChart.size(); i < n; i += 2) {
			addView(componentsChart.get(i));
		}
		for (int i = 0, n = componentsChart.size(); i < n; i += 2) {
			addView(componentsChart.get(i));
		}
	}

}
