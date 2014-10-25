package com.farru.android.utill;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

/**
 * Utility class for Bitmap related methods.
 * 
 * @author m.farhan
 */
public abstract class BitmapUtils {

	private static String	LOG_TAG	= "BitmapUtils";

	/**
	 * @param pSourceBmp
	 * @param pNewWidth
	 * @param pNewHeight
	 * @return scaled bitmap, which is not less than required minimum dimensions
	 */
	public static Bitmap getScaledBitmap(Bitmap pSourceBmp, int pNewWidth, int pNewHeight, boolean pIsUniform) {
		if (pSourceBmp == null || pNewWidth <= 0 || pNewHeight <= 0) {
			return null;
		}
		int actualWidth = pSourceBmp.getWidth();
		int actualHeight = pSourceBmp.getHeight();

		/**
		 * Check if scaling is not required.
		 */
		if (actualWidth == pNewWidth && actualHeight == pNewHeight) {
			return pSourceBmp;
		}

		int scalingToWidth, scalingToHeight;

		/**
		 * If uniform scaling is required, then scaled Bitmap might not be of
		 * pNewWidth and pNewHeight. Any one dimension will be as per respective
		 * new dimension and other will be >= respective new dimension
		 */
		if (pIsUniform) {
			// If scaling as per pNewWidth will be enough
			scalingToWidth = pNewWidth;
			scalingToHeight = actualHeight * scalingToWidth / actualWidth;

			// If scaling as per pNewHeight will be enough
			if (scalingToHeight < pNewHeight) {
				scalingToHeight = pNewHeight;
				scalingToWidth = actualWidth * scalingToHeight / actualHeight;
			}
		} else {
			scalingToWidth = pNewWidth;
			scalingToHeight = pNewHeight;
		}

		try {
			return Bitmap.createScaledBitmap(pSourceBmp, scalingToWidth, scalingToHeight, true);
		} catch (Throwable tr) {
			Log.e(LOG_TAG, "Error in scaling upto required width and/or height", tr);
			return null;
		}
	}

	/**
	 * @param pSourceBmp
	 * @param pNewWidth
	 * @param pNewHeight
	 * @return
	 */
	public static Bitmap getScaledCroppedBitmap(Bitmap pSourceBmp, int pNewWidth, int pNewHeight) {
		Bitmap uniScaledBitmap = getScaledBitmap(pSourceBmp, pNewWidth, pNewHeight, true);
		if (uniScaledBitmap == null) {
			return null;
		}
		int currentWidth = uniScaledBitmap.getWidth();
		int currentHeight = uniScaledBitmap.getHeight();
		int cropDiff;

		/**
		 * Crop from left and right edges
		 */
		if (currentWidth > pNewWidth) {
			cropDiff = currentWidth - pNewWidth;
			try {
				return Bitmap.createBitmap(uniScaledBitmap, cropDiff / 2, 0, currentWidth - cropDiff, currentHeight);
			} catch (Throwable tr) {
				Log.e(LOG_TAG, "Error in horizontal cropping", tr);
				return null;
			}
		}

		/**
		 * Crop from top and bottom edges
		 */
		if (currentHeight > pNewHeight) {
			cropDiff = currentHeight - pNewHeight;
			try {
				return Bitmap.createBitmap(uniScaledBitmap, 0, cropDiff / 2, currentWidth, currentHeight - cropDiff);
			} catch (Throwable tr) {
				Log.e(LOG_TAG, "Error in horizontal cropping", tr);
				return null;
			}
		}

		return uniScaledBitmap;
	}

