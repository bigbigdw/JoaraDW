package bigbigdw.joaradw.book

import bigbigdw.joaradw.fragment_main.MainBannerValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//북 리스트
class BookListResult {
    @Expose
    val books: List<BooksListValue>? = null
    @Expose
    val webtoons: List<WebtoonListValue>? = null
}

//웹툰 상세
class WebtoonListValue {
    @SerializedName("webtoon_img")
    @Expose
    var webtoon_img: String? = null

    @SerializedName("webtoon_title")
    @Expose
    var webtoon_title: String? = null

    @SerializedName("is_adult")
    @Expose
    var is_adult: String? = null
}

//작품 상세
class BooksListValue {
    @SerializedName("book_code")
    @Expose
    var bookCode: String? = null

    @SerializedName("book_img")
    @Expose
    var bookImg: String? = null

    @SerializedName("history_sortno")
    @Expose
    var historySortno: String? = null

    @SerializedName("subject")
    @Expose
    var subject: String? = null

    @SerializedName("writer_name")
    @Expose
    var writerName: String? = null

    @SerializedName("is_adult")
    @Expose
    var is_adult: String? = null
}

//북 이벤트
class BookEventListResult {
    @SerializedName("md_theme")
    @Expose
    val mdTheme: List<BookEventListValue>? = null
}

//북 이벤트 상세
class BookEventListValue {
    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("enddate")
    @Expose
    var enddate: String? = null

    @SerializedName("event_img")
    @Expose
    var eventImg: String? = null

    @SerializedName("idx")
    @Expose
    var idx: String? = null

    @SerializedName("theme_sub_title")
    @Expose
    var themeSubTitle: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null
}

//북 리스트 C
class BookListResultC {
    @SerializedName("books")
    @Expose
    val books: List<BooksListValueC>? = null
}

//작품 상세
class BooksListValueC {
    @SerializedName("writer_name")
    @Expose
    var writerName: String? = null

    @SerializedName("subject")
    @Expose
    var subject: String? = null

    @SerializedName("book_img")
    @Expose
    var bookImg: String? = null

    @SerializedName("is_adult")
    @Expose
    var isAdult: String? = null

    @SerializedName("is_finish")
    @Expose
    var isFinish: String? = null

    @SerializedName("is_premium")
    @Expose
    var isPremium: String? = null

    @SerializedName("is_nobless")
    @Expose
    var isNobless: String? = null

    @SerializedName("intro")
    @Expose
    var intro: String? = null

    @SerializedName("is_favorite")
    @Expose
    var isFavorite: String? = null

    @SerializedName("book_code")
    @Expose
    var bookCode: String? = null

    @SerializedName("category_ko_name")
    @Expose
    var categoryKoName: String? = null

    @SerializedName("cnt_chapter")
    @Expose
    var cntChapter: String? = null
}