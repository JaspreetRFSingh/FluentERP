package com.jstech.fluenterp;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends BaseActivity {

    WebView webView;
    String urlG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        urlG = getIntent().getExtras().getString("url");
        setupToolbar(R.id.toolbarWebView, "PDF Viewer");
        initWeb();
    }

    void initWeb() {
        webView = findViewById(R.id.webView);
        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlG);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
