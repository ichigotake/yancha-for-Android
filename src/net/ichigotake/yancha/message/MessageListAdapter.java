package net.ichigotake.yancha.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.sharakova.android.urlimageview.UrlImageView;
import net.ichigotake.yancha.R;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageListAdapter extends ArrayAdapter<MessageCell> {

	private LayoutInflater inflater;
	
	private ArrayList<MessageCell> messageList;
	
	public MessageListAdapter(Context context, ArrayList<MessageCell> messageList) {
		super(context, R.layout.common_message_cell, messageList);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.messageList = messageList;
	}
	
	protected ArrayList<MessageCell> toArrayList(SparseArray<MessageCell> messages) {
		ArrayList<MessageCell> arrayListMessages = new ArrayList<MessageCell>();
		int length = messages.size();
		for (int i=0; i>length; i++) {
			arrayListMessages.add(messages.get(i));
		}
		
		return arrayListMessages;
	}
	@Override
	public MessageCell getItem(int position) {
		return messageList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View contentView, ViewGroup parent) {
		Log.d("MessageListAdapter", "getView()");
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.common_message_cell, null);
		}
		
		MessageCell message = getItem(position);
		if (message != null) {
			Log.d("MessageListAdapter", "not null");
			TextView viewNickname = (TextView) contentView.findViewById(R.id.common_message_cell_nickname);
			viewNickname.setText(message.getNickname());
			
			TextView viewMessage = (TextView) contentView.findViewById(R.id.common_message_cell_message);
			viewMessage.setText(message.getMessage());
			
			UrlImageView viewProfileImageUrl = (UrlImageView) contentView.findViewById(R.id.common_message_cell_profile_image_url);
			viewProfileImageUrl.setImageUrl(message.getProfileImageUrl());
			
			int plusplus = message.getPlusplus();
			TextView viewPlusplus = (TextView) contentView.findViewById(R.id.common_message_cell_plusplus);
			if (plusplus > 0) {
				String plusplusText;
				if (plusplus >= 50) {
					plusplusText = "��x" + plusplus;
				} else {
					StringBuilder builder = new StringBuilder();
					for (int i=0; i<plusplus; i++) {
						builder.append("��");
					}
					plusplusText = builder.toString();
				}
				viewPlusplus.setVisibility(View.VISIBLE);
				viewPlusplus.setText(plusplusText);
			} else {
				viewPlusplus.setVisibility(View.GONE);
			}
			
			TextView viewTimestamp = (TextView) contentView.findViewById(R.id.common_message_cell_timestamp);
			viewTimestamp.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message.getCreatedTime()/100));
		}
		
		
		return contentView;
	}

}
