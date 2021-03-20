package Bigbigdw.JoaraDW.Etc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import Bigbigdw.JoaraDW.R;
import Bigbigdw.JoaraDW.Test.Test_RestAPI;


public class Popup extends Dialog {

    private View.OnClickListener mBtnLeftListener;
    private View.OnClickListener mBtnRightListener;
    private WebView PopupWebView;
    ImageView Popup;
    String Banner = "https://api.joara.com/v1/banner/main_popup.joa?api_key=mw_8ba234e7801ba288554ca07ae44c7&ver=2.6.3&device=mw&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604&devicetoken=mw&banner_id=15967";

    public Popup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);

        setContentView(R.layout.popup);

        PopupWebView= findViewById(R.id.PopupWebView);
        Popup= findViewById(R.id.PopupImg);

        WebSettings mws=PopupWebView.getSettings();
        mws.setLoadWithOverviewMode(true);
        mws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        WebView.setWebContentsDebuggingEnabled(true);
        mws.setSupportZoom(false); // 화면 줌 허용 여부

        PopupWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        PopupWebView.setWebChromeClient(new WebChromeClient());
        PopupWebView.setInitialScale(133);
        PopupWebView.setBackgroundColor(0x00000000);
        PopupWebView.loadUrl(Banner);

        Glide.with(Popup).load("https://cf.joara.com/banner_file/20210312_094353.jpg").into(Popup);

        //셋팅
        Button BtnLeft = findViewById(R.id.BtnLeft);
        Button BtnRight = findViewById(R.id.BtnRight);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        BtnLeft.setOnClickListener(mBtnLeftListener);
        BtnRight.setOnClickListener(mBtnRightListener);

    }

    //생성자 생성
    public Popup(@NonNull Context context, View.OnClickListener BtnLeftListener, View.OnClickListener BtnRightListener) {
        super(context);
        this.mBtnLeftListener = BtnLeftListener;
        this.mBtnRightListener = BtnRightListener;
    }
}
