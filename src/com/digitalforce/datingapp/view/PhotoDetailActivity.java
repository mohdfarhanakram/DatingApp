/**
 * 
 */
package com.digitalforce.datingapp.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.utils.ToastCustom;
import com.farru.android.utill.StringUtils;
import com.farru.android.utill.Utils;

/**
 * @author m.farhan
 *
 */
public class PhotoDetailActivity extends BaseActivity implements OnClickListener{
	
	private int mWidth,mHeight;
	
	private boolean isPublic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_detail_layout);
		
		isPublic = getIntent().getBooleanExtra(AppConstants.IS_COMING_FROM_PUBLIC_PHOTO, false);
		
		if(isPublic){
			((TextView)findViewById(R.id.txtv_make_privacy)).setText("Make Private");
		}else{
			((TextView)findViewById(R.id.txtv_make_privacy)).setText("Make Public");
		}
		
		findViewById(R.id.txtv_make_profile).setOnClickListener(this);
		findViewById(R.id.txtv_make_privacy).setOnClickListener(this);
		findViewById(R.id.txtv_delete).setOnClickListener(this);
		
		String imgUrl = getIntent().getStringExtra(AppConstants.IMAGE_URL);
		ImageView imgDetail = (ImageView)findViewById(R.id.image_view_detail);
		
		initializeImageWidthHeight();
		
		picassoLoad(imgUrl, imgDetail,mWidth,mHeight);
		
		//Utils.scaleImage(imgDetail, 200);
	}
	
	

	@Override
	public void onEvent(int eventId, Object eventData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txtv_make_profile:
             ToastCustom.underDevelopment(this);
			break;
		case R.id.txtv_make_privacy:
			if(isPublic){
				
			}else{
				
			}
			ToastCustom.underDevelopment(this);
			break;
		case R.id.txtv_delete:
			ToastCustom.underDevelopment(this);
			break;

		default:
			break;
		}

	}
	
	
	 private void initializeImageWidthHeight() {
	        Resources resources = getResources();
	        Display display = getWindowManager().getDefaultDisplay();
	        int width = display.getWidth();
	        int height = display.getHeight()- (findViewById(R.id.option_layout).getHeight());
	        /*float colCount = resources.getInteger(R.integer.catalog_page_col_count);
	        float itemSpacing = resources.getDimension(R.dimen.catalog_product_unit_horizontal_spacing);*/
	        float padding = resources.getDimension(R.dimen.img_detail_padding);
	        height -= (padding * 2);

	        width = (int)(height/1.45);

	        mWidth = width;
	        mHeight = height;

	    }

    public void picassoLoad(String imgUrl, ImageView imageView,int width,int height) {
        if(!StringUtils.isNullOrEmpty(imgUrl)){
            if(!imgUrl.contains("http"))
                createBitmapImage(imageView,imgUrl);
            else
                PicassoEx.getPicasso(this).load(imgUrl).resize(width, height).into(imageView);

        }

    }

    private void createBitmapImage(ImageView img , String path){
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        if(bitmap!=null)
            img.setImageBitmap(bitmap);
    }

}
