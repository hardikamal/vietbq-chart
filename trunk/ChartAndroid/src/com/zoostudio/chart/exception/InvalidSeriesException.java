package com.zoostudio.chart.exception;

import android.util.Log;

@SuppressWarnings("serial")
public class InvalidSeriesException extends Exception {
	public InvalidSeriesException() {
		
	}
	
	@Override
	public String getMessage() {
		return "2 mang lech size";
	}
	@Override
	public void printStackTrace() {
		Log.e("InvalidSeriesException", "Cac series phai co cung kich thuoc");
	}
}
