package com.digitalforce.datingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.digitalforce.datingapp.R;
import com.digitalforce.datingapp.model.Chat;
import com.digitalforce.datingapp.persistance.DatingAppPreference;
import com.digitalforce.datingapp.utils.PicassoEx;
import com.farru.android.utill.StringUtils;

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

            convertView.setTag(holder);

        }else{
           holder = (ViewHolder)convertView.getTag();
        }

        if(isMineMessage(chat.getUserId())){
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            setData(chat,holder.rightProfileImg,holder.rightChatMsgTextView,holder.rightChatPicMsgImageView,holder.rightChatUserTextView,holder.rightChatTimeTextView);
        }else{
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatLayout.setVisibility(View.GONE);
            setData(chat,holder.leftProfileImg,holder.leftChatMsgTextView,holder.leftChatPicMsgImageView,holder.leftChatUserTextView,holder.leftChatTimeTextView);
        }


        return convertView;
    }


    private void setData(Chat chat,ImageView profileImgView,TextView msgTv,ImageView imageView,TextView userNameView,TextView chatTimingView) {
        picassoLoad(chat.getByPhoto(), profileImgView);
        userNameView.setText(chat.getByName());
        chatTimingView.setText(chat.getTime());
        switch (chat.getChatType()) {
            case 0: //text msg
                msgTv.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                msgTv.setText(chat.getText());
                break;
            case 1: // image
                msgTv.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                msgTv.setText(chat.getText());
                picassoLoad(chat.getChatImage(), imageView);
                break;
            case 2: // emotions
                msgTv.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(mContext.getResources().obtainTypedArray(R.array.emotions_imgs).getResourceId(getEmotionId(chat.getText()),-1));
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
    }


    private boolean isMineMessage(String userId){
        return userId.equals(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", mContext));
    }


    public void picassoLoad(String url, ImageView imageView) {
        if(!StringUtils.isNullOrEmpty(url))
           PicassoEx.getPicasso(mContext).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
    }


    private int getEmotionId(String msg){

        int id = Integer.parseInt(msg.replaceAll("emotionn2him","").trim());
        return id;
    }

}
