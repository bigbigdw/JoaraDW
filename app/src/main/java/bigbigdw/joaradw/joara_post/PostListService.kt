package bigbigdw.joaradw.joara_post

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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

interface PostDetailService {
    @GET("v1/board/post_detail.joa" + HELPER.ETC)
    fun getRetrofit(
        @Query("post_id") post_id: String?,
    ): Call<PostDetailResult?>?
}