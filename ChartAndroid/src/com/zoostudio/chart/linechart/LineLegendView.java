package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.zoostudio.bean.LegendItem;
import com.zoostudio.chart.R;

public class LineLegendView extends TextView {
	private ArrayList<LegendItem> items;
	private int radius;
	private float distance;
	private Paint paint;
	private int offsetY;
	private float line;
	private Paint paintLine;
	private Paint paintText;
	private Rect rectText;
	private int offsetX;
	private int padding;

	private AnimationSet animationSet;

	public LineLegendView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LineLegendView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineLegendView(Context context) {
		super(context);
		init();
	}

	public void setData(ArrayList<LegendItem> items) {
		this.items = items;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be,
			// so set text size to make it fit
			result = specSize;
		} else {
			int max = 0;
			for (LegendItem item : items) {
				paintText.getTextBounds(item.getTitle(), 0, item.getTitle()
						.length(), rectText);
				if (max < rectText.width()) {
					max = rectText.width();
				}
			}
			result = padding
					* 2
					+ getResources().getDimensionPixelSize(
							R.dimen.width_legend_view) + max;

			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was
				// what is called for by measureSpec
				result = Math.min(result, specSize);
			}
		}
		return (result);
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be,
			// so set text size to make it fit
			result = specSize;
		} else {
			// Measure the text: twice text size plus padding
			result = getResources().getDimensionPixelSize(
					R.dimen.height_legend_view)
					+ padding * 2;
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was
				// what is called for by measureSpec
				result = Math.min(result, specSize);
			}
		}
		return (result);
	}

	// private int measureText(int measureSpec, int padding1, int padding2) {
	// int result = 0;
	// int specMode = MeasureSpec.getMode(measureSpec);
	// int specSize = MeasureSpec.getSize(measureSpec);
	// if (specMode == MeasureSpec.EXACTLY) {
	// // We were told how big to be,
	// // so set text size to make it fit
	// result = specSize;
	// } else {
	// // Measure the text: twice text size plus padding
	// result = getResources().getDimensionPixelSize(
	// R.dimen.width_legend_view);
	// if (specMode == MeasureSpec.AT_MOST) {
	// // Respect AT_MOST value if that was
	// // what is called for by measureSpec
	// result = Math.min(result, specSize);
	// }
	// }
	// return (result);
	// }

	private void init() {
		animationSet = (AnimationSet) AnimationUtils.loadAnimation(
				getContext(), R.anim.alpha_animation);
		animationSet.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				LineLegendView.this.setVisibility(View.INVISIBLE);
			}
		});
		Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		rectText = new Rect();
		radius = getResources().getDimensionPixelSize(R.dimen.legend_radius);
		distance = getResources().getDimensionPixelSize(R.dimen.default_offset);
		line = getResources().getDimensionPixelSize(R.dimen.default_width_line);
		padding = getResources().getDimensionPixelSize(
				R.dimen.padding_legend_view);
		paint = new Paint();
		paint.setAntiAlias(true);

		paintLine = new Paint();
		paintLine.setStrokeWidth(getResources().getDimensionPixelSize(
				R.dimen.default_border_line));
		paintLine.setAntiAlias(true);
		paintLine.setStrokeCap(Paint.Cap.ROUND);
		paintLine.setStrokeJoin(Paint.Join.ROUND);
		paintLine.setStyle(Paint.Style.STROKE);

		paintText = new Paint();
		paintText.setAntiAlias(true);
		paintText.setTextSize(getResources().getDimension(
				R.dimen.defalut_font_size));
		paintText.setTypeface(typeface);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		offsetY = radius + padding;
		offsetX = radius + padding;
		for (LegendItem item : items) {
			paint.setColor(item.getColor());
			paint.setShadowLayer(.5f, 0, 0, item.getColor());
			paintLine.setColor(item.getColor());
			paintText.setColor(item.getColor());
			paintLine.setShadowLayer(.5f, 0, 0, item.getColor());

			paintText.getTextBounds(item.getTitle(), 0, item.getTitle()
					.length(), rectText);

			canvas.drawCircle(offsetX, offsetY, radius, paint);
			canvas.drawLine(offsetX, offsetY, line, offsetY, paintLine);
			canvas.drawCircle(line, offsetY, radius, paint);
			canvas.drawText(item.getTitle(), line + radius + 2, offsetY
					- rectText.centerY(), paintText);
			offsetY += distance;
		}
	}

	public void hideView() {
		startAnimation(animationSet);
	}
	
	public void showView(){
		setVisibility(View.VISIBLE);
	}
}
