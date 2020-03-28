package app.free.corona.virus;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import app.free.corona.virus.services.CoronaApi;
import app.free.corona.virus.services.NewsApi;
import app.free.corona.virus.services.NewsClient;
import app.free.corona.virus.utils.EventBusUtils;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by nguyenvanhien on 5/28/18.
 */

public class BaseFragment extends Fragment {
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    public NewsApi newsApi;
    public CoronaApi coronaApi;
    protected EventBusUtils.Holder eventBus = EventBusUtils.getDefault();
    static int count = 0;

    public Disposable sendStateSubscription;

    /**
     * 最短click事件的时间间隔
     */
    private static final long MIN_CLICK_INTERVAL = 500;
    /**
     * 上次click的时间
     */
    private long mLastClickTime;

    @Override
    public void onStart() {
        super.onStart();
    }

    public AlertDialog progressDialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        progressDialog = new SpotsDialog.Builder()
                .setContext(this.getContext())
                .setTheme(R.style.Custom)
                .build();
        newsApi = NewsClient.getNewsService();
        coronaApi = NewsClient.getCoronaService();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        try {
            if (sendStateSubscription != null && !sendStateSubscription.isDisposed()) {
                sendStateSubscription.dispose();
                sendStateSubscription = null;
            }
        } catch (Exception ex){}
    }

    @Override
    public void onResume() {
        super.onResume();
    }



}

