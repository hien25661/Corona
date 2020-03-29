package app.free.corona.virus.apps;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.ads.MobileAds;

import app.free.corona.virus.BaseActivity;
import app.free.corona.virus.R;
import app.free.corona.virus.databinding.ActivityMainBinding;
import app.free.corona.virus.fragments.Number.NumberTopFragment;
import app.free.corona.virus.fragments.Watch.WatchTopFragment;
import app.free.corona.virus.fragments.infor.InforTopFragment;
import app.free.corona.virus.fragments.news.NewsTopFragment;
import app.free.corona.virus.utils.KeyboardUtils;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mBinding;
    private NewsTopFragment newsTopFragment;
    private NumberTopFragment numberTopFragment;
    private WatchTopFragment watchTopFragment;
    private InforTopFragment inforTopFragment;

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        initView();
    }

    private void initView() {
        newsTopFragment = NewsTopFragment.newInstance();
        numberTopFragment = NumberTopFragment.newInstance();
        watchTopFragment = WatchTopFragment.newInstance();
        inforTopFragment = InforTopFragment.newInstance();

        showFragmentPosition(currentIndex);
        mBinding.bottomBar.setOnClickCallBack(pos -> showFragmentPosition(pos));
    }

    private void showFragmentPosition(int pos) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (pos) {
            case 0:
                currentIndex = 0;
                transaction.replace(R.id.content, newsTopFragment, NewsTopFragment.class.getName());
                transaction.commit();
                break;
            case 1:
                currentIndex = 1;
                numberTopFragment = NumberTopFragment.newInstance();
                transaction.replace(R.id.content, numberTopFragment, NumberTopFragment.class.getName());
                transaction.commit();
                break;
            case 2:
                currentIndex = 2;
                transaction.replace(R.id.content, watchTopFragment, WatchTopFragment.class.getName());
                transaction.commit();
                break;
            case 3:
                currentIndex = 3;
                transaction.replace(R.id.content, inforTopFragment, InforTopFragment.class.getName());
                transaction.commit();
                break;
        }
        KeyboardUtils.hideKeyboard(this);
        mBinding.bottomBar.setSelected(pos);

    }
}
