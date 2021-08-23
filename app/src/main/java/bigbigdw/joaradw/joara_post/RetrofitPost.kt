package bigbigdw.joaradw.joara_post

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitPost {
    fun getPostData(token: String?, orderBy: String?): Call<PostTabResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostListService::class.java)
            .getRetrofit(
                token,
                "1",
                "",
                orderBy,
                "10", "1"
            )
    }

    fun postWriteComment(
        postId: String?,
        commentEditText: String?,
        token: String?
    ): Call<PostWriteCommentResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostWriteCommentService::class.java)
            .postRetrofit(
                postId,
                commentEditText,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )
    }

    fun postRecommend(postId: String?, token: String?): Call<PostRecommendResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostRecommendService::class.java)
            .postRetrofit(
                postId,
                "1",
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )
    }

    fun getPostDetailData(postId: String?, token: String?): Call<PostDetailResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostDetailService::class.java)
            .getRetrofit(postId, token)
    }

    fun getCommentData(postId: String?, token: String?, page: String?): Call<PostCommentListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostCommentListService::class.java)
            .getRetrofit(
                postId,
                token,
                page
            )
    }

    fun putCommentEdit(comment: String?,commentId:String?, token: String?): Call<PostWriteCommentResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostEditCommentService::class.java)
            .putRetrofit(
                comment,
                "2,9,6,3,4,5,19",
                commentId,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )
    }

    fun postCommentDelete(commentId: String?, token: String?): Call<PostWriteCommentResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostDeleteCommentService::class.java)
            .postRetrofit(
                "2,9,6,3,4,5,19",
                commentId,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )
    }
}