	/**
	 * @param pSourceBmp
	 * @param radiusX
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap pSourceBmp, int radiusX, int radiusY) {
		if (pSourceBmp == null || radiusX <= 0 || radiusY <= 0) {
			return pSourceBmp;
		}
		Bitmap outputBmp = null;

		try {

			outputBmp = Bitmap.createBitmap(pSourceBmp.getWidth(), pSourceBmp.getHeight(), Config.ARGB_8888);

			Canvas canvas = new Canvas(outputBmp);

			int color = 0xffffffff;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, pSourceBmp.getWidth(), pSourceBmp.getHeight());
			RectF rectF = new RectF(rect);
			float roundPx = radiusX;

			paint.setAntiAlias(true);

			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(pSourceBmp, rect, rect, paint);
		} catch (Throwable tr) {
			Log.e(LOG_TAG, "Error in creating rounded bitmap", tr);
		}

		return outputBmp;
	}

	/**
	 * @param pSourceBmp
	 * @param radiusX
	 * @return
	 */
	public static Bitmap getRoundedCircularBitmap(Bitmap pSourceBmp, int radius) {
		if (pSourceBmp == null || radius <= 0) {
			return pSourceBmp;
		}
		Bitmap outputBmp = null;

		try {

			outputBmp = Bitmap.createBitmap(pSourceBmp.getWidth(), pSourceBmp.getHeight(), Config.ARGB_8888);

			Canvas canvas = new Canvas(outputBmp);

			int color = 0xffffffff;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, pSourceBmp.getWidth(), pSourceBmp.getHeight());
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(pSourceBmp, rect, rect, paint);
		} catch (Throwable tr) {
			Log.e(LOG_TAG, "Error in creating rounded bitmap", tr);
		}

		return outputBmp;
	}

	/**
	 * @param pView
	 * @param pWidth
	 * @param pHeight
	 * @return
	 */
	public static Bitmap getBitmapFromView(View pView, int pWidth, int pHeight) {
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(pWidth, MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(pHeight, MeasureSpec.EXACTLY);

		pView.measure(widthMeasureSpec, heightMeasureSpec);
		pView.layout(0, 0, pView.getMeasuredWidth(), pView.getMeasuredHeight());

		pView.setDrawingCacheEnabled(true);
		try {
			return pView.getDrawingCache();
		} catch (Throwable tr) {
			Log.e(LOG_TAG, "Error in getDrawingCache", tr);
			return null;
		}
	}

	public static Bitmap getCroppedBitmap(Bitmap bitmap, int margin) {
		int rad = bitmap.getWidth() <= bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(rad, rad, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		final Rect rect = new Rect(0, 0, rad, rad);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);

		canvas.drawCircle(rad / 2, rad / 2, rad / 2 - margin, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * @param imageBitmap
	 * @return compressed bytes of imageBitmap
	 */
	public static byte[] getPNGImageData(Bitmap imageBitmap) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		boolean isSuccess = imageBitmap.compress(CompressFormat.PNG, 0, os);
		byte[] imageData = null;
		if (isSuccess) {
			imageData = os.toByteArray();
		}
		try {
			os.close();
		} catch (Exception e) { /* ignore */
		}
		return imageData;
	}

	/**
	 * @param pSourceBmp
	 * @return resized imageBitmap
	 */
	public static Bitmap resizeToFitInside(Bitmap pSourceBmp, int pMaxWidth, int pMaxHeight) {
		int imageWidth = pSourceBmp.getWidth();
		int imageHeight = pSourceBmp.getHeight();
		if (imageWidth < pMaxWidth && imageHeight < pMaxHeight) {
			return pSourceBmp;
		}
		double widthRatio = (double) pMaxWidth / imageWidth;
		double heightRatio = (double) pMaxWidth / imageHeight;
		if (widthRatio > heightRatio) {
			pMaxWidth = (int) (imageWidth * heightRatio);
		} else {
			pMaxHeight = (int) (imageHeight * widthRatio);
		}
		try {
			return Bitmap.createScaledBitmap(pSourceBmp, pMaxWidth, pMaxHeight, true);
		} catch (Throwable tr) {
			Log.e(LOG_TAG, "Error in scaling upto required width and/or height", tr);
			return null;
		}
	}

	/**
	 * @param originalBmp
	 * @param diameter
	 * @return
	 */
	public static Bitmap getCircularCenterCropBitmap(Bitmap originalBmp, int diameter) {
		Bitmap resizedBmp = BitmapUtils.getScaledCroppedBitmap(originalBmp, diameter, diameter);
		Bitmap circularBmp = BitmapUtils.getRoundedCircularBitmap(resizedBmp, diameter / 2);
		return circularBmp;
	}
}
