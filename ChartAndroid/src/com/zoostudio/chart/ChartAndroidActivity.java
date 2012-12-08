package com.zoostudio.chart;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zoostudio.bean.ItemChart;
import com.zoostudio.bean.LineData;
import com.zoostudio.chart.linechart.LineChartView;

public class ChartAndroidActivity extends Activity {
	/** Called when the activity is first created. */
	CircelChartView container;
	ListView legend;
	RelativeLayout chartView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.line_chart);
		testLineChart();
		// testCircleChart();
	}

	@SuppressWarnings("unchecked")
	private void testLineChart() {
		LineChartView chartView = (LineChartView) findViewById(R.id.linechart);

		ArrayList<LineData> dataLineChart = new ArrayList<LineData>();
		dataLineChart.add(new LineData(1500, "01/12"));
		dataLineChart.add(new LineData(200, "02/12"));
		dataLineChart.add(new LineData(500, "03/12"));
		dataLineChart.add(new LineData(1500, "04/12"));
		dataLineChart.add(new LineData(1600, "05/12"));
		dataLineChart.add(new LineData(1500, "01/12"));
		dataLineChart.add(new LineData(200, "02/12"));
		dataLineChart.add(new LineData(500, "03/12"));
		dataLineChart.add(new LineData(1500, "04/12"));
		dataLineChart.add(new LineData(1600, "05/12"));

		ArrayList<LineData> data1LineChart = new ArrayList<LineData>();
		data1LineChart.add(new LineData(1240, "12/01"));
		data1LineChart.add(new LineData(352, "12/02"));
		data1LineChart.add(new LineData(135, "12/03"));
		data1LineChart.add(new LineData(875, "12/04"));
		data1LineChart.add(new LineData(0, "12/04"));
		data1LineChart.add(new LineData(1240, "12/01"));
		data1LineChart.add(new LineData(352, "12/02"));
		data1LineChart.add(new LineData(135, "12/03"));
		data1LineChart.add(new LineData(875, "12/04"));
		data1LineChart.add(new LineData(0, "12/04"));
		ArrayList<String> titleLegend = new ArrayList<String>();
		titleLegend.add("Thu");
		titleLegend.add("Chi");
		chartView.setData(titleLegend,dataLineChart,data1LineChart);
	}

	private void testCircleChart() {
		container = (CircelChartView) this
				.findViewById(R.id.graphicContainer1);
		legend = (ListView) findViewById(R.id.listLegend);
		chartView = (RelativeLayout) findViewById(R.id.chart);
		container.setControl(legend, chartView);
		ArrayList<ItemChart> itemCharts = new ArrayList<ItemChart>();
		itemCharts.add(new ItemChart("An uong", 50));
		itemCharts.add(new ItemChart("Do xang", 70));
		itemCharts.add(new ItemChart("Tien nha", 143));
		itemCharts.add(new ItemChart("Tien nuoc", 120));
		itemCharts.add(new ItemChart("Tien dien", 185));
		itemCharts.add(new ItemChart("Sinh nhat", 210));
		itemCharts.add(new ItemChart("Luong", 600));
		itemCharts.add(new ItemChart("Bong da", 500));
		itemCharts.add(new ItemChart("Du lich", 15));
		itemCharts.add(new ItemChart("Mua sam", 950));
		container.setData(itemCharts);
	}
}