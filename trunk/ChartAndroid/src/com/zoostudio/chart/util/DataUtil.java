package com.zoostudio.chart.util;

import java.util.ArrayList;
import java.util.Collections;

import com.zoostudio.bean.CircleData;
import com.zoostudio.bean.ItemChart;
import com.zoostudio.bean.MyColor;
import com.zoostudio.bean.OtherData;

public class DataUtil {
	private static final float MIN_PERCENT = 3;

	public static ArrayList<CircleData> genDataForChart(
			ArrayList<ItemChart> data) {

		float mTotalValue = 0;
		ArrayList<CircleData> mChartData = new ArrayList<CircleData>();
		for (ItemChart item : data) {
			mTotalValue += item.getValue();
		}

		int index = 0;
		float percent = 0;

		OtherData otherData = new OtherData();
		ArrayList<MyColor> colours = ColorUtil.getColor(data.size());
		boolean mHasOther = false;
		for (ItemChart item : data) {
			percent = (item.getValue() / mTotalValue) * 100;
			if (percent < MIN_PERCENT && !otherData.isFull()) {
				mHasOther = true;
				otherData.addChartData(new CircleData(percent, 1, item
						.getTitle(), index));
			} else {
				mChartData.add(new CircleData(percent, colours.get(index)
						.getColor(), item.getTitle(), index));
				index++;
			}
		}

		if (mHasOther) {
			otherData.setColor(colours.get(index).getColor());
			mChartData.add(otherData);
		}
		Collections.sort(mChartData, new ChartDataCompator());
		return mChartData;
	}
}
