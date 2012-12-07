package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.zoostudio.bean.CircleNodeData;
import com.zoostudio.bean.LineData;
import com.zoostudio.bean.MyColor;
import com.zoostudio.bean.PaddingChart;
import com.zoostudio.bean.PieceLineData;
import com.zoostudio.bean.SeriesX;
import com.zoostudio.bean.SeriesY;
import com.zoostudio.chart.R;
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

	public enum DIRECTION {
		RIGHT, LEFT;
	}

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
	private int mAction;
	private float yMove;
	private float xMove;
	private float startOffsetX;
	private float mDistanceX;
	private DIRECTION mDriection;
	private float mLastX;
	private int totalSeriesX;

	private float speedDraw;
	private float fontSize;
	private float borderLine;
	private float[] offsetExtend;
	private int mTotalNode;
	private float mDistanceLine;
	private float startOffsetLineX;

	public LineChartControllerView(Context context, LineChart lineChart) {
		super(context);
		chart = lineChart;
		fontSize = getResources().getDimensionPixelOffset(
				R.dimen.defalut_font_size);
		handler = new Handler();
		offsetExtend = new float[1];
		chartConfig = new LineChartConfig();
		chartConfig.fontSize = fontSize;
		chartData = chart.getChartData();
		totalSeriesX = chartData.size();
		getOffset();
		dataSeries = chart.getChartDataSeries();
		initChart();
	}

	private void initChart() {

		genChartConfig();

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

	private void genChartConfig() {
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
		for (int i = 0; i < mNumberLine; i++) {
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
			mEndOffset = chartData.size() - 1;
			mStartOffset = mEndOffset - (MAX_SERIES_X + 1);
			mTotalNode = MAX_SERIES_X;
		} else {
			mStartOffset = 0;
			mEndOffset = chartData.size();
			mTotalNode = mEndOffset - mStartOffset;
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
		for (int j = 0; j < dataSeries.length; j++) {
			ArrayList<LineData> chartData = dataSeries[j];
			PathLineView lineView = new PathLineView(getContext(),
					colours.get(j));
			lineView.setConfigLine(new PaddingChart(chartConfig.paddingLeft,
					chartConfig.paddingTop, chartConfig.paddingBottom,
					chartConfig.paddingRight), mNumberLine, mNumberStep,
					mTotalNode);
			lineView.setData(chartData.subList(mStartOffset, mEndOffset + 1));
			lineView.setVisibility(View.INVISIBLE);

			ArrayList<ComponentChartView<?>> arrayComponents = new ArrayList<ComponentChartView<?>>();
			for (int i = mStartOffset; i < mEndOffset; i++) {
				final PieceLineData data = new PieceLineData();
				data.indexStart = i;
				data.indexEnd = i + 1;
				data.valueStart = chartData.get(i).getValue();
				data.valueEnd = chartData.get(i + 1).getValue();
				data.step = mNumberStep;
				data.paddingLeft = chartConfig.paddingLeft;
				data.paddingBottom = chartConfig.paddingBottom;
				data.paddingRight = chartConfig.paddingRight;
				data.paddingTop = chartConfig.paddingTop;
				data.numberPieceXAxis = mTotalNode;
				data.numberLine = mNumberLine;
				data.endOffset = mEndOffset;
				final PieceLineChartView piece = new PieceLineChartView(
						getContext(), handler, colours.get(j));
				piece.setData(data);

				final CircleNodeView nodeView = new CircleNodeView(
						getContext(), handler, colours.get(j));
				final CircleNodeData nodeData = new CircleNodeData();
				nodeData.indexStart = i;
				nodeData.valueStart = chartData.get(i).getValue();
				nodeData.step = mNumberStep;
				nodeData.paddingLeft = chartConfig.paddingLeft;
				nodeData.paddingBottom = chartConfig.paddingBottom;
				nodeData.paddingRight = chartConfig.paddingRight;
				nodeData.paddingTop = chartConfig.paddingTop;
				nodeData.numberPieceXAxis = mTotalNode;
				nodeData.numberLine = mNumberLine;
				nodeData.endOffset = mEndOffset;
				nodeView.setData(nodeData);

				arrayComponents.add(nodeView);
				arrayComponents.add(piece);
			}

			final CircleNodeView nodeView = new CircleNodeView(getContext(),
					handler, colours.get(j));
			final CircleNodeData nodeData = new CircleNodeData();
			nodeData.indexStart = mEndOffset;
			nodeData.valueStart = chartData.get(mEndOffset).getValue();
			nodeData.step = mNumberStep;
			nodeData.paddingLeft = chartConfig.paddingLeft;
			nodeData.paddingBottom = chartConfig.paddingBottom;
			nodeData.paddingRight = chartConfig.paddingRight;
			nodeData.paddingTop = chartConfig.paddingTop;
			nodeData.numberPieceXAxis = mTotalNode;
			nodeData.numberLine = mNumberLine;
			nodeData.endOffset = mEndOffset;
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
		chart.setTotalNodeX(mTotalNode);
		chart.setDimen(w, h);
		chart.initVariables();
		chart.updateSeriesXMoveRight(w - chartConfig.paddingRight
				- chart.getDistanceSeriesX());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		chart.drawChart(canvas);
	}

	private void getMaxMin() {
		mMaxValue = 0;
		mMinValue = Float.MAX_VALUE;

		float maxSeries;
		float minSeries;
		System.out.print("Title Pre Start = "
				+ dataSeries[0].get(mStartOffset).getTitle());
		System.out.println("| Title Pre END = "
				+ dataSeries[0].get(mEndOffset).getTitle());
		for (int i = 0, n = dataSeries.length; i < n; i++) {
			ArrayList<LineData> data = dataSeries[i];
			maxSeries = data.get(mStartOffset).getValue();
			minSeries = data.get(mStartOffset).getValue();

			for (int j = mStartOffset; j <= mEndOffset; j++) {
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mAction = event.getAction();
		switch (mAction) {
		case MotionEvent.ACTION_DOWN:
			mLastX = event.getX();
			System.out.println("X= " + mLastX);
			mDistanceX = mLastX - chart.getFirstX();
			return true;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getX();
			startOffsetX = xMove - mDistanceX;
			if (xMove > mLastX) {
				mLastX = xMove;
				chart.updateSeriesXMoveRight(startOffsetX);
				regenChartConfig();
				lineViews.get(0).updateLine(chart.getOffsetStartX());
				lineViews.get(1).updateLine(chart.getOffsetStartX());
				invalidate();
				return true;

			} else if (xMove < mLastX) {
				mLastX = xMove;
				chart.updateSeriesXMoveLeft(startOffsetX);
				regenChartConfig();
				lineViews.get(0).updateLine(chart.getOffsetStartX());
				lineViews.get(1).updateLine(chart.getOffsetStartX());
				invalidate();
				return true;
			}
			chart.updateSeriesXMoveRight(startOffsetX);
			return true;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private void regenChartConfig() {
		mStartOffset = chart.getStartOffset();
		mEndOffset = chart.getEndOffset();

		genChartConfig();
		getSeriesY();
		for (int j = 0; j < dataSeries.length; j++) {
			ArrayList<LineData> chartData = dataSeries[j];
			lineViews.get(j).setData(
					chartData.subList(mStartOffset, mEndOffset + 1));
		}
		chart.seriesY = mSeriesY;
		chart.numberLine = mNumberLine;
		chart.numberStep = mNumberStep;
		chart.genDistanceY();
		lineViews.get(0).updateConfig(chart.getDistanceSeriesY(), mNumberLine,
				mNumberStep);
		lineViews.get(1).updateConfig(chart.getDistanceSeriesY(), mNumberLine,
				mNumberStep);
	}

}
