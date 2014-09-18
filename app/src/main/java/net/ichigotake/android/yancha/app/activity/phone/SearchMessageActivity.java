package net.ichigotake.android.yancha.app.activity.phone;

import android.app.Activity;
import android.os.Bundle;

import net.ichigotake.android.yancha.app.R;
import net.ichigotake.android.yancha.app.core.ChatMessage;
import net.ichigotake.android.yancha.app.core.http.HttpClient;
import net.ichigotake.android.yancha.app.core.http.OnErrorResponseListener;
import net.ichigotake.android.yancha.app.core.http.OnResponseListener;
import net.ichigotake.android.yancha.app.messages.ChatMessageListFragment;
import net.ichigotake.android.yancha.app.messages.OnPagingListener;
import net.ichigotake.android.yancha.app.messages.PagingState;

import java.util.List;

public final class SearchMessageActivity extends Activity implements OnPagingListener {

    private final PagingState pagingState = new PagingState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_message);
    }

    private ChatMessageListFragment findChatMessageListFragment() {
        return (ChatMessageListFragment) getFragmentManager()
                .findFragmentById(R.id.activity_search_message_list);
    }

    @Override
    public void onPaging() {
        if (pagingState.isRequesting() || pagingState.isComplete()) {
            return;
        }
        pagingState.startRequesting();
        String requestUrl = "http://yancha.hachiojipm.org/api/search?order=-created_at_ms" +
                "&limit=" + pagingState.getPerPage() + "," + pagingState.getOffset();
        new HttpClient(requestUrl)
                .setOnResponseListener(new OnResponseListener() {
                    @Override
                    public void onResponse(String result) {
                        pagingState.stopRequesting();
                        List<ChatMessage> recursiveMessages = ChatMessage.fromSearchApiResponse(result);
                        if (recursiveMessages.size() < pagingState.getPerPage()) {
                            pagingState.complete();
                        }
                        pagingState.next();
                        findChatMessageListFragment().addMessages(recursiveMessages);
                    }
                })
                .setOnErrorResponseListener(new OnErrorResponseListener() {
                    @Override
                    public void onErrorResponse(Exception e, String result) {
                        pagingState.stopRequesting();
                    }
                })
                .execute();
    }

}
