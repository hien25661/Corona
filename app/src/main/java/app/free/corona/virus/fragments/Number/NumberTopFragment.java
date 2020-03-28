package app.free.corona.virus.fragments.Number;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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

public class NumberTopFragment extends BaseFragment {
    private FragmentTopNumberBinding mBinding;
    private Context mContext;
    private NumberPagerAdapter numberPagerAdapter;

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
            //initView();
        }
        return mBinding.getRoot();
    }

    private void initView() {
        setDataPager();
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
