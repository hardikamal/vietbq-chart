package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import com.zoostudio.bean.ItemChart;
import com.zoostudio.bean.LineData;
import com.zoostudio.chart.exception.InvalidSeriesException;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class LineChartView extends RelativeLayout {
	private LineChartControllerView axisView;
	private ArrayList<ArrayList<ComponentChartView<?>>> componentsChart;

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
		dataLineChart.add(new LineData(1500, "12/8"));
		dataLineChart.add(new LineData(200, "12/9"));
		dataLineChart.add(new LineData(500, "12/10"));
		dataLineChart.add(new LineData(600, "12/11"));
		dataLineChart.add(new LineData(850, "12/12"));
		dataLineChart.add(new LineData(2500, "12/13"));
		dataLineChart.add(new LineData(1520, "12/14"));

		ArrayList<LineData> data1LineChart = new ArrayList<LineData>();
		data1LineChart.add(new LineData(800, "12/8"));
		data1LineChart.add(new LineData(350, "12/9"));
		data1LineChart.add(new LineData(420, "12/10"));
		data1LineChart.add(new LineData(180, "12/11"));
		data1LineChart.add(new LineData(850, "12/12"));
		data1LineChart.add(new LineData(3200, "12/13"));
		data1LineChart.add(new LineData(2900, "12/14"));

		try {
			lineChart.setSeries(dataLineChart, data1LineChart);
			axisView = new LineChartControllerView(getContext(), lineChart);
			componentsChart = axisView.getComponents();
			addView(axisView);

			for (ArrayList<ComponentChartView<?>> item : componentsChart) {
				for (int i = 0, n = item.size(); i < n; i++) {
					if (i < n - 1) {
						item.get(i)
								.setOnAnimationFinishLitener(item.get(i + 1));
					}
				}

				for (int i = 1, n = item.size(); i < n; i += 2) {
					addView(item.get(i));
				}
				
				for (int i = 0, n = item.size(); i < n; i += 2) {
					addView(item.get(i));
				}
			}
			
			for (int i = 0, n = componentsChart.size(); i < n; i++) {
				componentsChart.get(i).get(0).onDrawFinishLitener();
			}
			
//			componentsChart.get(0).get(0).onDrawFinishLitener();
		} catch (InvalidSeriesException e) {
			e.printStackTrace();
		}
	}

}
