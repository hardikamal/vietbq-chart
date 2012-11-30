package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;

import com.zoostudio.bean.CircleNodeData;
import com.zoostudio.bean.LineData;
import com.zoostudio.bean.MyColor;
import com.zoostudio.bean.PaddingChart;
import com.zoostudio.bean.PieceLineData;
import com.zoostudio.bean.SeriesX;
import com.zoostudio.bean.SeriesY;
import com.zoostudio.chart.util.ColorUtil;

@SuppressLint("ViewConstructor")
public class LineChartControllerView extends View {
	private LineChart chart;
	private final static int MAX_LINE = 6;
	private final static int MAX_SERIES_X = 6;
	private float mMaxValue;
	private float mMinValue;
	private float mNumberStep;
	private float mNumberLine;
	private LineChartConfig chartConfig;
	private float mOrginX, mOrginY;

	private ArrayList<LineData> chartData;
	private ArrayList<SeriesX> mSeriesX;
	private ArrayList<SeriesY> mSeriesY;

	private int mStartOffset;
	private int mEndOffset;

	private Handler handler;
	private final static float STEP[] = { 1f, 5f, 10f, 15f, 20f, 25f, 50f,
			100f, 200f, 500f, 1000f, 5000f, 10000f, 50000f, 100000f, 500000f,
			1000000f, 5000000f, 1000000000f, 5000000000f };
	private ArrayList<ArrayList<ComponentChartView<?>>> chartSeriesComponents;
	private ArrayList<LineData>[] dataSeries;
	private ArrayList<PathLineView> lineViews;

	public LineChartControllerView(Context context, LineChart lineChart) {
		super(context);
		chart = lineChart;
		initChart();
	}

	private void initChart() {

		handler = new Handler();
		chartConfig = new LineChartConfig();
		chartData = chart.getChartData();
		getOffset();
		dataSeries = chart.getChartDataSeries();
		getMaxMin();
		float total = mMaxValue - mMinValue;
		// Tinh step cua truc Y
		for (float step : STEP) {
			if (total / step < MAX_LINE) {
				mNumberStep = step;
				mNumberLine = ((int) total / (int) step);
				System.out.println("" + Math.ceil(total));
				int a = (int) Math.ceil(total);
				int b = (int) Math.ceil(step);
				if (a % b != 0)
					mNumberLine++;
				break;
			}
		}

		chart.numberLine = mNumberLine;
		chart.numberStep = mNumberStep;
		getSeriesX();
		getSeriesY();
		// Tinh step cua truc X

		chart.setStartOffset(mStartOffset);
		chart.setEndOffset(mEndOffset);

		chart.seriesY = mSeriesY;
		chart.seriesX = mSeriesX;

		genViewPieceLine();

		chart.chartConfig = chartConfig;
	}

	private void getSeriesY() {
		mSeriesY = new ArrayList<SeriesY>();
		int startStep = (int) mNumberStep;
		String strStep;
		Paint painText = new Paint();
		painText.setTextSize(chartConfig.fontSize);
		painText.setTypeface(Typeface.SANS_SERIF);

		Rect bounds = new Rect();
		float paddingLeft = 0;
		for (int i = 1; i <= mNumberLine; i++) {
			strStep = String.valueOf(startStep);
			painText.getTextBounds(strStep, 0, strStep.length(), bounds);
			SeriesY seriesY = new SeriesY();
			seriesY.title = strStep;
			seriesY.height = bounds.height();
			seriesY.width = bounds.width() + 6;
			seriesY.padding = seriesY.width - 1;
			if (paddingLeft < seriesY.width) {
				paddingLeft = seriesY.width;
			}
			mSeriesY.add(seriesY);
			startStep += (int) mNumberStep;
		}
		chartConfig.paddingLeft = (float) Math.ceil(paddingLeft);
	}

	private void getOffset() {
		if (chartData.size() > MAX_SERIES_X) {
			mEndOffset = chartData.size();
			mStartOffset = mEndOffset - MAX_SERIES_X;
		} else {
			mStartOffset = 0;
			mEndOffset = chartData.size();
		}
	}

	private void getSeriesX() {
		mSeriesX = new ArrayList<SeriesX>();
		Paint painText = new Paint();
		painText.setTextSize(chartConfig.fontSize);
		painText.setTypeface(Typeface.SANS_SERIF);
		Rect bounds = new Rect();
		String strValue;
		float paddingBottom = 0;

		for (int i = 0; i < chartData.size(); i++) {
			SeriesX seriesX = new SeriesX();
			strValue = chartData.get(i).getTitle();
			painText.getTextBounds(strValue, 0, strValue.length(), bounds);
			seriesX.title = chartData.get(i).getTitle();
			seriesX.width = bounds.width();
			seriesX.height = bounds.height() + 6;
			seriesX.centerX = bounds.centerX();
			seriesX.padding = 3;
			if (paddingBottom < seriesX.height) {
				paddingBottom = seriesX.height;
			}
			mSeriesX.add(seriesX);
		}
		chartConfig.paddingBottom = paddingBottom;
	}

