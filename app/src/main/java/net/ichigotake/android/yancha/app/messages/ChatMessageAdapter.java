package net.ichigotake.android.yancha.app.messages;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.ichigotake.android.yancha.app.R;
import net.ichigotake.android.yancha.app.core.ChatMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

final class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private static final int layoutResource = R.layout.chat_message_item;
    private final LayoutInflater layoutInflater;
    private final String defaultProfileImageUrl = "http://yancha.hachiojipm.org/static/img/nobody.png";

    ChatMessageAdapter(Context context, List<ChatMessage> messages) {
        super(context, layoutResource, messages);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ChatMessage item = getItem(position);
        holder.chatMessageItemNickname.setText(item.nickname);
        holder.chatMessageItemText.setText(item.text);
        holder.chatMessageItemTimestamp.setText(item.created_at_datetime);
        Picasso.with(getContext())
                .load(TextUtils.isEmpty(item.profile_image_url) ? defaultProfileImageUrl : item.profile_image_url)
                .into(holder.chatMessageItemIcon);
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'chat_message_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.chat_message_item_timestamp)
        TextView chatMessageItemTimestamp;
        @InjectView(R.id.chat_message_item_icon)
        ImageView chatMessageItemIcon;
        @InjectView(R.id.chat_message_item_nickname)
        TextView chatMessageItemNickname;
        @InjectView(R.id.chat_message_item_text)
        TextView chatMessageItemText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
