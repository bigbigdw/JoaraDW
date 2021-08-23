package bigbigdw.joaradw.book

import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.fragment_main.MainBannerResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBookList {
    fun getUserHistoryBooks(token: String?, page: String?, offset: String?): Call<MainBannerResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainHistoryBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset
            )
    }
}