	private void genViewPieceLine() {
		ArrayList<MyColor> colours = ColorUtil.getColor(dataSeries.length);
		lineViews = new ArrayList<PathLineView>();
		chartSeriesComponents = new ArrayList<ArrayList<ComponentChartView<?>>>();
		int k = 0;
		int n;
		for (int j = 0; j < dataSeries.length; j++) {
			n = mEndOffset - mStartOffset;
			ArrayList<LineData> chartData = dataSeries[j];
			PathLineView lineView = new PathLineView(getContext(),
					colours.get(j));
			lineView.setConfigLine(new PaddingChart(chartConfig.paddingLeft,
					chartConfig.paddingTop, chartConfig.paddingBottom,
					chartConfig.paddingRight), mNumberLine, mNumberStep,n);
			lineView.setData(chartData.subList(mStartOffset, mEndOffset));
			lineView.setVisibility(View.INVISIBLE);
			
			ArrayList<ComponentChartView<?>> arrayComponents = new ArrayList<ComponentChartView<?>>();
			k = 0;
			for (int i = mStartOffset; i < mEndOffset - 1; i++, k++) {
				final PieceLineData data = new PieceLineData();
				data.indexStart = k;
				data.indexEnd = k + 1;
				data.valueStart = chartData.get(i).getValue();
				data.valueEnd = chartData.get(i + 1).getValue();
				data.step = mNumberStep;
				data.paddingLeft = chartConfig.paddingLeft;
				data.paddingBottom = chartConfig.paddingBottom;
				data.paddingRight = chartConfig.paddingRight;
				data.paddingTop = chartConfig.paddingTop;
				data.numberPieceXAxis = n;
				data.numberLine = mNumberLine;
				final PieceLineChartView piece = new PieceLineChartView(
						getContext(), handler, colours.get(j));
				piece.setData(data);

				final CircleNodeView nodeView = new CircleNodeView(
						getContext(), handler, colours.get(j));
				final CircleNodeData nodeData = new CircleNodeData();
				nodeData.indexStart = k;
				nodeData.valueStart = chartData.get(i).getValue();
				nodeData.step = mNumberStep;
				nodeData.paddingLeft = chartConfig.paddingLeft;
				nodeData.paddingBottom = chartConfig.paddingBottom;
				nodeData.paddingRight = chartConfig.paddingRight;
				nodeData.paddingTop = chartConfig.paddingTop;
				nodeData.numberPieceXAxis = n;
				nodeData.numberLine = mNumberLine;
				nodeView.setData(nodeData);

				arrayComponents.add(nodeView);
				arrayComponents.add(piece);
			}

			final CircleNodeView nodeView = new CircleNodeView(getContext(),
					handler, colours.get(j));
			final CircleNodeData nodeData = new CircleNodeData();
			nodeData.indexStart = k;
			nodeData.valueStart = chartData.get(mEndOffset - 1).getValue();
			nodeData.step = mNumberStep;
			nodeData.paddingLeft = chartConfig.paddingLeft;
			nodeData.paddingBottom = chartConfig.paddingBottom;
			nodeData.paddingRight = chartConfig.paddingRight;
			nodeData.paddingTop = chartConfig.paddingTop;
			nodeData.numberPieceXAxis = n;
			nodeData.numberLine = mNumberLine;
			nodeView.setData(nodeData);
			arrayComponents.add(nodeView);

			chartSeriesComponents.add(arrayComponents);
			lineViews.add(lineView);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mOrginX = chartConfig.paddingLeft;
		mOrginY = h - chartConfig.paddingBottom;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		chart.setDimen(getWidth(), getHeight());
		chart.drawChart(canvas);
	}

	private void getMaxMin() {
		mMaxValue = 0;
		mMinValue = Float.MAX_VALUE;

		float maxSeries;
		float minSeries;

		for (int i = 0, n = dataSeries.length; i < n; i++) {
			ArrayList<LineData> data = dataSeries[i];
			maxSeries = data.get(mStartOffset).getValue();
			minSeries = data.get(mStartOffset).getValue();

			for (int j = mStartOffset; j < mEndOffset; j++) {
				if (maxSeries < data.get(j).getValue()) {
					maxSeries = data.get(j).getValue();
				}

				if (minSeries > data.get(j).getValue()) {
					minSeries = data.get(j).getValue();
				}
			}
			if (mMaxValue < maxSeries) {
				mMaxValue = maxSeries;
			}
			if (mMinValue > minSeries) {
				mMinValue = minSeries;
			}
		}
	}

	public ArrayList<ArrayList<ComponentChartView<?>>> getComponents() {
		return chartSeriesComponents;
	}

	public ArrayList<PathLineView> getLines() {
		return lineViews;
	}
}
