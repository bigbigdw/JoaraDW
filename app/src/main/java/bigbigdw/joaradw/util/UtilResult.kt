package bigbigdw.joaradw.util

import bigbigdw.joaradw.joara_post.PostsCommentValue
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
    var status: String = ""

    @SerializedName("message")
    @Expose
    var message: String = ""
}

//로그아웃
class LogoutResult {
    @SerializedName("status")
    @Expose
    var status: String = ""
}

//로그인 - 유저 정보
class UserValue {
    @SerializedName("nickname")
    @Expose
    var nickname: String = ""

    @SerializedName("token")
    @Expose
    var token: String = ""

    @SerializedName("mana")
    @Expose
    var mana: String = ""

    @SerializedName("expire_cash")
    @Expose
    var expireCash: String = ""

    @SerializedName("cash")
    @Expose
    var cash: String = ""

    @SerializedName("manuscript_coupon")
    @Expose
    var manuscriptCoupon: String = ""

    @SerializedName("support_coupon")
    @Expose
    var supportCoupon: String = ""

    @SerializedName("member_id")
    @Expose
    var memberId: String = ""

    @SerializedName("profile")
    @Expose
    var profile: String = ""

    @SerializedName("grade")
    @Expose
    var grade: String = ""
}

//인덱스 API
class IndexAPIResult {
    @SerializedName("status")
    @Expose
    var status: String = ""

    @SerializedName("banner")
    @Expose
    val banner: List<String>? = null

    @SerializedName("main_menu")
    @Expose
    val mainMenu: List<MainMenuValue>? = null
}

//메인 메뉴
class MainMenuValue {
    @SerializedName("menu_ver")
    @Expose
    var menuVer: String = ""

    @SerializedName("MainTab")
    @Expose
    var MainTab: List<MainTabInfoValue>? = null

    @SerializedName("TabInfo")
    @Expose
    var TabInfo: TabInfoValue? = null
}

//탭 정보
class TabInfoValue {
    @SerializedName("tabname")
    @Expose
    var tabname: String = ""
}

//매인 정보
class MainTabInfoValue {
    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("position")
    @Expose
    var position: String = ""
}
