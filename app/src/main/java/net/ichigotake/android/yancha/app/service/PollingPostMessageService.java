package net.ichigotake.android.yancha.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;

import net.ichigotake.android.yancha.app.ChatServer;
import net.ichigotake.android.yancha.app.R;
import net.ichigotake.android.yancha.app.activity.phone.ChatActivity;
import net.ichigotake.android.yancha.app.store.PreferenceStore;
import net.ichigotake.yancha.sdk.api.ApiEndpoint;
import net.ichigotake.yancha.sdk.chat.ChatMessage;
import net.ichigotake.yancha.sdk.chat.ChatMessageFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collections;
import java.util.List;

public final class PollingPostMessageService extends Service {

    public static Intent createIntent(Context context) {
        return new Intent(context, PollingPostMessageService.class);
    }

    private final String LOG_TAG = "PollingPostMessageService";
    private final Handler handler = new Handler();
    private final int POLLING_INTERVAL_MILLISECONDS = 60 * 1000;
    private PreferenceStore preferenceStore;
    private boolean willDestroy;

    public PollingPostMessageService() {
        super.onCreate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.preferenceStore = new PreferenceStore(this);
        this.handler.post(new PollingPostMessage());
    }

    private void restartPolling() {
        this.handler.postDelayed(new PollingPostMessage(), POLLING_INTERVAL_MILLISECONDS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        this.willDestroy = true;
        super.onDestroy();
    }

    private void sendNotification(List<ChatMessage> messages) {
        PendingIntent intent = PendingIntent.getActivity(
                this, 0, ChatActivity.createIntent(this), PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        ChatMessage shownNotifyMessage = null;
        int lastReadMessageId = preferenceStore.getReadeMessageId();
        int unreadCount = 0;
        for (ChatMessage message : messages) {
            if (message.getId() <= lastReadMessageId) {
                continue;
            }
            unreadCount++;
            shownNotifyMessage = message;
        }
        if (shownNotifyMessage == null) {
            return ;
        }
        preferenceStore.setReadMessageId(shownNotifyMessage.getId());
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setContentTitle(shownNotifyMessage.getNickname())
                .setContentText(shownNotifyMessage.getMessage())
                .setTicker(getString(R.string.notification_new_message_body,
                        shownNotifyMessage.getNickname(), shownNotifyMessage.getMessage()))
                .setContentIntent(intent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);
        if (unreadCount > 1) {
            notificationBuilder.setNumber(unreadCount);
        }
        notificationManager.notify("yancha", R.drawable.ic_launcher, notificationBuilder.build());
    }

    private class PollingPostMessage implements Runnable {

        @Override
        public void run() {
            if (preferenceStore.isChatActive()) {
                restartPolling();
                return ;
            }
            AsyncHttpClient.getDefaultInstance().executeString(
                    new AsyncHttpGet(ChatServer.getServerHost() + ApiEndpoint.SEARCH + "?order=-created_at_ms&limit=100"),
                    new AsyncHttpClient.StringCallback() {
                        @Override
                        public void onCompleted(Exception e, AsyncHttpResponse asyncHttpResponse, String s) {
                            if (e != null) {
                                Log.d(LOG_TAG, "", e);
                            }
                            if (willDestroy) {
                                return;
                            }
                            try {
                                JSONArray messagesJsonArray = new JSONArray(s);
                                List<ChatMessage> messages = ChatMessageFactory.createList(messagesJsonArray);
                                Collections.reverse(messages);
                                sendNotification(messages);
                                restartPolling();
                            } catch (JSONException e1) {
                                Log.d(LOG_TAG, "", e1);
                            }
                        }
                    }
            );
        }
    }
}
