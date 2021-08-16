package bigbigdw.joaradw.joara_post

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.*

//포스트 리스트
interface PostListService {
    @GET("v1/board/post_list.joa" + HELPER.ETC)
    fun getRetrofit(
            @Query("token") token: String?,
            @Query("category") category: String?,
            @Query("category_id") categoryId: String?,
            @Query("orderby") orderBy: String?,
            @Query("offset") offset: String?,
            @Query("page") page: String?,
    ): Call<PostTabResult?>?
}

//포스트 상세
interface PostDetailService {
    @GET("v1/board/post_detail.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("post_id") postId: String?,
        @Query("token") token: String?,
    ): Call<PostDetailResult?>?
}


//포스트 상세 추천
interface PostRecommendService {
    @FormUrlEncoded
    @POST("v1/board/post_recommend.joa")
    fun postRetrofit(
        @Field("post_id") postId: String?,
        @Field("category") category: String?,
        @Field("api_key") apiKey: String?,
        @Field("ver") ver: String?,
        @Field("device") device: String?,
        @Field("deviceuid") deviceUid: String?,
        @Field("devicetoken") deviceToken: String?,
        @Field("token") token: String?,
    ): Call<PostRecommendResult?>?
}

//포스트 댓글 리스트
interface PostCommentListService {
    @GET("v1/board/post_comment.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("post_id") postId: String?,
        @Query("token") token: String?,
    ): Call<PostCommentListResult?>?
}