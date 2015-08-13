    package com.modernizedmedia.webview;

    import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


    public class MainActivity extends Activity {

        private WebView mWebView;

        final Activity activity = this;

        private ProgressDialog progress;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_PROGRESS);
            setContentView(R.layout.activity_main);

            Button button= (Button) findViewById(R.id.reset);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetView();
                }
            });

            mWebView = (WebView) findViewById(R.id.activity_main_webview);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            progress = new ProgressDialog(this);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setMax(100);
            progress.setMessage(getString(R.string.loading));

            // Force links and redirects to open in the WebView instead of in a browser
            mWebView.setWebViewClient(new HelloWebViewClient());
            mWebView.setWebChromeClient(new ChromeClient());
            this.resetView();
        }

        @Override
        public void onBackPressed() {
            this.resetView();
        }

        public void resetView() {
            mWebView.loadUrl(getString(R.string.url));
        }

        @Override
        public void onWindowFocusChanged(boolean hasFocus) {
            super.onWindowFocusChanged(hasFocus);
            if (hasFocus) {
                this.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
        }

        private class ChromeClient extends WebChromeClient {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                progress.show();
                if (newProgress == 100) {
                    progress.dismiss();
                }
                super.onProgressChanged(view, newProgress);
            }
        }

        private class HelloWebViewClient extends WebViewClient {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(getApplicationContext(),
                        "Error: " + description + " " + failingUrl,
                        Toast.LENGTH_LONG).show();

            }
        }
    }