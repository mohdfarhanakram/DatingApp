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
            if(isMineMessage(chat.getUserId())){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_bubble_item_right,parent,false);
            }else{
                convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_bubble_item_left,parent,false);
            }
            holder.headerView = convertView.findViewById(R.id.header_layout);
            holder.mainLayout = convertView.findViewById(R.id.main_layout);
            holder.headerTextView = (TextView)convertView.findViewById(R.id.header_text_view);
            holder.chatMsgTextView = (TextView)convertView.findViewById(R.id.txtv_chat_msg);
            holder.profileImg = (ImageView)convertView.findViewById(R.id.img_member);
            holder.chatPicMsgImageView = (ImageView)convertView.findViewById(R.id.imgv_chat_img);

            convertView.setTag(holder);

        }else{
           holder = (ViewHolder)convertView.getTag();
        }


        if(chat.isHeader()){
            holder.headerView.setVisibility(View.VISIBLE);
            holder.mainLayout.setVisibility(View.GONE);
            holder.headerTextView.setText(chat.getTime());
        }else{
            holder.headerView.setVisibility(View.GONE);
            holder.mainLayout.setVisibility(View.VISIBLE);
            picassoLoad(chat.getByPhoto(),holder.profileImg);
            switch (chat.getChatType()){
                case 0: //text msg
                    holder.chatMsgTextView.setVisibility(View.VISIBLE);
                    holder.chatPicMsgImageView.setVisibility(View.GONE);
                    holder.chatMsgTextView.setText(chat.getText());
                    break;
                case 1: // image
                    holder.chatMsgTextView.setVisibility(View.GONE);
                    holder.chatPicMsgImageView.setVisibility(View.VISIBLE);
                    holder.chatMsgTextView.setText(chat.getText());
                    picassoLoad(chat.getChatImage(),holder.chatPicMsgImageView);
                    break;
                default:
            }
        }

        return convertView;
    }


    private static class ViewHolder {
        public View headerView;
        public View mainLayout;
        public TextView headerTextView;
        public TextView chatMsgTextView;
        public ImageView profileImg;
        public ImageView chatPicMsgImageView;
    }


    private boolean isMineMessage(String userId){
        return userId.equals(DatingAppPreference.getString(DatingAppPreference.USER_ID, "", mContext));
    }


    public void picassoLoad(String url, ImageView imageView) {
        PicassoEx.getPicasso(mContext).load(url).error(R.drawable.farhan).placeholder(R.drawable.farhan).fit().into(imageView);
    }


}
