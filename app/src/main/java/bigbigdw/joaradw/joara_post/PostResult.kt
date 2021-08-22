package bigbigdw.joaradw.joara_post

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//포스트 탭
class PostTabResult {
    @SerializedName("offset")
    @Expose
    val offset = 0

    @SerializedName("page")
    @Expose
    val page = 0

    @SerializedName("posts")
    @Expose
    val posts: List<PostsValue>? = null

    @SerializedName("status")
    @Expose
    val status = 0

    @SerializedName("total_cnt")
    val totalCnt: String? = null
}

//포스트 탭 - 포스트 데이터
class PostsValue {
    @SerializedName("post_id")
    @Expose
    var postId: String? = null

    @SerializedName("nickname")
    var nickname: String? = null

    @SerializedName("category_name")
    @Expose
    var categoryName: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("thumbnail_image")
    @Expose
    var thumbnailImage: String? = null

    @SerializedName("redate")
    @Expose
    var redate: String? = null

    @SerializedName("is_html")
    var isHtml: String? = null

    @SerializedName("is_comment")
    var isComment: String? = null

    @SerializedName("cnt_read")
    @Expose
    var cntRead: String? = null

    @SerializedName("cnt_recom")
    @Expose
    var cntRecom: String? = null

    @SerializedName("cnt_comment")
    @Expose
    var cntComment: String? = null
}

//포스트 상세
class PostDetailResult {

    @SerializedName("post")
    @Expose
    val post: PostDetailData? = null
}

// 포스트 상세 데이터
class PostDetailData {
    @SerializedName("category_name")
    @Expose
    var categoryName: String? = null

    @SerializedName("cnt_comment")
    @Expose
    var cntComment: String? = null

    @SerializedName("cnt_read")
    @Expose
    var cntRead: String? = null

    @SerializedName("cnt_recom")
    @Expose
    var cntRecom: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("is_comment")
    @Expose
    var isComment: String? = null

    @SerializedName("is_recom")
    @Expose
    var isRecom: String? = null

    @SerializedName("is_slideshow")
    @Expose
    var isSlideshow: String? = null

    @SerializedName("redate")
    @Expose
    var redate: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("slideshow_data")
    @Expose
    val slideshowData: List<PostDetailSlideShow>? = null
}

//포스트 상세 - 슬라이드
class PostDetailSlideShow {
    @SerializedName("slideshow_image")
    @Expose
    var slideshowImage: String? = null
}

//포스트 추천
class PostRecommendResult {
    @SerializedName("status")
    val status = 0

    @SerializedName("message")
    @Expose
    var message: String? = null
}

//포스트 댓글 리스트
class PostCommentListResult {

    @SerializedName("comments")
    @Expose
    val comments: List<PostsCommentValue>? = null

    @SerializedName("status")
    @Expose
    val status = 0

    @SerializedName("total_cnt")
    val totalCnt = 0
}

//포스트 댓글 상세
class PostsCommentValue {
    @SerializedName("comment")
    @Expose
    var comment: String? = null

    @SerializedName("comment_id")
    var comment_id: String? = null

    @SerializedName("created")
    @Expose
    var created: String? = null

    @SerializedName("member_name")
    @Expose
    var member_name: String? = null

    @SerializedName("profile")
    @Expose
    var profile: String? = null

    @SerializedName("member_id")
    @Expose
    var memberId: String? = null
}

//포스트 댓글 쓰기
class PostWriteCommentResult {
    @SerializedName("status")
    val status = 0

    @SerializedName("message")
    @Expose
    var message: String? = null
}
