package app.free.corona.virus.fragments.news;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.laimiux.rxnetwork.RxNetwork;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import app.free.corona.virus.BaseFragment;
import app.free.corona.virus.BuildConfig;
import app.free.corona.virus.R;
import app.free.corona.virus.adapters.NewsAdsAdapter;
import app.free.corona.virus.databinding.FragmentNewsTopBinding;
import app.free.corona.virus.helper.NetworkStatus;
import app.free.corona.virus.models.news.Article;
import app.free.corona.virus.services.responses.NewsListReponse;
import app.free.corona.virus.utils.Utils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewsTopFragment extends BaseFragment {
    private FragmentNewsTopBinding mBinding;
    private Context mContext;
    private int page = 1;
    private int totalPage = 5;
    private NewsListReponse newsListReponse;
    private ArrayList<Object> mRecyclerViewItems = new ArrayList<>();
    private NewsAdsAdapter newsAdsAdapter;


    private PublishProcessor<Integer> pagination = PublishProcessor.create();
    private boolean requestOnWay = false;

    // List of native ads that have been successfully loaded.
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    private Disposable sendStateSubscription;

    // The number of native ads to load.
    public static final int NUMBER_OF_ADS = 2;

    // The AdLoader used to load ads.
    private AdLoader adLoader;


    public static NewsTopFragment newInstance() {
        NewsTopFragment fragment = new NewsTopFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_top, container, false);
            this.mContext = mBinding.getRoot().getContext();
            initView();
        }

        if (newsAdsAdapter != null) {
            mBinding.rcvList.setAdapter(newsAdsAdapter);
            setUpLoadMore();
        } else {
            handleNetworkStatus();
        }
        return mBinding.getRoot();
    }

    private void initView() {
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    private void fetchData(int type) {

        mBinding.rcvList.setRefreshing(true);
        compositeDisposable.clear();

        Disposable disposable = pagination.onBackpressureDrop()
                .doOnNext(integer -> {
                    requestOnWay = true;
                    mBinding.rcvList.setRefreshing(true);
                })
                .concatMap((Function<Integer, Publisher<Response<NewsListReponse>>>) fromId -> loadData(type))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(listNews -> {
                    if (listNews.isSuccessful()) {
                        totalPage = 5;
                        if (newsListReponse == null) {
                            newsListReponse = listNews.body();
                            mRecyclerViewItems.clear();
                            mRecyclerViewItems.addAll(newsListReponse.getArticles());
                            //loadNativeAds();
                            insertAdsInMenuItems();
                        } else {
                            mBinding.rcvList.setRefreshing(false);
                            if (listNews.body().getArticles() != null && listNews.body().getArticles().size() > 0) {
                                for (Article item : listNews.body().getArticles()) {
                                    newsAdsAdapter.insert(item, newsAdsAdapter.getAdapterItemCount());
                                }
                            }
                        }
                    }
                    requestOnWay = false;

                })
                .doOnError(throwable -> {
                    if (throwable instanceof HttpException) {
                        Response<?> response = ((HttpException) throwable).response();
                        Log.d("AAAAA", response.message());
                    }
                }).subscribe();

        compositeDisposable.add(disposable);
        pagination.onNext(1);
    }

    private Flowable<Response<NewsListReponse>> loadData(int type) {
        String todate = Utils.getCurrentDateTime();
        switch (type) {
            case 0:
                return newsApi.getNewsList("COVID",todate,"publishedAt", BuildConfig.NewsAPIKey, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        }
        return newsApi.getNewsList("COVID",todate,"publishedAt", BuildConfig.NewsAPIKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void setUpLoadMore() {
        mBinding.rcvList.reenableLoadmore();
        mBinding.rcvList.setOnLoadMoreListener((i, i1) -> {
            if (page < totalPage) {
                page++;
                pagination.onNext(page);
            }
        });
    }

    public void handleNetworkStatus() {
        if (getActivity() == null || getActivity().isFinishing()) return;
        final Flowable<NetworkStatus> sendStateStream =
                RxNetwork.flow(mContext).map(hasInternet -> {
                    if (hasInternet) {
                        return new NetworkStatus(NetworkStatus.OK);
                    }
                    return new NetworkStatus(NetworkStatus.DISCONNECTED);
                });

        sendStateSubscription =
                sendStateStream.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(networkStatus -> {
                            if (networkStatus.getStatus() == NetworkStatus.OK) {
                                fetchData(0);
                                setUpLoadMore();

                            } else if (networkStatus.getStatus() == NetworkStatus.DISCONNECTED) {
                            }
                        });
    }


    private void loadNativeAds() {
        mNativeAds.clear();
        AdLoader.Builder builder = new AdLoader.Builder(mContext, getString(R.string.native_advance_id));
        adLoader = builder.forUnifiedNativeAd(
                unifiedNativeAd -> {
                    // A native ad loaded successfully, check if the ad loader has finished loading
                    // and if so, insert the ads into the list.
                    mNativeAds.add(unifiedNativeAd);
                    if (!adLoader.isLoading()) {
                        insertAdsInMenuItems();
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        insertAdsInMenuItems();
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }

    private void insertAdsInMenuItems() {

        mBinding.rcvList.setRefreshing(false);
        if (mNativeAds.size() > 0) {
            int offset = (mRecyclerViewItems.size() / mNativeAds.size()) + 1;
            int index = 0;
            for (UnifiedNativeAd ad : mNativeAds) {
                mRecyclerViewItems.add(index, ad);
                index = index + offset;
            }
        }

        bindDataAds();
    }

    private void bindDataAds() {
        try {
            newsAdsAdapter = new NewsAdsAdapter(mContext, mRecyclerViewItems);
            mBinding.rcvList.setAdapter(newsAdsAdapter);
        }catch (Exception ex){

        }
    }

    @Override
    public void onDestroy() {
        recycleFragmentView();
        super.onDestroy();
    }

    private void recycleFragmentView() {
        try {
            Glide.get(mContext).getBitmapPool().clearMemory();
            Glide.get(mContext).clearMemory();
        } catch (Exception ex) {

        }
    }

}
