package net.ichigotake.android.yancha.app.store;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferenceStore {

    private final SharedPreferences pref;

    public PreferenceStore(Context context) {
        this.pref = context
                .getSharedPreferences("net.ichigotake.android.yancha.app", Context.MODE_MULTI_PROCESS);
    }

    public void setChatActivate(boolean active) {
        pref.edit()
                .putBoolean(PreferenceStoreKey.CHAT_ACTIVATE.key, active)
                .apply();
    }

    public boolean isChatActive() {
        return pref.getBoolean(PreferenceStoreKey.CHAT_ACTIVATE.key, false);
    }

    public void setReadMessageId(int messageId) {
        pref.edit()
                .putInt(PreferenceStoreKey.READ_MESSAGE_ID.key, messageId)
                .apply();
    }

    public int getReadeMessageId() {
        return pref.getInt(PreferenceStoreKey.READ_MESSAGE_ID.key, 0);
    }

    private static enum PreferenceStoreKey {
        CHAT_ACTIVATE("chat_activate"),
        READ_MESSAGE_ID("read_message_id"),
        ;

        private final String key;

        private PreferenceStoreKey(String key) {
            this.key = key;
        }

    }
}
