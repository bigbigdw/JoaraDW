package bigbigdw.joaradw.writer

import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.novel.MainBannerResult
import bigbigdw.joaradw.novel.MainBookResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//작가 북 갯수 데이터
interface WrtierBookCount {
    @GET(API.BOOK_CATEGORY_BOOKCOUNT_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?
    ): Call<WriterBookCountResult?>?
}

//작가 레벨
interface WrtierLevel {
    @GET(API.BOOK_WRITER_LEVEL_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?
    ): Call<WriterMemberLevelResult?>?
}

interface MyBookList {
    @GET(API.BOOK_MYLIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("store") store: String?,
        @Query("finish") finish: String?,
        @Query("class") classString: String?
    ): Call<MyListResult?>?
}