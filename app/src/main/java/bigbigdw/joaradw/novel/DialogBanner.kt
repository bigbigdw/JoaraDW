package bigbigdw.joaradw.novel

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import bigbigdw.joaradw.R
import android.widget.Button
import java.util.*
import android.webkit.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.Util


class DialogBanner(
    context: Context,
    private val mBtnLeftListener: View.OnClickListener,
    private val mBtnRightListener: View.OnClickListener,
    url: String
) : Dialog(context) {
    lateinit var fakeView: WebView
    lateinit var RealView: WebView
    lateinit var PopupWrap: LinearLayout
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

        fakeView = findViewById(R.id.fakeView)
        RealView = findViewById(R.id.RealView)
        PopupWrap = findViewById(R.id.PopupWrap)

        val mws = fakeView.settings

        mws.loadWithOverviewMode = true

        mws.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        WebView.setWebContentsDebuggingEnabled(true)

        mws.setSupportZoom(false) // 화면 줌 허용 여부

        fakeView.webChromeClient = WebChromeClient()

        fakeView.getSettings().setJavaScriptEnabled(true)
        fakeView.addJavascriptInterface(MyJavaScriptInterface(context), "HtmlViewer")

        fakeView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                fakeView.loadUrl(
                    "javascript:HtmlViewer.resultHTML" +
                            "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');"
                )
            }
        }

        if(context.getSharedPreferences("BANNER_HTML", AppCompatActivity.MODE_PRIVATE).getString("HTML", "").equals("")){
            PopupWrap.visibility = View.GONE
            //처음에는 팝업 HTML 정보가 없어서 바로 내리게 조치
            Handler(Looper.myLooper()!!).postDelayed(
                {
                    dismiss()
                },
                500
            )
        }

        fakeView.loadUrl(popupurl + url)
        Util.loadPostHTMLData(RealView!!, "", context.getSharedPreferences("BANNER_HTML", AppCompatActivity.MODE_PRIVATE).getString("HTML", ""))


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

    internal class MyJavaScriptInterface(private val ctx: Context) {
        @JavascriptInterface
        fun resultHTML(html: String?) {
            if (html != null) {
                val pref = ctx.getSharedPreferences("BANNER_HTML", AppCompatActivity.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("HTML", html.replace("<html><head></head><body>", "").replace("</body></html>",""))
                editor.apply()
            }
        }
    }
}