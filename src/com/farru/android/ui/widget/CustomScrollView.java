package com.farru.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


/**
 * 
 * @author vineet.kumar
 *
 */
public class CustomScrollView extends ScrollView {

	private OnScrollViewListener	mOnScrollViewListener	= null;

	public interface OnScrollViewListener {
		void onScrollChanged(CustomScrollView v, int l, int t, int oldl, int oldt);
	}

	public CustomScrollView(Context context) {
		super(context);
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mOnScrollViewListener != null) {
			mOnScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}

	public void setOnScrollViewListener(OnScrollViewListener l) {
		this.mOnScrollViewListener = l;
	}

}
