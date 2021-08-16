package bigbigdw.joaradw.util

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST

import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET




interface CheckTokenService {
    @GET("v1/user/token_check.joa" + HELPER.ETC)
    fun getRetrofit(@Query("token") token: String?): Call<CheckTokenResult?>?
}

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