package net.ichigotake.android.yancha.app;

import android.app.Application;

import net.ichigotake.android.yancha.app.service.PollingPostMessageService;

/**
 * 別の BuildVariants で独自の {@link Application} クラスを用意する場合は、このクラスを敬称しよう
 */
public class YanchaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(PollingPostMessageService.createIntent(this));
    }

    @Override
    public void onTerminate() {
        stopService(PollingPostMessageService.createIntent(this));
        super.onTerminate();
    }
}
