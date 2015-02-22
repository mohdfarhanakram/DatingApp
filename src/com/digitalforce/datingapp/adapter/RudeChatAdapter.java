package com.digitalforce.datingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.constants.AppConstants;
import com.digitalforce.datingapp.dialog.PhotoGalleryDialog;
import com.digitalforce.datingapp.model.Chat;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.AppUtil;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.digitalforce.datingapp.view.AudioPlayerActivity;
import com.digitalforce.datingapp.view.PhotoDetailActivity;
import com.digitalforce.datingapp.view.PlayVideoActivity;
import com.farru.android.utill.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by FARHAN on 11/8/2014.
 */
public class RudeChatAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Chat> mChatArrayList;
    public RudeChatAdapter(Context context,ArrayList<Chat> chatArrayList){
        mContext = context;
        mChatArrayList = chatArrayList;
    }

    public void setChatList(ArrayList<Chat> chatArrayList){
        mChatArrayList = chatArrayList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mChatArrayList==null?0:mChatArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Chat chat = mChatArrayList.get(position);

        if(convertView==null){
            holder = new ViewHolder();

            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_chat_bubble_item,parent,false);


            holder.mainLayout = convertView.findViewById(R.id.main_layout);

            holder.leftChatMsgTextView = (TextView)convertView.findViewById(R.id.left_txtv_chat_msg);
            holder.leftProfileImg = (ImageView)convertView.findViewById(R.id.left_img_member);
            holder.leftChatPicMsgImageView = (ImageView)convertView.findViewById(R.id.left_imgv_chat_img);

            holder.rightChatMsgTextView = (TextView)convertView.findViewById(R.id.right_txtv_chat_msg);
            holder.rightProfileImg = (ImageView)convertView.findViewById(R.id.right_img_member);
            holder.rightChatPicMsgImageView = (ImageView)convertView.findViewById(R.id.right_imgv_chat_img);

            holder.leftChatLayout = convertView.findViewById(R.id.left_chat_layout);
            holder.rightChatLayout = convertView.findViewById(R.id.right_chat_layout);

            holder.leftChatUserTextView = (TextView)convertView.findViewById(R.id.left_user_name);
            holder.rightChatUserTextView = (TextView)convertView.findViewById(R.id.right_user_name);

            holder.leftChatTimeTextView = (TextView)convertView.findViewById(R.id.left_chat_time);
            holder.rightChatTimeTextView = (TextView)convertView.findViewById(R.id.right_chat_time);

            holder.rightEmotionImgView = (ImageView)convertView.findViewById(R.id.right_emotion_img);
            holder.leftEmotionImgView = (ImageView)convertView.findViewById(R.id.left_emotion_img);

            holder.rightPlayImgView = (ImageView)convertView.findViewById(R.id.right_media_img);
            holder.leftPlayImgView = (ImageView)convertView.findViewById(R.id.left_media_img);



            convertView.setTag(holder);

        }else{
           holder = (ViewHolder)convertView.getTag();
        }

        if(isMineMessage(chat.getUserId())){
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            setData(chat,holder.rightProfileImg,holder.rightChatMsgTextView,holder.rightChatPicMsgImageView,holder.rightChatUserTextView,holder.rightChatTimeTextView,holder.rightEmotionImgView,holder.rightPlayImgView);
        }else{
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatLayout.setVisibility(View.GONE);
            setData(chat,holder.leftProfileImg,holder.leftChatMsgTextView,holder.leftChatPicMsgImageView,holder.leftChatUserTextView,holder.leftChatTimeTextView,holder.leftEmotionImgView,holder.leftPlayImgView);
        }


        return convertView;
    }


    private void setData(Chat chat,ImageView profileImgView,TextView msgTv,ImageView imageView,TextView userNameView,TextView chatTimingView,ImageView emotionView,ImageView playerView) {
        picassoLoad(chat.getByPhoto(), profileImgView);
        userNameView.setText(chat.getByName());
        chatTimingView.setText(chat.getTime());
        switch (chat.getChatType()) {
            case 0: //text msg
                msgTv.setVisibility(View.VISIBLE);
                emotionView.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                playerView.setVisibility(View.GONE);
                msgTv.setText(chat.getText());
                break;
            case 1: // image
                msgTv.setVisibility(View.GONE);
                emotionView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setTag(chat.getChatMediaUrl());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String imgUrl = (String)v.getTag();
                        ArrayList<String> urls = new ArrayList<String>();
                        urls.add(imgUrl);
                        (new PhotoGalleryDialog(mContext,urls, urls.indexOf(imgUrl))).show();
                        /*Intent intent = new Intent(mContext,PhotoDetailActivity.class);
                        intent.putExtra(AppConstants.IMAGE_URL, (String)v.getTag());
                        mContext.startActivity(intent);*/
                    }
                });
                playerView.setVisibility(View.GONE);
                msgTv.setText(chat.getText());
                picassoLoad(chat.getChatMediaUrl(), imageView);
                break;
            case 2: // emotions

                TypedArray emotions = mContext.getResources().obtainTypedArray(R.array.emotions_imgs);
                if(chat.getEmotionId()<emotions.length()){
                    msgTv.setVisibility(View.GONE);
                    emotionView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    playerView.setVisibility(View.GONE);
                    emotionView.setImageResource(emotions.getResourceId(chat.getEmotionId(),-1));
                }else{
                    msgTv.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    emotionView.setVisibility(View.GONE);
                    playerView.setVisibility(View.GONE);
                    msgTv.setText(chat.getText());
                }
                break;
            case 3:
                msgTv.setText("Audio");
                msgTv.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                emotionView.setVisibility(View.GONE);
                playerView.setVisibility(View.VISIBLE);
                playerView.setImageResource(R.drawable.play_audio_msg);
                playerView.setTag(chat.getChatMediaUrl());
                playerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext,AudioPlayerActivity.class);
                        intent.putExtra(AppConstants.RECORDED_AUDIO_URL, (String)v.getTag());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 4:
                msgTv.setText("Video");
                msgTv.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                emotionView.setVisibility(View.GONE);
                playerView.setVisibility(View.VISIBLE);
                playerView.setImageResource(R.drawable.play_video_msg);
                playerView.setTag(chat.getChatMediaUrl());
                playerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext,PlayVideoActivity.class);
                        i.putExtra(AppConstants.USER_VIDEO_URL,(String)v.getTag());
                        mContext.startActivity(i);
                    }
                });
                break;

            default:
        }
    }

    private class ViewHolder {

        //public View headerView;
        public View mainLayout;
        //public TextView headerTextView;

        public TextView leftChatMsgTextView;
        public ImageView leftProfileImg;
        public ImageView leftChatPicMsgImageView;

        public TextView rightChatMsgTextView;
        public ImageView rightProfileImg;
        public ImageView rightChatPicMsgImageView;

        public View leftChatLayout;
        public View rightChatLayout;

        public TextView rightChatUserTextView;
        public TextView rightChatTimeTextView;

        public TextView leftChatUserTextView;
        public TextView leftChatTimeTextView;

        public ImageView leftEmotionImgView;
        public ImageView rightEmotionImgView;

        public ImageView leftPlayImgView;
        public ImageView rightPlayImgView;
    }


    private boolean isMineMessage(String userId){
        return userId.equals(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", mContext));
    }


    public void picassoLoad(String url, ImageView imageView) {
        if(!StringUtils.isNullOrEmpty(url)){
            if(!url.contains("http"))
               createBitmapImage(imageView,url);
            else
                PicassoEx.getPicasso(mContext).load(url).fit().into(imageView);

        }
    }


    private void createBitmapImage(ImageView img , String path){
        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        if(bitmap!=null){
            Bitmap aBitmap = AppUtil.getActualBitmap(path, bitmap);
            if(aBitmap!=null){
                img.setImageBitmap(aBitmap);
            }
        }

    }

}
