package bigbigdw.joaradw.login

import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.LoginResult
import bigbigdw.joaradw.util.LoginService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLogin {

    fun postLogin(idCheck : String?, pwCheck: String?): Call<LoginResult?>? {
        val call = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(LoginService::class.java)
            .postRetrofit(
                idCheck,
                pwCheck,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )
        return call
    }
}