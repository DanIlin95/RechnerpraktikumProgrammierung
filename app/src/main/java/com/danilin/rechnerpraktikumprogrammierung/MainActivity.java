package com.danilin.rechnerpraktikumprogrammierung;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {


    ProgressBar progressBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        webView = (WebView) findViewById(R.id.webView);

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(false);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);


            webView.setWebViewClient(new ourViewClient());


            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    progressBar.setProgress(progress);
                    if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);


                    }

                    if (progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);

                    }

                }
            });

            String data = getIntent().getDataString();
            if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
                webView.loadUrl(data);
            } else {
                webView.loadUrl("http://137.208.106.90:8080/4141-Rechnerpraktikum");
            }

        }
    }





    public class ourViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {
            if(url.contains("137.208.106.90:8080/4141-Rechnerpraktikum")) {
                v.loadUrl(url);
                CookieManager.getInstance().setAcceptCookie(true);
            }else{
                Uri uri = Uri.parse(url);
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Choose browser"));
            }
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url){
            super.onPageFinished(view,url);

        }
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        webView.saveState(outState);

    }
}


