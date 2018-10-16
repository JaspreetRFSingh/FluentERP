package com.jstech.fluenterp.sd;

import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jstech.fluenterp.R;

public class WebViewActivity extends AppCompatActivity {

    WebView wv;
    String url;
    String sdn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar_colour));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWebView);
        setSupportActionBar(toolbar);
        setTitle("PDF Bill");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sdn = getIntent().getExtras().getString("sdn").toString();
        url = "http://jaspreettechnologies.000webhostapp.com/createInvoice.php?sales_doc_no=";
        url += sdn;
        wv = (WebView)findViewById(R.id.webView);
        WebViewClient client = new WebViewClient();
        wv.setWebViewClient(client);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);
    }
}
