package bigbigdw.joaradw.joara_post

import com.google.gson.annotations.SerializedName

class PostResult {
    @SerializedName("offset")
    private val offset = 0

    @SerializedName("page")
    private val page = 0

    @SerializedName("posts")
    private val posts: List<PostsValue>? = null

    @SerializedName("status")
    private val status = 0

    @SerializedName("total_cnt")
    private val total_cnt: String? = null

    override fun toString(): String {
        return "PostResult{" +
                "offset=" + offset +
                ", page=" + page +
                ", posts='" + posts +
                ", total_cnt='" + total_cnt +
                ", status='" + status  +
                '}'
    }
}

class PostTabResult {
    @SerializedName("offset")
    private val offset = 0

    @SerializedName("page")
    private val page = 0

    @SerializedName("posts")
    private val posts: List<PostsValue>? = null

    @SerializedName("status")
    private val status = 0

    @SerializedName("total_cnt")
    private val total_cnt: String? = null

    override fun toString(): String {
        return "PostResult{" +
                "offset=" + offset +
                ", page=" + page +
                ", posts='" + posts +
                ", total_cnt='" + total_cnt +
                ", status='" + status  +
                '}'
    }
}

internal class PostsValue {
    @SerializedName("post_id")
    var post_id: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("category_name")
    var category_name: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("thumbnail_image")
    var thumbnail_image: String? = null

    @SerializedName("redate")
    var redate: String? = null

    @SerializedName("is_html")
    var is_html: String? = null

    @SerializedName("is_comment")
    var is_comment: String? = null

    @SerializedName("cnt_read")
    var cnt_read: String? = null

    @SerializedName("cnt_recom")
    var cnt_recom: String? = null

    @SerializedName("cnt_comment")
    var cnt_comment: String? = null
}