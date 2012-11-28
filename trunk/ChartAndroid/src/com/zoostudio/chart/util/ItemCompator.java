package com.zoostudio.chart.util;

import java.util.Comparator;

import com.zoostudio.bean.ItemChart;

public class ItemCompator implements Comparator<ItemChart> {

	@Override
	public int compare(ItemChart arg0, ItemChart arg1) {
		if (arg0.getValue() > arg1.getValue()) {
			return 1;
		} else if (arg0.getValue() < arg1.getValue()) {
			return -1;
		}
		return 0;
	}
}
