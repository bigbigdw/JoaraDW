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

    fun getMDEventList(token: String?): Call<BookEventListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookEventListService::class.java)
            .getRetrofit(
                token
            )
    }

    fun getBookListB(token: String?, store: String?, orderby: String?, showType: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookListBService::class.java)
            .getRetrofit(
                token,
                store,
                orderby,
                showType,
            )
    }

    fun getBookListC(token: String?, sectionMode: String?, showType: String?, notyYear: String?): Call<BookListResultC?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookListCService::class.java)
            .getRetrofit(
                token,
                sectionMode,
                showType,
                notyYear
            )
    }

    fun getBookListD(token: String?, sectionMode: String?, store: String?, orderby: String?, showType: String?, category: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookListDService::class.java)
            .getRetrofit(
                token,
                sectionMode,
                store,
                orderby,
                showType,
                category
            )
    }

    fun getNewBook(token: String?, store: String?, page : Int?): Call<BookListResultC?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewBookService::class.java)
            .getRetrofit(
                token,
                store,
                page,
            )
    }
}