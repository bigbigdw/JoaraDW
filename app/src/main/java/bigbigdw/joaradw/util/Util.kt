package bigbigdw.joaradw.util

import android.util.Base64
import android.webkit.WebView
import java.util.*

object Util {
    //날짜 변환 타입
    fun changeDateType(string: String?, type : String?): String {
        var string = string
        if(type.equals("0000.00.00")){
            string = string?.substring(2, 4) + '.' + string?.substring(5, 7) + '.' + string?.substring(8, 10)
            return string
        } else if(type.equals("00.00.00")) {
            string = string?.substring(2, 4) + '.' + string?.substring(4, 6) + '.' + string?.substring(6, 8)
            return string
        } else if(type.equals("00.00.00 00:00")) {
            string = string?.substring(2, 4) + '.' + string?.substring(4, 6) + '.' + string?.substring(6, 8) + ' ' + string?.substring(8, 10) + ':' + string?.substring(10, 12)
            return string
        } else {
            string = string?.substring(2, 4) + '-' + string?.substring(5, 7) + '-' + string?.substring(8, 10)
            return string
        }
    }

    /**
     * 포스트 HTML 스타일
     */
    fun loadPostHTMLData(webView: WebView, bgColor: String?, data: String?) {
        if (data!!.lowercase(Locale.getDefault()).indexOf("<html") > 0) {
            val encodedHtml = Base64.encodeToString(data.toByteArray(), Base64.NO_PADDING)
            webView.loadData(encodedHtml, "text/html; charset=UTF-8", "base64")
        } else {
            val loadData =  createPostHtmlBody(data,bgColor)
            val encodedHtml = Base64.encodeToString(loadData.toByteArray(), Base64.NO_PADDING)
            webView.loadData(encodedHtml, "text/html; charset=UTF-8", "base64")
        }
    }

    private fun createPostHtmlBody(data: String?, bgColor: String?): String {
        val sb = StringBuffer("<HTML>")
        sb.append("<HEAD>")
        sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>")
        sb.append("<meta name=\"viewport\" content=\"width=device-width\">")
        sb.append("</HEAD>")
        sb.append("<STYLE>img{max-width:100%;width:auto;height:auto;}a{display:contents;}</STYLE>")
        sb.append("<BODY style=\"margin:0; padding:0px; background-color:$bgColor;\">")
        sb.append("<DIV style=\"font-size:15px; color:#666666;\">")
        sb.append(data)
        sb.append("</DIV>")
        sb.append("</BODY>")
        sb.append("</HTML>")
        return sb.toString()
    }

}