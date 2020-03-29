package app.free.corona.virus.fragments.Number;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import app.free.corona.virus.BaseFragment;
import app.free.corona.virus.R;
import app.free.corona.virus.adapters.NumberPagerAdapter;
import app.free.corona.virus.databinding.FragmentTopNumberBinding;
import app.free.corona.virus.helper.interfaces.SimpleCallBack;

public class NumberTopFragment extends BaseFragment {
    private FragmentTopNumberBinding mBinding;
    private Context mContext;
    private NumberPagerAdapter numberPagerAdapter;
    private CountryOverviewFragment countryOverviewFragment;
    private NumberOverviewFragment numberOverviewFragment;

    public static NumberTopFragment newInstance() {
        NumberTopFragment fragment = new NumberTopFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mBinding == null){
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_number, container, false);
            this.mContext = mBinding.getRoot().getContext();
        }
        initView();
        return mBinding.getRoot();
    }


    public void initView() {
        setDataPager();
        numberOverviewFragment = (NumberOverviewFragment) getChildFragmentManager().findFragmentById(R.id.NumberOverviewFragment);
        countryOverviewFragment = (CountryOverviewFragment) getChildFragmentManager().findFragmentById(R.id.CountryOverviewFragment);

        if(numberOverviewFragment!=null){
            numberOverviewFragment.initView(new SimpleCallBack() {
                @Override
                public void success(Object... params) {
                    if(countryOverviewFragment!=null){
                        new Handler().postDelayed(() -> countryOverviewFragment.initView(),3000);

                    }
                }

                @Override
                public void failed() {
                    if(countryOverviewFragment!=null){
                        new Handler().postDelayed(() -> countryOverviewFragment.initView(),3000);

                    }
                }
            });
        }
    }

    private void setDataPager() {
        if (numberPagerAdapter == null) {
            numberPagerAdapter = new NumberPagerAdapter(getChildFragmentManager());
            mBinding.vprMovie.setAdapter(numberPagerAdapter);


        } else {
            numberPagerAdapter = new NumberPagerAdapter(getChildFragmentManager());
            mBinding.vprMovie.setAdapter(numberPagerAdapter);


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
