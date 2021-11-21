package bigbigdw.joaradw.novel

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//메인 북데이터
interface MainBookService {
    @GET("api/info/main_info.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?
    ): Call<MainBookResult?>?
}

//메인 배너
interface MainBannerService {
    @GET("v1/banner/home_banner.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("banner_type") bannerType: String?
    ): Call<MainBannerResult?>?
}
