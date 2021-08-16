package bigbigdw.joaradw.util

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// 토큰 체크
class CheckTokenResult {
    @SerializedName("status")
    @Expose
    val status = 0
}

//로그인
class LoginResult {
    @SerializedName("user")
    @Expose
    val user: UserValue? = null
}

//로그인 - 유저 정보
class UserValue {
    @SerializedName("nickname")
    @Expose
    var nickname: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null
}