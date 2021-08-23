package bigbigdw.joaradw.book

import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.fragment_main.MainBannerResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//메인 북데이터 이어보기
interface MainHistoryBookService {
    @GET("v1/user/historybooks.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
    ): Call<MainBannerResult?>?
}

//메인 북데이터 취향 저격
interface MainRecommedBookService {
    @GET("v1/book/recommend_list_api" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
    ): Call<MainBannerResult?>?
}

//메인 북 리스트
interface MainHomeListBookService {
    @GET("v1/home/list.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
    ): Call<MainBannerResult?>?
}

interface MainBookListBookService {
    @GET("v1/book/list.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
    ): Call<MainBannerResult?>?
}

//메인 북 웹툰
interface MainWebtoonListBookService {
    @GET("v1/home/webtoon_list" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
    ): Call<MainBannerResult?>?
}

