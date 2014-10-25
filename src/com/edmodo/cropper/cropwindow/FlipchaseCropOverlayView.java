package com.edmodo.cropper.cropwindow;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

public class FlipchaseCropOverlayView extends CropOverlayView{

	public FlipchaseCropOverlayView(Context context) {
		super(context);
	}
	
	 public FlipchaseCropOverlayView(Context context, AttributeSet attrs) {
	        super(context, attrs);

	 }
	 
	 public void setBitmapRect(Rect bitmapRect) {
		    super.setBitmapRect(bitmapRect);
	 }

}
