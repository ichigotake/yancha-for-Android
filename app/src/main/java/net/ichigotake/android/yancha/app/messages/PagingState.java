package net.ichigotake.android.yancha.app.messages;

public class PagingState {

    private final int perPage = 100;
    private int page = 1;
    private boolean isRequesting;
    private boolean isComplete;

    public void complete() {
        this.isComplete = true;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isRequesting() {
        return isRequesting;
    }

    public void next() {
        this.page++;
    }

    public void startRequesting() {
        this.isRequesting = true;
    }

    public void stopRequesting() {
        this.isRequesting = false;
    }

    public int getOffset() {
        return (page - 1) * perPage;
    }

    public int getPerPage() {
        return perPage;
    }

}
