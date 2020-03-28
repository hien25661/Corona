package app.free.corona.virus.helper.customview.graph;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;

import app.free.corona.virus.CoronaApplication;


public class PieChart extends com.github.mikephil.charting.charts.PieChart {
    private Context mContext;

    public PieChart(Context context) {
        super(context);
        init(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;

        this.setUsePercentValues(true);
        this.getDescription().setEnabled(false);
        this.setExtraOffsets(5, 10, 5, 5);

        this.setDragDecelerationFrictionCoef(0.95f);

        this.setDrawHoleEnabled(true);
        this.setHoleColor(Color.WHITE);

        this.setTransparentCircleColor(Color.WHITE);
        this.setTransparentCircleAlpha(50);

        this.setHoleRadius(30f);
        this.setTransparentCircleRadius(40f);

        this.setDrawCenterText(true);

        this.setRotationAngle(0);
        // enable rotation of the chart by touch
        this.setRotationEnabled(true);
        this.setHighlightPerTapEnabled(true);

        Legend l = this.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        this.setEntryLabelColor(Color.WHITE);
        this.setEntryLabelTypeface(CoronaApplication.getInstance().getCustomFont());
        this.setEntryLabelTextSize(9f);



        this.setDrawEntryLabels(false);
        this.setNoDataText("No Data");
        Paint p = this.getPaint(Chart.PAINT_INFO);
        p.setColor(Color.parseColor("#007ad9"));
        p.setTypeface(CoronaApplication.getInstance().getCustomFont());
    }
}
