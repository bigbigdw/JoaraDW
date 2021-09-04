package bigbigdw.joaradw.book

import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.fragment_main.MainBannerResult
import bigbigdw.joaradw.util.LoginResult
import retrofit2.Call
import retrofit2.http.*

//메인 북데이터 이어보기
interface MainHistoryBookService {
    @GET(API.USER_HISTORYBOOKS_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
        @Query("recommend_book") section_mode: String?,
    ): Call<BookListResult?>?
}

//메인 북데이터 취향 저격
interface MainRecommendBookService {
    @GET(API.BOOK_RECOMMEND_LIST_API_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
        @Query("recommend_book") section_mode: String?,
    ): Call<BookListResult?>?
}

//메인 북 MD추천
interface MainMDBookService {
    @GET(API.HOME_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
        @Query("recommend_book") section_mode: String?,
    ): Call<BookListResult?>?
}

//메인 북 웹툰
interface MainMDWebtoonService {
    @GET(API.HOME_WEBTOON_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("page") page: String?,
        @Query("offset") offset: String?,
        @Query("recommend_book") section_mode: String?,
    ): Call<BookListResult?>?
}

//메인 북 이벤트 리스트
interface MainBookEventListService {
    @GET(API.HOME_MD_THEME + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
    ): Call<BookEventListResult?>?
}

//77 페스티벌, 조아라 본
interface MainBookListBService {
    @GET(API.BOOK_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("store") store: String?,
        @Query("orderby") orderby: String?,
        @Query("show_type") showType: String?,
    ): Call<BookListResult?>?
}

//독자가 픽한 작품
interface MainBookListCService {
    @GET(API.HOME_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("section_mode") sectionMode: String?,
        @Query("show_type") showType: String?,
        @Query("noty_year") notyYear: String?,
    ): Call<BookListResultC?>?
}

//베스트
interface MainBookListDService {
    @GET(API.HOME_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("section_mode") sectionMode: String?,
        @Query("store") store: String?,
        @Query("orderby") orderby: String?,
        @Query("show_type") showType: String?,
        @Query("category") category: String?,
    ): Call<BookListResult?>?
}

//최신 작품
interface NewBookService {
    @GET(API.BOOK_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("store") store: String?,
        @Query("page") page: Int?,
        @Query("category") category: String?,
    ): Call<BookListResultC?>?
}

interface NewBookDService {
    @GET(API.BOOK_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("store") store: String?,
        @Query("page") page: Int?,
        @Query("category") category: String?,
    ): Call<BookListResult?>?
}

//선호작 등록
interface FavBookService {
    @FormUrlEncoded
    @POST(API.USER_FAVORATE)
    fun postRetrofit(
        @Field("token") token: String?,
        @Field("book_code") bookCode: String?,
        @Field("category") category: String?,
        @Field("api_key") apiKey: String?,
        @Field("ver") ver: String?,
        @Field("device") device: String?,
        @Field("deviceuid") deviceuid: String?,
        @Field("devicetoken") devicetoken: String?,
    ): Call<BookFavResult?>?
}

//베스트
interface BookListBestService {
    @GET(API.BEST_BOOK_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("best") best: String?,
        @Query("store") store: String?,
        @Query("category") category: String?,
    ): Call<BookListBestResult?>?
}

interface BookListBestServiceA {
    @GET(API.BEST_BOOK_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("best") best: String?,
        @Query("store") store: String?,
        @Query("category") category: String?,
    ): Call<BookListResult?>?
}

//완결
interface BookListFinishService {
    @GET(API.BOOK_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("store") store: String?,
        @Query("orderby") orderby: String?,
        @Query("category") category: String?,
    ): Call<BookListResultC?>?
}

interface BookListFinishDService {
    @GET(API.BOOK_LIST_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("store") store: String?,
        @Query("category") category: String?,
    ): Call<BookListResult?>?
}

//선호작
interface BookListFavService {
    @GET(API.USER_FAVORITE_JOA + HELPER.ETC)
    fun getRetrofit(
        @Query("token") token: String?,
        @Query("orderby") orderby: String?,
        @Query("page") page: Int?
    ): Call<BookListResultC?>?
}



