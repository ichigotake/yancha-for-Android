package net.ichigotake.yancha.chat;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import net.ichigotake.yancha.R;
import net.ichigotake.yanchasdk.lib.model.JoinUsers;

/**
 * ログインユーザーを表示する
 */
class JoinUsersContainer {

    final private TextView mJoinUsersCountView;
    final private JoinUsersPopupListener mPopup;
    final private View mUsersIcon;

    JoinUsersContainer(Activity activity, View view) {
        mJoinUsersCountView = (TextView) view.findViewById(R.id.chatJoinUsersCount);
        mUsersIcon = view.findViewById(R.id.chatJoinUsersIcon);
        mUsersIcon.setOnClickListener(new IconClickListener());
        mPopup = new JoinUsersPopupListener(activity, mUsersIcon);
    }
    
    void setUsers(JoinUsers users) {
        mPopup.setUsers(users);
        mJoinUsersCountView.setText(users.count() + "人");
    }
    
    void update(JoinUsers users) {
        setUsers(users);
    }
    
    private class IconClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            mPopup.show();
        }

    }
}
