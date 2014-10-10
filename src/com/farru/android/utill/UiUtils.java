package com.farru.android.utill;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;

/**
 * Utility class for UI related methods.
 */
public class UiUtils {

	/**
	 * @param pContext
	 */
	public static String getDeviceDensityGroupName(Context pContext) {
		int densityDpi = pContext.getResources().getDisplayMetrics().densityDpi;
		String densityGroupNameStr = "xhdpi";
		switch (densityDpi) {
		case DisplayMetrics.DENSITY_LOW: {
			densityGroupNameStr = "ldpi";
			break;
		}
		case DisplayMetrics.DENSITY_MEDIUM: {
			densityGroupNameStr = "mdpi";
			break;
		}
		case DisplayMetrics.DENSITY_HIGH: {
			densityGroupNameStr = "hdpi";
			break;
		}
		case DisplayMetrics.DENSITY_XHIGH: {
			densityGroupNameStr = "xhdpi";
			break;
		}
		case DisplayMetrics.DENSITY_XXHIGH: {
			densityGroupNameStr = "xxhdpi";
			break;
		}
		}
		return densityGroupNameStr;
	}

	/**
	 * @param event
	 * @return
	 */
	public static String getEventDetails(MotionEvent event) {
		String action = "";
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			action = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_UP:
			action = "ACTION_UP";
			break;
		case MotionEvent.ACTION_MOVE:
			action = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_CANCEL:
			action = "ACTION_CANCEL";
			break;
		default:
			action = "ACTION_" + event.getAction();
			break;
		}
		return action + ": " + event.getX() + "-" + event.getY();
	}

	/**
	 * @param event
	 * @return
	 */
	public static float getPixels(Context context, float dp) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dp * dm.density;
	}

	/**
	 * @note method is not working fine
	 * @param gridView
	 * @param numColumns
	 * @param verticalSpacing
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gridView, int numColumns, int verticalSpacing) {
		Adapter adapter = gridView.getAdapter();
		if (adapter == null) {
			return;
		}
		int numItems = gridView.getCount();
		int numRows = (numItems + numColumns - 1) / numColumns;
		int totalHeight = 0;
		for (int row = 0; row < numRows; row++) {
			int rowHeight = 0;
			int itemHeight = 0;
			for (int column = 0; column < numColumns; column++) {
				int itemIndex = row * numColumns + column;
				if (itemIndex >= numItems) {
					break;
				}
				View gridItem = adapter.getView(itemIndex, null, gridView);
				gridItem.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
				itemHeight = gridItem.getMeasuredHeight();
				if (itemHeight > rowHeight) {
					rowHeight = itemHeight;
				}
			}
			totalHeight += rowHeight;
			totalHeight += verticalSpacing;
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = (totalHeight);
		gridView.setLayoutParams(params);
		gridView.requestLayout();
	}

	/**
	 * method use to expand a view smoothly
	 * 
	 * @param v
	 */
	public static void expand(final View v) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT : (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration(((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density)));
		v.startAnimation(a);
	}

	/**
	 * method use to collapse a view smoothly
	 * 
	 * @param v
	 */
	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration(((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)));
		v.startAnimation(a);
	}
}
