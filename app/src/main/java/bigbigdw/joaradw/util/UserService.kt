package bigbigdw.joaradw.util

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST

import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

//토큰 체크
interface CheckTokenService {
    @GET("v1/user/token_check.joa" + HELPER.ETC)
    fun getRetrofit(@Query("token") token: String?): Call<CheckTokenResult?>?
}

//로그인
interface LoginService {
    @FormUrlEncoded
    @POST("v1/user/auth.joa")
    fun postRetrofit(
        @Field("member_id") memberId: String?,
        @Field("passwd") passwd: String?,
        @Field("api_key") apiKey: String?,
        @Field("ver") ver: String?,
        @Field("device") device: String?,
        @Field("deviceuid") deviceuid: String?,
        @Field("devicetoken") devicetoken: String?,
    ): Call<LoginResult?>?
}

//로그아웃
interface LogoutService {
    @GET("v1/user/deauth.joa")
    fun getRetrofit(
        @Query("category") category: String?,
        @Query("token") token: String?,
        @Query("api_key") apiKey: String?,
        @Query("ver") ver: String?,
        @Query("device") device: String?,
        @Query("deviceuid") deviceuid: String?,
        @Query("devicetoken") devicetoken: String?,
    ): Call<LogoutResult?>?
}

//인덱스 API
interface IndexAPIService {
    @GET("api/info/index.joa")
    fun getRetrofit(
        @Query("menu_ver") menuVer: String?,
        @Query("api_key") apiKey: String?,
        @Query("ver") ver: String?,
        @Query("device") device: String?,
        @Query("deviceuid") deviceuid: String?,
        @Query("devicetoken") devicetoken: String?,
    ): Call<IndexAPIResult?>?
}