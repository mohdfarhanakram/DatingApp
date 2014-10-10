package com.farru.android.ui.widget.gif;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @edited m.farhan
 */
public class GifDecoderView extends ImageView {

	private GifDecoder	mGifDecoder;
	private Bitmap		mTmpBitmap;
	private Handler		mHandler;
	private Runnable	mUpdateResults;

	/**
	 * @param context
	 */
	public GifDecoderView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GifDecoderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GifDecoderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context, attrs, defStyle);
	}

	/**
	 * 
	 */
	private void initialize(Context pContext, AttributeSet attrs, int defStyle) {
		mHandler = new Handler();
		mUpdateResults = new Runnable() {
			public void run() {
				if (mTmpBitmap != null && !mTmpBitmap.isRecycled()) {
					GifDecoderView.this.setImageBitmap(mTmpBitmap);
				}
			}
		};
		
		// Un Comment below code

		/*int[] viewsAllAttrIdsArr = R.styleable.com_kelltontech_ui_widget_gif_GifDecoderView;
		int contentDescriptionAttributeId = R.styleable.com_kelltontech_ui_widget_gif_GifDecoderView_android_contentDescription;
		TypedArray typedArr = pContext.obtainStyledAttributes(attrs, viewsAllAttrIdsArr);
		String gifFilePathInAssets = typedArr.getString(contentDescriptionAttributeId);
		typedArr.recycle();
		InputStream stream = null;
		try {
			stream = pContext.getAssets().open(gifFilePathInAssets);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (stream != null) {
			playGif(stream);
		}*/
	}

	/**
	 * @param pGifInputStream
	 */
	private void playGif(InputStream pGifInputStream) {
		mGifDecoder = new GifDecoder();
		int gifStatus = mGifDecoder.read(pGifInputStream);

		if (gifStatus != GifDecoder.STATUS_OK) {
			return;
		}

		new Thread() {
			public void run() {
				final int n = mGifDecoder.getFrameCount();
				final int ntimes = mGifDecoder.getLoopCount();
				int repetitionCounter = 0;
				do {
					for (int i = 0; i < n; i++) {
						mTmpBitmap = mGifDecoder.getFrame(i);
						int t = mGifDecoder.getDelay(i);
						mHandler.post(mUpdateResults);
						try {
							Thread.sleep(t);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (ntimes != 0) {
						repetitionCounter++;
					}
				} while (repetitionCounter <= ntimes);
			}
		}.start();
	}
}
