package app.free.corona.virus;

import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class CoronaApplication extends MultiDexApplication {
    static CoronaApplication instance;
    private Typeface customFont;
    private Typeface customFontNormal;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        customFontNormal = Typeface.createFromAsset(getAssets(), "en.ttf");
        customFont = Typeface.createFromAsset(getAssets(), "en_bold.ttf");
    }

    public static CoronaApplication getInstance() {
        return instance;
    }

    public Typeface getCustomFont() {
        return customFont;
    }

    public Typeface getCustomFontNormal() {
        return customFontNormal;
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(CoronaApplication.this);
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
