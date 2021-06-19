package bigbigdw.joaradw.etc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.Objects;

import bigbigdw.joaradw.R;


public class PopupBanner extends Dialog {

    private View.OnClickListener mBtnLeftListener;
    private View.OnClickListener mBtnRightListener;
    ImageView popup;
    String url = "";
    String popupurl = HELPER.API + API.BANNER_MAIN_POPUP_JOA + HELPER.ETC + "&banner_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        Objects.requireNonNull(getWindow()).setAttributes(layoutParams);

        setContentView(R.layout.popup);

        WebView popupwebview = findViewById(R.id.PopupWebView);
        popup = findViewById(R.id.PopupImg);

        WebSettings mws= popupwebview.getSettings();
        mws.setLoadWithOverviewMode(true);
        mws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        WebView.setWebContentsDebuggingEnabled(true);
        mws.setSupportZoom(false); // 화면 줌 허용 여부

        popupwebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        popupwebview.setWebChromeClient(new WebChromeClient());
        popupwebview.setInitialScale(133);
        popupwebview.setBackgroundColor(0x00000000);
        popupwebview.loadUrl(popupurl + url);

        //셋팅
        Button btnLeft = findViewById(R.id.BtnLeft);
        Button btnRight = findViewById(R.id.BtnRight);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        btnLeft.setOnClickListener(mBtnLeftListener);
        btnRight.setOnClickListener(mBtnRightListener);
    }

    //생성자 생성
    public PopupBanner(@NonNull Context context, View.OnClickListener btnLeftListener, View.OnClickListener btnRightListener, String url) {
        super(context);
        this.mBtnLeftListener = btnLeftListener;
        this.mBtnRightListener = btnRightListener;
        this.url = url;
    }
}
