package app.free.corona.virus.fragments.Number;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import app.free.corona.virus.databinding.FragmentNumberOverviewBinding;
import app.free.corona.virus.services.responses.corona.OverviewResponse;
import app.free.corona.virus.utils.AbstractObserver;
import app.free.corona.virus.utils.Utils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NumberOverviewFragment extends BaseFragment {
    private FragmentNumberOverviewBinding mBinding;
    private Context mContext;

    public static NumberOverviewFragment newInstance() {
        NumberOverviewFragment fragment = new NumberOverviewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_number_overview, container, false);
            this.mContext = mBinding.getRoot().getContext();
        }
        initView();

        return mBinding.getRoot();
    }

    private void initView() {
        initPieChart();
    }

    private void initPieChart() {

        compositeDisposable.add(coronaApi.getOverview()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new AbstractObserver<OverviewResponse>() {
                    @Override
                    public void onNext(OverviewResponse value) {
                        if (value == null) {
                            Snackbar snackbar = Snackbar.make(mBinding.getRoot(),
                                    R.string.there_error, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            // Show Graph here
                            OverviewResponse overviewResponse = value;
                            setData(overviewResponse);
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

    private void setData(OverviewResponse overviewResponse) {
        if(overviewResponse == null) return;
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (overviewResponse.getDeaths() > 0) {

            entries.add(new PieEntry(overviewResponse.getDeaths(),
                    "Dead"));
        }

        if (overviewResponse.getActive() > 0) {
            entries.add(new PieEntry(overviewResponse.getActive(),
                    "Active"));
        }

        if (overviewResponse.getRecovered() > 0) {
            entries.add(new PieEntry(overviewResponse.getRecovered(),
                    "Recovered"));
        }


        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(1f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(25f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.parseColor("#e94d2b"));
        colors.add(Color.parseColor("#007ad9"));
        colors.add(Color.parseColor("#00cbcf"));


        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(CoronaApplication.getInstance().getCustomFontNormal());
        mBinding.pieChart.setData(data);

        // undo all highlights
        mBinding.pieChart.highlightValues(null);
        mBinding.pieChart.animateY(1500);

        mBinding.pieChart.invalidate();

        Utils.animateTextView(0, overviewResponse.getCases(), mBinding.tvNumberMovie);

        if (entries.size() == 0) return;
        for (PieEntry item : entries) {
            LinearLayout parent = new LinearLayout(mContext);

            parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 70));
            parent.setOrientation(LinearLayout.HORIZONTAL);
            parent.setBackgroundColor(Color.parseColor("#ddffffff"));
            parent.setGravity(Gravity.CENTER);

            TextView tvNameCountry = new TextView(mContext);
            tvNameCountry.setText(item.getLabel());
            tvNameCountry.setGravity(Gravity.LEFT | Gravity.CENTER);
            tvNameCountry.setTypeface(CoronaApplication.getInstance().getCustomFont());
            tvNameCountry.setTextColor(Color.parseColor("#007ad9"));

            TextView tvNumberMoviePerCountry = new TextView(mContext);
            //tvNumberMoviePerCountry.setText("" + (int) item.getValue());
            tvNumberMoviePerCountry.setGravity(Gravity.CENTER);
            tvNumberMoviePerCountry.setTypeface(CoronaApplication.getInstance().getCustomFont());
            tvNumberMoviePerCountry.setTextColor(Color.RED);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;

            tvNameCountry.setLayoutParams(params);
            tvNumberMoviePerCountry.setLayoutParams(params);

            parent.addView(tvNameCountry);
            parent.addView(tvNumberMoviePerCountry);
            Utils.animateTextView(0, (int) item.getValue(), tvNumberMoviePerCountry);

            mBinding.tableDataCountry.addView(parent);

            TextView tvDivide = new TextView(mContext);
            tvDivide.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            tvDivide.setBackgroundColor(Color.BLACK);
            mBinding.tableDataCountry.addView(tvDivide);
        }
    }
}
