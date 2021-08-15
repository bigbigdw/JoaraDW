package bigbigdw.joaradw.joara_post

import com.google.gson.annotations.SerializedName

class PostTabResult {
    @SerializedName("offset")
    val offset = 0

    @SerializedName("page")
    val page = 0

    @SerializedName("posts")
    val posts: List<PostsValue>? = null

    @SerializedName("status")
    val status = 0

    @SerializedName("total_cnt")
    val totalCnt: String? = null
}

class PostsValue {
    @SerializedName("post_id")
    var postId: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("category_name")
    var categoryName: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("thumbnail_image")
    var thumbnailImage: String? = null

    @SerializedName("redate")
    var redate: String? = null

    @SerializedName("is_html")
    var isHtml: String? = null

    @SerializedName("is_comment")
    var isComment: String? = null

    @SerializedName("cnt_read")
    var cntRead: String? = null

    @SerializedName("cnt_recom")
    var cntRecom: String? = null

    @SerializedName("cnt_comment")
    var cntComment: String? = null
}