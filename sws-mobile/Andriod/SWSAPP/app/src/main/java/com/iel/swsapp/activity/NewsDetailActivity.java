package com.iel.swsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iel.swsapp.R;
/**
 * Class Full Name  : com.iel.swsapp.activity.NewsDetailActivity
 * Author Name      : yxnne
 * Create Time      : 2017/3
 * Project Name     : SWSAPP
 * Descriptions     : 新闻详情
 */
public class NewsDetailActivity extends ActionbarBackBaseActivity implements View.OnClickListener {
    private String mURL;
    private ProgressBar mPbLoading;


    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        //设置自己的ActionBar
        setMyActionBar(R.id.rl_myactionbar_back,
                R.id.iv_myactionbar_menu,
                R.id.tv_myactionbar_tittle,
                true,getString(R.string.text_app_news));


        mPbLoading = (ProgressBar) findViewById(R.id.pb_loading_news);
        //URL
        mURL = getIntent().getStringExtra("url");

        //webview
        mWebView = (WebView) findViewById(R.id.wv_app_news);
        //mWebView.loadUrl("http://www.baidu.com/");
        mWebView.loadUrl(mURL);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //监听加载完成
        mWebView.setWebViewClient(new MyWebViewClient());
        //加载进度
        mWebView.setWebChromeClient(new MyWebViewChromeClient());

        //final View vPopupBtn = mActionbarMenu;
        mActionbarMenu.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mURL = intent.getStringExtra("url");
        if(mWebView != null ){
            mWebView.loadUrl(mURL);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(mWebView != null&&mURL!=null){
//            mWebView.loadUrl(mURL);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.iv_myactionbar_menu:
                showPopupMenu(mActionbarMenu);
                break;
        }
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_app_info, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                switch (item.getItemId()){
                    case R.id.action_more_news:
                        Intent intent = new Intent(NewsDetailActivity.this,MoreNewsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_reload_news:
                        mWebView.loadUrl(mURL);
                        break;
                    case R.id.action_share_news:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            mPbLoading.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

    }

    class MyWebViewChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if(mPbLoading.getVisibility() == View.GONE){
                mPbLoading.setVisibility(View.VISIBLE);
            }
            mPbLoading.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
}
