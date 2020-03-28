package app.free.corona.virus.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nguyenvanhien on 4/16/18.
 */

public class Utils {
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /*public static void showImageFromUrlIntoView(Context context, String url, ImageView imv, int pos) {
        if (StringUtils.isNotEmpty(url)) {

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // because file name is always same
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(pos))
                    .sizeMultiplier(0.08f).bitmapTransform(new BlurTransformation(10, 5));

            Glide.with(context).clear(imv);
            Glide.get(context).getBitmapPool().clearMemory();
            Glide.get(context).clearMemory();

            Glide.with(context).load(url).apply(requestOptions)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            final ShapeDrawable background = new ShapeDrawable();
                            background.getPaint().setColor(Color.parseColor(PlaceHolderDrawableHelper.getRandomColor()));
                            final Drawable[] layers = {background, resource};

                            imv.setImageDrawable(new LayerDrawable(layers));
                        }
                    });
        }
    }*/

    public static void showImageFromUrlIntoViewYoutube(Context context, String url, ImageView imv, int pos) {
        if (StringUtils.isNotEmpty(url)) {

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // because file name is always same
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(pos))
                    .sizeMultiplier(0.7f);

            Glide.with(context).clear(imv);
            Glide.get(context).getBitmapPool().clearMemory();
            Glide.get(context).clearMemory();

            Glide.with(context).load(url).apply(requestOptions)
                    .into(imv);
        }
    }

    public static void showImageFromUrlIntoView(Context context, String url, ImageView imv, int pos) {
        if (StringUtils.isNotEmpty(url)) {

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // because file name is always same
                    .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(pos))
                    .sizeMultiplier(1.0f);

            Glide.with(context).clear(imv);
            Glide.get(context).getBitmapPool().clearMemory();
            Glide.get(context).clearMemory();

            Glide.with(context).load(url).apply(requestOptions)
                    .into(imv);
        }
    }


    public static String convertNumberFormat(String strNumber) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        long number;
        try {
            number = Long.parseLong(strNumber);
            String yourFormattedString = formatter.format(number);
            return yourFormattedString;
        } catch (Exception ex) {
            return "";
        }
    }

    public static void openAppStore(Context mContext) {
        final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    public static String getThumbnailYoutube(int type, String youtubeId) {
        switch (type) {
            case Const.THUMBNAIL_DEFAULT:
                return "http://img.youtube.com/vi/" + youtubeId + "/default.jpg";
            case Const.THUMBNAIL_MEDIUM:
                return "http://img.youtube.com/vi/" + youtubeId + "/mqdefault.jpg";
            case Const.THUMBNAIL_HIGHT:
                return "http://img.youtube.com/vi/" + youtubeId + "/hqdefault.jpg";
            default:
                return "http://img.youtube.com/vi/" + youtubeId + "/default.jpg";
        }
    }

    public static boolean isConnectedInternet(Context context) {
        boolean isNetworkConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getState() == NetworkInfo.State.CONNECTED) {
            isNetworkConnected = true;
        }
        return isNetworkConnected;
    }

    public static String getCurrentYear() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (month == 0) {
            year = year - 1;
        }
        return "" + year;
    }

    public static String getCurrentDateTime(){
        Date date1 = new Date();
        SimpleDateFormat sdformat1
                = new SimpleDateFormat("yyyy-MM-dd");
        String fdate1 = sdformat1.format(date1);
        Log.e("Date: ",fdate1);
        return fdate1;
    }

    public static void animateTextView(long initialValue, long finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(valueAnimator1 -> textview.setText(valueAnimator1.getAnimatedValue().toString()));
        valueAnimator.start();

    }
}
