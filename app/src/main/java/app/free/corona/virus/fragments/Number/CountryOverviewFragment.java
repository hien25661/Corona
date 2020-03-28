package app.free.corona.virus.fragments.Number;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import app.free.corona.virus.BaseFragment;
import app.free.corona.virus.CoronaApplication;
import app.free.corona.virus.R;
import app.free.corona.virus.adapters.CountryInfoAdapter;
import app.free.corona.virus.databinding.FragmentCountryOverviewBinding;
import app.free.corona.virus.databinding.FragmentNumberOverviewBinding;
import app.free.corona.virus.services.responses.corona.CountryInforResponse;
import app.free.corona.virus.services.responses.corona.OverviewResponse;
import app.free.corona.virus.utils.AbstractObserver;
import app.free.corona.virus.utils.Utils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CountryOverviewFragment extends BaseFragment {
    private FragmentCountryOverviewBinding mBinding;
    private Context mContext;
    private CountryInfoAdapter countryInfoAdapter;
    private ArrayList<Object> mList = new ArrayList<>();

    public static CountryOverviewFragment newInstance() {
        CountryOverviewFragment fragment = new CountryOverviewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_overview, container, false);
            this.mContext = mBinding.getRoot().getContext();
        }
        initView();

        return mBinding.getRoot();
    }

    private void initView() {
        mBinding.rcvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        initData();
    }

    private void initData() {
        mList.clear();

        compositeDisposable.add(coronaApi.getCountriesInfor("cases")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbstractObserver<ArrayList<CountryInforResponse>>() {
                    @Override
                    public void onNext(ArrayList<CountryInforResponse> value) {
                        if (value == null) {
                            Snackbar snackbar = Snackbar.make(mBinding.getRoot(),
                                    R.string.there_error, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            mList.addAll(value);
                            setData(mList);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Snackbar snackbar = Snackbar.make(mBinding.getRoot(),
                                R.string.there_error, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }));


    }

    private void setData(ArrayList<Object> countryInforResponseList) {
        if (countryInforResponseList == null) return;
        countryInfoAdapter = new CountryInfoAdapter(mContext, countryInforResponseList);
        mBinding.rcvList.setAdapter(countryInfoAdapter);

    }
}
