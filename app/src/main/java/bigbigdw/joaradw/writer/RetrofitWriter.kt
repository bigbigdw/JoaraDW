package bigbigdw.joaradw.writer

import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.novel.MainBannerResult
import bigbigdw.joaradw.novel.MainBannerService
import bigbigdw.joaradw.novel.MainBookResult
import bigbigdw.joaradw.novel.MainBookService
import bigbigdw.joaradw.util.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWriter {
    fun getMainBanner(token: String?, bannerType: String?): Call<MainBannerResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBannerService::class.java)
            .getRetrofit(
                token,
                bannerType
            )
    }
}