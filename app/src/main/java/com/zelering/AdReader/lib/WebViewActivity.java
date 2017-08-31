package com.zelering.AdReader.lib;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.zelering.AdReader.VideoEnabledWebChromeClient;
import com.zelering.AdReader.lib.model.Config;
import com.zelering.AdReader.lib.model.Product;

public class WebViewActivity extends AppCompatActivity {

    private static String toolbarColor = "#ffffff";
    private static int imgLogoInt = R.drawable.logo_vuforia;
    private static String backColor = "#da1c28";

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    private java.lang.String productUrl = "http://www.google.com";
    private Product product;
    private Toolbar toolbar;
    private ImageView imgBackArrow,imgLogo;
    private static Config config;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromIntent();
        // Save the web view
        webView = (VideoEnabledWebView) findViewById(R.id.webView_new_id);
        imgBackArrow = (ImageView) findViewById(R.id.imgBackArrow);
        imgLogo=(ImageView)findViewById(R.id.imgLogo);

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
        {
            @Override
            public void onProgressChanged(WebView view, int progress) {
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(new InsideWebViewClient());

        webView.loadUrl(productUrl);

        imgBackArrow.setColorFilter(Color.parseColor(backColor), PorterDuff.Mode.SRC_ATOP);

       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(toolbarColor)));
        imgLogo.setImageDrawable(this.getResources().getDrawable(imgLogoInt));

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       // getSupportActionBar().setTitle(Html.fromHtml("<font color='" + toolbarTextColor + "'>" + product.getName() + "</font>"));

    }

    private void getDataFromIntent() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            product = (Product) getIntent().getExtras().getSerializable("product");
            toolbarColor = (String) getIntent().getExtras().getString("toolbarColor");
            imgLogoInt = (Integer) getIntent().getExtras().getInt("imgLogo");
            backColor=(String) getIntent().getExtras().getString("backColor");
            productUrl = product.getUri();
        }
    }


    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public static Intent creatIntent(Activity context, Product product, String toolbarColor, int toolbarTextColor ,String backColor) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("product", product);
        intent.putExtra("toolbarColor", toolbarColor);
        intent.putExtra("imgLogo", toolbarTextColor);
        intent.putExtra("backColor",backColor);
        return intent;
    }

    @Override
    public void onBackPressed() {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (!webChromeClient.onBackPressed()) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }
        }
    }

}
