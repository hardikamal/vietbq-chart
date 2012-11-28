package com.zoostudio.chart.util;

import java.util.Comparator;

import com.zoostudio.bean.CircleData;
import com.zoostudio.bean.OtherData;

public class ChartDataCompator implements Comparator<CircleData> {

	@Override
	public int compare(CircleData arg0, CircleData arg1) {
		if (arg0 instanceof OtherData) {
			return -1;
		} else if (arg1 instanceof OtherData) {
			return 1;
		} else if (arg0.getPercent() > arg1.getPercent()) {
			return 1;
		} else if (arg0.getPercent() < arg1.getPercent()) {
			return -1;
		}
		return 0;
	}

}
