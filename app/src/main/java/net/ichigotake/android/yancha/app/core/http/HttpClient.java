package net.ichigotake.android.yancha.app.core.http;

import android.os.Handler;
import android.os.Looper;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpResponse;


public final class HttpClient {

    private final String url;
    private OnResponseListener onResponseListener;
    private OnErrorResponseListener onErrorResponseListener;

    public HttpClient(String url) {
        this.url = url;
    }

    public HttpClient setOnResponseListener(OnResponseListener listener) {
        this.onResponseListener = listener;
        return this;
    }

    public HttpClient setOnErrorResponseListener(OnErrorResponseListener listener) {
        this.onErrorResponseListener = listener;
        return this;
    }

    public void execute() {
        AsyncHttpClient client = AsyncHttpClient.getDefaultInstance();
        client.executeString(new AsyncHttpGet(url), new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(final Exception e, AsyncHttpResponse source, final String result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (e == null) {
                            if (onResponseListener != null) {
                                onResponseListener.onResponse(result);
                            }
                        } else {
                            if (onErrorResponseListener != null) {
                                onErrorResponseListener.onErrorResponse(e, result);
                            }
                        }
                    }
                });
            }
        });
    }

}
