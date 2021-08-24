package bigbigdw.joaradw.book

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBookList {
    fun getUserHistoryBooks(token: String?, page: String?, offset: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainHistoryBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset,
                ""
            )
    }

    fun getRecommendBooks(token: String?, page: String?, offset: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainRecommendBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset,
                ""
            )
    }

    fun getMDBooks(token: String?, page: String?, offset: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainMDBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset,
                "recommend_book"
            )
    }

    fun getMDWebtoon(token: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainMDWebtoonService::class.java)
            .getRetrofit(
                token,
                "",
                "",
                ""
            )
    }
}