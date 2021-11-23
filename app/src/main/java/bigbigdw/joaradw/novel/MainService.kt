package bigbigdw.joaradw.novel

import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//메인 북데이터
interface MainBookService {
    @GET(API.INFO_MAIN_INFO_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?
    ): Call<MainBookResult?>?
}

//메인 배너
interface MainBannerService {
    @GET(API.BANNER_HOME_BANNER_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("banner_type") bannerType: String?
    ): Call<MainBannerResult?>?
}
