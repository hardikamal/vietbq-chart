package com.zoostudio.chart.linechart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

import com.zoostudio.bean.LineData;

@SuppressLint("SimpleDateFormat")
public class LineChartUtil {

	public static void validSeriesTypeDay(float[] maxmin,
			ArrayList<LineData>... series) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date dateFrom = new Date();
		Date dateTo = new Date();
		String strDateFrom, strDateTo;
		Calendar calendar = Calendar.getInstance();
		int inc;
		float max = 0;
		float min = Float.MAX_VALUE;

		float maxSeries;
		float minSeries;

		for (int i = 0, n = series.length; i < n; i++) {
			ArrayList<LineData> data = series[i];
			maxSeries = data.get(0).getValue();
			minSeries = data.get(0).getValue();
			
			for (int j = 0, m = data.size(); j < m - 1; j++) {
				try {
					
					if (maxSeries < data.get(j).getValue()){
						maxSeries = data.get(j).getValue();
					}
					
					if(minSeries > data.get(j).getValue()){
						minSeries = data.get(j).getValue();
					}
					
					strDateFrom = data.get(j).getTitle();
					strDateTo = data.get(j + 1).getTitle();
					dateFrom = dateFormat.parse(strDateFrom);
					dateTo = dateFormat.parse(strDateTo);
					inc = autoInsertDay(dateFormat, data, calendar, dateFrom,
							dateTo, j);
					j += inc;
					m = data.size();
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
			
			if(max < maxSeries){
				max = maxSeries;
			}
			if(min > minSeries){
				min = minSeries;
			}
		}
		
		System.out.println("MAX =" + max);
		System.out.println("MIN =" + min);
	}

	private static int autoInsertDay(SimpleDateFormat sdf,
			ArrayList<LineData> data, Calendar calendar, Date dateFrom,
			Date dateTo, int index) {
		int inc = 0;
		Date dateCheck;
		calendar.setTime(dateFrom);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		dateCheck = calendar.getTime();
		if (dateCheck.compareTo(dateTo) < 0) {
			index++;
			data.add(index, new LineData(0f, sdf.format(dateCheck.getTime())));
			inc++;
			inc += autoInsertDay(sdf, data, calendar, dateCheck, dateTo, index);
		}
		return inc;
	}

	public static ArrayList<LineData> validSeriesTypeMonth(
			ArrayList<LineData>... series) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
		Date dateFrom = new Date();
		Date dateTo = new Date();
		String strDateFrom, strDateTo;
		Calendar calendar = Calendar.getInstance();
		int inc;
		for (int i = 0, n = series.length; i < n; i++) {
			ArrayList<LineData> data = series[i];
			for (int j = 0, m = data.size(); j < m - 1; j++) {
				try {
					strDateFrom = data.get(j).getTitle();
					strDateTo = data.get(j + 1).getTitle();
					dateFrom = dateFormat.parse(strDateFrom);
					dateTo = dateFormat.parse(strDateTo);
					inc = autoInsertMonth(dateFormat, data, calendar, dateFrom,
							dateTo, j);
					j += inc;
					m = data.size();
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static int autoInsertMonth(SimpleDateFormat sdf,
			ArrayList<LineData> data, Calendar calendar, Date dateFrom,
			Date dateTo, int index) {
		int inc = 0;
		Date dateCheck;
		calendar.setTime(dateFrom);
		calendar.add(Calendar.MONTH, 1);
		dateCheck = calendar.getTime();
		if (dateCheck.compareTo(dateTo) < 0) {
			index++;
			data.add(index, new LineData(0f, sdf.format(dateCheck.getTime())));
			inc++;
			inc += autoInsertMonth(sdf, data, calendar, dateCheck, dateTo,
					index);
		}
		return inc;
	}
}
