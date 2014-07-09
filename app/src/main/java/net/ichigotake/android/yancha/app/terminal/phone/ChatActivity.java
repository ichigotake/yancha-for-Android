package net.ichigotake.android.yancha.app.terminal.phone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import net.ichigotake.android.yancha.app.chat.ChatFragment;
import net.ichigotake.android.yancha.app.R;

public final class ChatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, ChatFragment.newInstance())
                .commit();
    }

}
