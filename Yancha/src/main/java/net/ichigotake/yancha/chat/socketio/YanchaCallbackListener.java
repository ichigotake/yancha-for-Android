package net.ichigotake.yancha.chat.socketio;

import android.app.Activity;
import android.view.View;

import net.ichigotake.yancha.common.api.socketio.YanchaEmitter;
import net.ichigotake.yancha.common.api.socketio.listener.ChatCallbackListener;
import net.ichigotake.yancha.common.api.socketio.listener.ConnectionEventListener;
import net.ichigotake.yancha.common.api.socketio.listener.LoginEventListener;
import net.ichigotake.yancha.common.api.socketio.listener.MessageEventListener;

/**
 * yanchaからのSocketIOのコールバックリスナ
 */
public class YanchaCallbackListener implements ChatCallbackListener {

    final private ChatHolder mParameter;
    
    public YanchaCallbackListener(YanchaEmitter emitter, Activity activity, View view) {
        mParameter = new ChatHolder(emitter, activity, view);
        mParameter.initialize();
    }

    @Override
    public ConnectionEventListener createConnectionListener() {
        return new ConnectionListener(mParameter);
    }

    @Override
    public LoginEventListener createLoginListener() {
        return new LoginListener(mParameter);
    }

    @Override
    public MessageEventListener createMessageListener() {
        return new MessageListener(mParameter);
    }
}
