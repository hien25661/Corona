package app.free.corona.virus.helper;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import app.free.corona.virus.R;
import app.free.corona.virus.helper.interfaces.MyAdsListener;


public class AdsLoader {
    private static InterstitialAd mInterstitialAd;
    public static MyAdsListener myAdsListener;
    private static AdView adView;

    public static InterstitialAd getInstanceInterstitialAd(Context mContext) {
        if (mInterstitialAd == null) {
            mInterstitialAd = new InterstitialAd(mContext);
            mInterstitialAd.setAdUnitId(mContext.getResources().getString(R.string.interstitial_id));
            mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F65C87184259952867C0A20E68F6958D").build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("F65C87184259952867C0A20E68F6958D").build());
                    if (myAdsListener != null) {
                        myAdsListener.callback(true);
                    }
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    if (count < 5) {
                        count++;
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdLoaded() {
                    count = 0;
                    super.onAdLoaded();
                }
            });
        }
        return mInterstitialAd;
    }

    static int count = 0;

    public static void showAds(Context mContext, MyAdsListener listener) {
        myAdsListener = listener;
        mInterstitialAd = AdsLoader.getInstanceInterstitialAd(mContext);
        if (mInterstitialAd.isLoaded() ) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            if (myAdsListener != null) {
                myAdsListener.callback(true);
            }
        }
    }
}
