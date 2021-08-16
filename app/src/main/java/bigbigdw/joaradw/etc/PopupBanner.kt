package bigbigdw.joaradw.etc

import android.app.Dialog
import android.content.Context
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.etc.API
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import bigbigdw.joaradw.R
import android.webkit.WebView
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.Button
import android.widget.ImageView
import java.util.*

class PopupBanner(
    context: Context,
    private val mBtnLeftListener: View.OnClickListener,
    private val mBtnRightListener: View.OnClickListener,
    url: String
) : Dialog(context) {
    var popup: ImageView? = null
    var url = ""
    var popupurl = HELPER.API + API.BANNER_MAIN_POPUP_JOA + HELPER.ETC + "&banner_id="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.8f
        Objects.requireNonNull(window)!!.attributes = layoutParams
        setContentView(R.layout.popup)
        val popupwebview = findViewById<WebView>(R.id.PopupWebView)
        popup = findViewById(R.id.PopupImg)
        val mws = popupwebview.settings
        mws.loadWithOverviewMode = true
        mws.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        WebView.setWebContentsDebuggingEnabled(true)
        mws.setSupportZoom(false) // 화면 줌 허용 여부
        popupwebview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        popupwebview.webChromeClient = WebChromeClient()
        popupwebview.setInitialScale(133)
        popupwebview.setBackgroundColor(0x00000000)
        popupwebview.loadUrl(popupurl + url)

        //셋팅
        val btnLeft = findViewById<Button>(R.id.BtnLeft)
        val btnRight = findViewById<Button>(R.id.BtnRight)

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        btnLeft.setOnClickListener(mBtnLeftListener)
        btnRight.setOnClickListener(mBtnRightListener)
    }

    //생성자 생성
    init {
        this.url = url
    }
}