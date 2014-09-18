package net.ichigotake.android.yancha.app.messages;

import android.widget.AbsListView;

public abstract class PagingOnScrollListener implements AbsListView.OnScrollListener {

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    final public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount < firstVisibleItem + visibleItemCount + 20) {
            onPaging();
        }
    }

    abstract public void onPaging();
}
