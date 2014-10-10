package com.farru.android.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.farru.android.utill.BitmapUtils;

/**
 * @author sachin.gupta
 */
public class CircularImageView extends ImageView {

	private int	mDiameter;

	/**
	 * @param context
	 */
	public CircularImageView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CircularImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * @param diameter
	 */
	public void setDiameter(int diameter) {
		mDiameter = diameter;
	}

	@Override
	public void setImageBitmap(Bitmap pBitmap) {
		if (pBitmap != null && mDiameter != 0) {
			pBitmap = BitmapUtils.getCircularCenterCropBitmap(pBitmap, mDiameter);
		}
		super.setImageBitmap(pBitmap);
	}
}
