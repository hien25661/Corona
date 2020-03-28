package app.free.corona.virus.apps;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import app.free.corona.virus.BaseActivity;
import app.free.corona.virus.R;
import app.free.corona.virus.databinding.ActivityWebViewInfoBinding;
import app.free.corona.virus.utils.Const;
import app.free.corona.virus.utils.StringUtils;

public class WebInforActivity extends BaseActivity {
    private ActivityWebViewInfoBinding binding;
    private String url;
    private String title = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view_info);
        url = getIntent().getStringExtra(Const.URL);

        if (StringUtils.isEmpty(url)) {
            finish();
            return;
        }
        setUpToolBar(this, binding.toolbar);

        WebSettings webSettings = binding.webViewInfor.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        binding.webViewInfor.getSettings().setJavaScriptEnabled(true);
        binding.webViewInfor.getSettings().setDomStorageEnabled(true);
        binding.webViewInfor.getSettings().setPluginState(WebSettings.PluginState.ON);

        binding.webViewInfor.setWebViewClient(new WebViewClient());
        binding.webViewInfor.loadUrl(url);
    }
}
