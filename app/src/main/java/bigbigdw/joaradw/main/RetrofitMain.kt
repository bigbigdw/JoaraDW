package bigbigdw.joaradw.main

import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.fragment_main.MainBannerResult
import bigbigdw.joaradw.util.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMain {
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

    fun getIndexAPI(menuVer: String?): Call<IndexAPIResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(IndexAPIService::class.java)
            .getRetrofit(
                menuVer,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )
    }

    fun onClickLogout(token: String?): Call<LogoutResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(LogoutService::class.java)
            .getRetrofit(
                "22%2C2",
                token,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )
    }

    fun loginCheck(usertoken: String?): Call<CheckTokenResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CheckTokenService::class.java)
            .getRetrofit(usertoken)
    }
}

