package net.ichigotake.yancha.chat.socketio;

import net.ichigotake.colorfulsweets.common.activity.ActivityTransit;
import net.ichigotake.yancha.ChatActivity;
import net.ichigotake.yancha.common.api.socketio.listener.LoginEventListener;
import net.ichigotake.yancha.common.api.socketio.response.JoinTagResponse;
import net.ichigotake.yancha.common.api.socketio.response.NicknamesResponse;
import net.ichigotake.yancha.common.api.socketio.response.NoSessionResponse;
import net.ichigotake.yancha.common.api.socketio.response.TokenLoginResponse;
import net.ichigotake.yancha.sdk.model.ChatUser;
import net.ichigotake.yancha.sdk.model.ChatUserFactory;
import net.ichigotake.yancha.sdk.model.ChatUsers;

import org.json.JSONException;

/**
 * ログイン関連のイベントリスナ
 */
public class LoginListener implements LoginEventListener {

    final private ChatMediator mParameter;
    final private ChatUserFactory mUserFactory;

    public LoginListener(ChatMediator parameter) {
        mParameter = parameter;
        mUserFactory = new ChatUserFactory();
    }

    @Override
    public void onEvent(JoinTagResponse response) {

    }

    @Override
    public void onEvent(final NicknamesResponse response) {
        try {
            String rawJson = response.getResponseBody().or("{}");
            ChatUsers users = mUserFactory.fromNicknameEvent(rawJson);
            updateJoinUsers(users);
        } catch (JSONException e) {
            e.printStackTrace();
            updateJoinUsers(new ChatUsers());
        }
    }

    private void updateJoinUsers(final ChatUsers users) {
        mParameter.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mParameter.getContainer().updateJoinUsers(users);
            }
        });
        mParameter.getContainer().updateMyself(users);
    }

    @Override
    public void onEvent(NoSessionResponse response) {
        new ActivityTransit(mParameter.getActivity(), ChatActivity.class)
                .clearTop()
                .transition();
    }

    @Override
    public void onEvent(TokenLoginResponse response) {
        try {
            ChatUser myself = mUserFactory.fromTokenLoginEvent(response.getResponseBody().get());
            mParameter.getContainer().updateMyself(myself);
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO エラーイベント
        }
    }
}
