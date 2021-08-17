package bigbigdw.joaradw.joara_post

class PostListData(
    var categoryName: String?,
    var title: String?,
    var postImg: String?,
    var postId: String?,
    var cntRead: String?,
    var cntRecom: String?,
    var cntComment: String?,
)

class PostCommentData(
    var commentImg: String?,
    var commentWriter: String?,
    var commentDate: String?,
    var commentId: String?,
    var comment: String?,
    var userId: String?,
)