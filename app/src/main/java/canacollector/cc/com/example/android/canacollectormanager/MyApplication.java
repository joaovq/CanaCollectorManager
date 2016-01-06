package canacollector.cc.com.example.android.canacollectormanager;

import android.app.Application;

/**
 * Created by joaovq on 06/01/16.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // register with parse
        ParseUtils.registerParse(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
