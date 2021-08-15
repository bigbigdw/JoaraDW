package bigbigdw.joaradw.util

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import retrofit2.Call
import retrofit2.http.GET
import android.R.id




class CheckTokenResult {
    @SerializedName("status")
    val status = 0
}

class LoginResult {
    @SerializedName("user")
    val user: UserValue? = null

}

class UserValue {
    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("token")
    var token: String? = null
}