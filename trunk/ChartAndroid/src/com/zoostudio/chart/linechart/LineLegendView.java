package com.zoostudio.chart.linechart;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zoostudio.bean.LegendItem;
import com.zoostudio.chart.R;

public class LineLegendView extends View {
	private ArrayList<LegendItem> items;
	private int radius;
	private float distance;
	private Paint paint;
	private int offsetY;
	private float line;
	private Paint paintLine;
	private Paint paintText;

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

	private void init() {
		radius = getResources().getDimensionPixelSize(
				R.dimen.default_radius_node);
		distance = getResources().getDimensionPixelSize(R.dimen.default_offset);
		line = getResources().getDimensionPixelSize(R.dimen.default_width_line);
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
		paintText.setTextSize(getResources().getDimension(R.dimen.defalut_font_size));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		offsetY = radius / 2;
		for (LegendItem item : items) {
			paint.setColor(item.getColor());
			paint.setShadowLayer(2f, 0, 0, item.getColor());

			paintLine.setColor(item.getColor());
			paintLine.setShadowLayer(2f, 0, 0, item.getColor());
			
			canvas.drawCircle(0, offsetY, radius, paint);
			canvas.drawLine(0, offsetY, line, offsetY, paintLine);
			canvas.drawCircle(line, offsetY, radius, paint);
			canvas.drawText(item.getTitle(), line + radius, offsetY, paintText);
			offsetY += distance;
		}
	}
}
