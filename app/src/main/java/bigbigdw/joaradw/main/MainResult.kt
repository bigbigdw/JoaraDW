package bigbigdw.joaradw.fragment_main

import bigbigdw.joaradw.joara_post.PostsCommentValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


//메인 북 데이터
class MainBookResult {
    @SerializedName("main_info")
    @Expose
    val mainInfo: List<MainInfoValue>? = null

    @SerializedName("status")
    @Expose
    val status = 0

    @SerializedName("home_ver")
    @Expose
    var homeVer : String? = null
}

//메인 북 데이터 상세
class MainInfoValue {
    @SerializedName("section_api_url")
    @Expose
    var sectionApiUrl: String? = null

    @SerializedName("section_category")
    var sectionCategory: String? = null

    @SerializedName("section_sub_type")
    @Expose
    var sectionSubType: String? = null

    @SerializedName("section_type")
    @Expose
    var sectionType: String? = null
}

//메인 배너
class MainBannerResult {
    @SerializedName("banner")
    @Expose
    val banner: List<MainBannerValue>? = null
}

//메인 배너 상세
class MainBannerValue {
    @SerializedName("imgfile")
    @Expose
    var imgfile: String? = null
}