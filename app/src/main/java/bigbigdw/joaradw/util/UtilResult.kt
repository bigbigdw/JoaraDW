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

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}

//로그인 - 유저 정보
class UserValue {
    @SerializedName("nickname")
    @Expose
    var nickname: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("mana")
    @Expose
    var mana: String? = null

    @SerializedName("expire_cash")
    @Expose
    var expireCash: String? = null

    @SerializedName("cash")
    @Expose
    var cash: String? = null

    @SerializedName("manuscript_coupon")
    @Expose
    var manuscriptCoupon: String? = null

    @SerializedName("support_coupon")
    @Expose
    var supportCoupon: String? = null

    @SerializedName("member_id")
    @Expose
    var memberId: String? = null

    @SerializedName("profile")
    @Expose
    var profile: String? = null

}