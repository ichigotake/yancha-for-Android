package net.ichigotake.android.yancha.app.messages;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ichigotake.android.yancha.app.R;
import net.ichigotake.android.yancha.app.core.ChatMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ChatMessageListFragment extends ListFragment {

    private final List<ChatMessage> messages = new ArrayList<ChatMessage>();
    private ChatMessageAdapter adapter;
    private OnPagingListener onPagingListener;

    public void addMessages(Collection<ChatMessage> messages) {
        this.messages.addAll(messages);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof OnPagingListener)) {
            throw new IllegalStateException("Activity must implements OnPagingListener");
        }
        this.onPagingListener = (OnPagingListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_message_list, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.adapter = new ChatMessageAdapter(getActivity(), this.messages);
        setListAdapter(this.adapter);
        getListView().setOnScrollListener(new PagingOnScrollListener() {
            @Override
            public void onPaging() {
                onPagingListener.onPaging();
            }
        });
    }

    @Override
    public void onDetach() {
        this.onPagingListener = null;
        super.onDetach();
    }

}
