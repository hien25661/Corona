package app.free.corona.virus.helper.customview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.free.corona.virus.R;
import app.free.corona.virus.helper.interfaces.SelectorListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nguyenvanhien on 6/28/17.
 */

public class CustomBottomBar extends RelativeLayout implements View.OnClickListener {
    SelectorListener callBack;

    @BindView(R.id.navigation)
    View navigation;

    @BindView(R.id.viewHome)
    LinearLayout viewHome;
    @BindView(R.id.viewNumber)
    LinearLayout viewNumber;
    @BindView(R.id.viewWatch)
    LinearLayout viewWatch;
    @BindView(R.id.viewInfo)
    LinearLayout viewInfo;


    @BindView(R.id.imvHome)
    ImageView imvHome;
    @BindView(R.id.tvHome)
    TextView tvHome;

    @BindView(R.id.imvNumber)
    ImageView imvNumber;
    @BindView(R.id.tvNumber)
    TextView tvNumber;

    @BindView(R.id.imvWatch)
    ImageView imvWatch;
    @BindView(R.id.tvWatch)
    TextView tvWatch;

    @BindView(R.id.imvInfo)
    ImageView imvInfo;
    @BindView(R.id.tvInfo)
    TextView tvInfo;


    int currentPos = -1;

    public CustomBottomBar(Context context) {
        super(context);
        init();
    }

    public CustomBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View v = inflate(getContext(), R.layout.layout_bottom_bar, this);
        ButterKnife.bind(this, v);
        viewHome.setOnClickListener(this);
        viewNumber.setOnClickListener(this);
        viewWatch.setOnClickListener(this);
        viewInfo.setOnClickListener(this);

        setSelected(0);
    }

    public void setOnClickCallBack(SelectorListener callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewHome:
                setSelected(0);
                if (callBack != null) {
                    callBack.onSelected(0);
                }
                break;
            case R.id.viewNumber:
                setSelected(1);
                if (callBack != null) {
                    callBack.onSelected(1);
                }
                break;
            case R.id.viewWatch:
                setSelected(2);
                if (callBack != null) {
                    callBack.onSelected(2);
                }
                break;

            case R.id.viewInfo:
                setSelected(3);
                if (callBack != null) {
                    callBack.onSelected(3);
                }
                break;

            default:
                break;
        }
    }

    public void setSelected(int pos) {
        if (currentPos != pos) {
            currentPos = pos;

            imvHome.setImageResource(R.mipmap.ic_home);
            imvNumber.setImageResource(R.mipmap.ic_number);
            imvWatch.setImageResource(R.mipmap.ic_video);
            imvInfo.setImageResource(R.mipmap.ic_info);


            tvHome.setTextColor(Color.parseColor("#BBBBBB"));
            tvNumber.setTextColor(Color.parseColor("#BBBBBB"));
            tvInfo.setTextColor(Color.parseColor("#BBBBBB"));
            tvWatch.setTextColor(Color.parseColor("#BBBBBB"));



            switch (pos) {
                case 0:
                    imvHome.setImageResource(R.mipmap.ic_home_selected);
                    tvHome.setTextColor(Color.parseColor("#00346A"));
                    break;
                case 1:
                    imvNumber.setImageResource(R.mipmap.ic_number_selected);
                    tvNumber.setTextColor(Color.parseColor("#00346A"));
                    break;
                case 2:
                    imvWatch.setImageResource(R.mipmap.ic_video_selected);
                    tvWatch.setTextColor(Color.parseColor("#00346A"));
                    break;
                case 3:
                    imvInfo.setImageResource(R.mipmap.ic_info_selected);
                    tvInfo.setTextColor(Color.parseColor("#00346A"));
                    break;

            }
        }
    }

}
