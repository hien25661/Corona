package app.free.corona.virus.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

import app.free.corona.virus.R;
import app.free.corona.virus.services.responses.corona.CountryInforResponse;
import app.free.corona.virus.utils.StringUtils;
import app.free.corona.virus.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nguyenvanhien on 4/16/18.
 */

public class CountryInfoAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> {
    private List<Object> countryInforResponseList = new ArrayList<>();
    private Context mContext;
    private selectedItemListener listener;

    private static final int DATA_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;

    public CountryInfoAdapter(Context context, List<Object> articles) {
        this.countryInforResponseList = articles;
        mContext = context;
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = countryInforResponseList.get(position);
        if (recyclerViewItem instanceof UnifiedNativeAd) {
            return UNIFIED_NATIVE_AD_VIEW_TYPE;
        }
        return DATA_VIEW_TYPE;
    }

    public void setListener(selectedItemListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                View unifiedNativeLayoutView = LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.ad_unified_grid,
                        parent, false);
                mContext = unifiedNativeLayoutView.getContext();
                return new CountryInfoAdapter.UnifiedNativeAdViewHolder(unifiedNativeLayoutView);
            case DATA_VIEW_TYPE:
                // fall through
            default:
                View dataLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_country, parent, false);
                mContext = dataLayoutView.getContext();
                return new CountryInfoAdapter.ViewHolder(dataLayoutView);
        }
    }

    @Override
    public int getAdapterItemCount() {
        return countryInforResponseList.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    public void insert(CountryInforResponse item, int position) {
        insertInternal(countryInforResponseList, item, position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder dataHolder, int position) {

        int viewType = getItemViewType(position);
        switch (viewType) {
            case DATA_VIEW_TYPE:
                CountryInfoAdapter.ViewHolder holder = (CountryInfoAdapter.ViewHolder) dataHolder;
                if (position >= 0 && position < countryInforResponseList.size()) {
                    final CountryInforResponse article = (CountryInforResponse) countryInforResponseList.get(position);

                    holder.tvTitle.setText("");
                    String thumbnail = article.getCountryInfo().getFlag();
                    Utils.showImageFromUrlIntoView(mContext
                            , thumbnail
                            , holder.imvCountry, position);
                    if (StringUtils.isNotEmpty(article.getCountry())) {
                        holder.tvTitle.setText(article.getCountry());
                    }
                    holder.tvTotalCase.setText(""+article.getCases());
                    holder.tvTodayCases.setText(""+article.getTodayCases());
                    holder.tvDeath.setText(""+article.getDeaths());
                    holder.tvRecovered.setText(""+article.getRecovered());
                    holder.tvActive.setText(""+article.getActive());


                    holder.itemParent.setOnClickListener(view -> {
                        if (listener != null) {
                            listener.onSelectedItem(article);
                        }
                    });
                }
                break;
            case UNIFIED_NATIVE_AD_VIEW_TYPE:
                // fall through
            default:
                UnifiedNativeAd nativeAd = (UnifiedNativeAd) countryInforResponseList.get(position);
                populateNativeAdView(nativeAd, ((CountryInfoAdapter.UnifiedNativeAdViewHolder) dataHolder).getAdView());

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imvCountry)
        ImageView imvCountry;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvTotalCase)
        TextView tvTotalCase;
        @BindView(R.id.tvTodayCases)
        TextView tvTodayCases;
        @BindView(R.id.tvDeath)
        TextView tvDeath;
        @BindView(R.id.tvRecovered)
        TextView tvRecovered;
        @BindView(R.id.tvActive)
        TextView tvActive;

        @BindView(R.id.itemParent)
        View itemParent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

        private UnifiedNativeAdView adView;

        public UnifiedNativeAdView getAdView() {
            return adView;
        }

        UnifiedNativeAdViewHolder(View view) {
            super(view);
            adView = (UnifiedNativeAdView) view.findViewById(R.id.ad_view);

            // The MediaView will display a video asset if one is present in the ad, and the
            // first image asset otherwise.
            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

            // Register the view used for each individual asset.
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
            adView.setBodyView(adView.findViewById(R.id.ad_body));
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(adView.findViewById(R.id.ad_icon));
            adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        }
    }

    private void populateNativeAdView(UnifiedNativeAd nativeAd,
                                      UnifiedNativeAdView adView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        NativeAd.Image icon = nativeAd.getIcon();

        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd);
    }

    public interface selectedItemListener {
        void onSelectedItem(CountryInforResponse item);
    }

}
