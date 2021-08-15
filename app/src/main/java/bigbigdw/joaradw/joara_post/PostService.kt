package bigbigdw.joaradw.joara_post

import bigbigdw.joaradw.etc.HELPER
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("v1/board/post_list.joa" + HELPER.ETC)
    fun getQuery(
            @Query("token") token: String?,
            @Query("category") category: String?,
            @Query("category_id") categoryId: String?,
            @Query("orderby") orderBy: String?,
            @Query("offset") offset: String?,
            @Query("page") page: String?,
    ): Call<PostTabResult?>?
}