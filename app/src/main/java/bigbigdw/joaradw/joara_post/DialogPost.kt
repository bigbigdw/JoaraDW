package bigbigdw.joaradw.joara_post

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import com.synnapps.carouselview.CarouselView
import android.widget.FrameLayout
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.ImageView
import com.synnapps.carouselview.ImageListener

class DialogPost(context: Context, var postBannerURLs: List<String?>?, var imgUrl: String?, var type: String?) :
    Dialog(context) {

    var popupImg: ImageView? = null
    var postBanner: CarouselView? = null
    var iivew_exit: ImageView? = null
    var dialogWrap: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_post)

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.9f
        window!!.attributes = layoutParams

        popupImg = findViewById(R.id.PopupImg)
        postBanner = findViewById(R.id.Carousel_PostBanner)
        iivew_exit = findViewById(R.id.iivew_exit)
        dialogWrap = findViewById(R.id.dialogWrap)
        dialogWrap!!.setOnClickListener { dismiss() }

        if (type == "CAROUSEL") {
            postBanner!!.setImageListener(imageListener)
            postBanner!!.pageCount = (postBannerURLs?.size ?: postBanner!!.setBackgroundColor(Color.TRANSPARENT)) as Int
            popupImg!!.visibility = View.GONE
            postBanner!!.visibility = View.VISIBLE
        } else {
            Glide.with(context.applicationContext).load(imgUrl).into(popupImg!!)
            popupImg!!.visibility = View.VISIBLE
            postBanner!!.visibility = View.GONE
        }
        iivew_exit!!.setOnClickListener { dismiss() }
    }

    private var imageListener = ImageListener { position: Int, imageView: ImageView ->
        imageView.adjustViewBounds = true
        val vto = imageView.viewTreeObserver
        vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                imageView.viewTreeObserver.removeOnPreDrawListener(this)
                val layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    imageView.measuredWidth
                )
                postBanner!!.layoutParams = layoutParams
                imageView.setBackgroundColor(Color.TRANSPARENT)
                return true
            }
        })
        Glide.with(getContext().applicationContext).load(postBannerURLs?.get(position)).into(imageView)
    }
}