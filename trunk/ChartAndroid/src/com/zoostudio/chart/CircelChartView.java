package com.zoostudio.chart;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zoostudio.adapter.ChartAdapter;
import com.zoostudio.bean.CircleData;
import com.zoostudio.bean.ItemChart;
import com.zoostudio.chart.callback.IPieceCircleChartListener;
import com.zoostudio.chart.callback.OnItemChartSelectListener;
import com.zoostudio.chart.util.DataUtil;

public class CircelChartView extends LinearLayout implements
		IPieceCircleChartListener {
	private ListView mListView;
	private ChartAdapter mAdapter;
	private float degree_offset;
	private float mSweepAngle;
	private RelativeLayout parentChart;

	private OnItemChartSelectListener listener;
	private int mBorderColor;
	private float mBorderWidth;
	private long mDurationanim;

	private CircelChartConfig chartConfig;
	private int mBorderColorSelected;

	public CircelChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mAdapter = new ChartAdapter(context, R.id.textView1,
				new ArrayList<CircleData>());
		// Load default att
		final Resources res = getResources();
		final int defaultBorderColor = Color.WHITE;
		final float defaultBorderWidth = res
				.getDimension(R.dimen.default_border_line);
		final int defaultDurationAnim = 200;
		final int defaultBorderColorSelected = Color.WHITE;

		// Retrieve styles attributes
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ChartPiece);
		mBorderColor = a.getColor(R.styleable.ChartPiece_chartBorderColor,
				defaultBorderColor);
		mBorderWidth = a.getDimension(R.styleable.ChartPiece_chartBorderWidth,
				defaultBorderWidth);
		mDurationanim = a.getInt(R.styleable.ChartPiece_chartAnimDuration,
				defaultDurationAnim);
		mBorderColorSelected = a.getColor(
				R.styleable.ChartPiece_chartBorderSelectedColor,
				defaultBorderColorSelected);

		chartConfig = new CircelChartConfig(mBorderColor, mBorderColorSelected,
				mBorderWidth, mDurationanim);
	}

	public void setControl(ListView legend, RelativeLayout chartView) {
		mListView = legend;
		parentChart = chartView;
		mListView.setAdapter(mAdapter);
	}

	public void setData(ArrayList<ItemChart> dataParam) {
		ArrayList<CircleData> data = DataUtil.genDataForChart(dataParam);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		degree_offset = 180;

		PieceCircleChartView lastPiece = null;
		ObserverAnimation firstItem = null;
		for (int i = 0, n = data.size(); i < n; i++) {
			mSweepAngle = (data.get(i).getPercent() * 360) / 100;
			if (i == n - 1) {
				if ((degree_offset + mSweepAngle) / 360 != 0) {
					degree_offset = 540 - mSweepAngle;
				}
			}
			data.get(i).setDegree(degree_offset - 180, mSweepAngle);
			PieceCircleChartView piece = new PieceCircleChartView(getContext(),
					mDurationanim);
			piece.setData(i, data.get(i), degree_offset, this, chartConfig);
			piece.setLayoutParams(params);
			piece.setVisibility(View.INVISIBLE);
			parentChart.addView(piece);

			if (null != lastPiece) {
				lastPiece.setObserverAnimation(piece);
			} else {
				firstItem = piece;
			}

			lastPiece = piece;
			degree_offset += mSweepAngle;
		}

		CircleControllerView controllerView = new CircleControllerView(
				getContext(), this);
		controllerView.setData(data);
		parentChart.addView(controllerView);
		for (CircleData item : data) {
			mAdapter.add(item);
		}
		mAdapter.notifyDataSetChanged();
		firstItem.onFinish();
	}

	public void setOnItemChartSelectListener(OnItemChartSelectListener listener) {
		this.listener = listener;
	}

	@Override
	public void onInsidePiece(int index) {
		PieceCircleChartView view = (PieceCircleChartView) parentChart
				.getChildAt(index);
		view.selectedPiece();
		if (null != listener)
			listener.onItemSelected(index);
	}

	@Override
	public void onOutsidePiece(int index) {
		PieceCircleChartView view = (PieceCircleChartView) parentChart
				.getChildAt(index);
		view.unSelectedPiece();
	}

}
