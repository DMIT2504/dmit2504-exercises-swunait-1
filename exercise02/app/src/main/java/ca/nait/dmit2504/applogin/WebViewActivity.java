package ca.nait.dmit2504.applogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebsiteWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebsiteWebview = findViewById(R.id.website_webview);
        mWebsiteWebview.setWebViewClient(new WebViewClient());
        String websiteUrl = getIntent().getStringExtra(MainActivity.WEBSITE_URL_EXTRA);
        mWebsiteWebview.loadUrl(websiteUrl);

    }
}
