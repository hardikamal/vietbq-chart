package com.zoostudio.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zoostudio.bean.CircleData;
import com.zoostudio.chart.R;

public class ChartAdapter extends ArrayAdapter<CircleData>  {
	private LayoutInflater mInflater;

	public ChartAdapter(Context context, int textViewResourceId,
			ArrayList<CircleData> objects) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CircleData itemChart = (CircleData) this.getItem(position);
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_chart, null);
			holder.mColor = convertView.findViewById(R.id.view1);
			holder.mTitle = (TextView) convertView.findViewById(R.id.textView1);
			// Tai su dung Item Layout
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mTitle.setText(itemChart.getTitle());
		holder.mColor
				.setBackgroundColor(itemChart.getColor());

		return convertView;
	}

	private class ViewHolder {
		private View mColor;
		private TextView mTitle;
	}

}
