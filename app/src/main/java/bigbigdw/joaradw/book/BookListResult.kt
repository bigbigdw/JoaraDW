package bigbigdw.joaradw.book

import bigbigdw.joaradw.fragment_main.MainBannerValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//이어보기
class BookListResult {

    @Expose
    val books: List<BooksListValue>? = null

    @Expose
    val webtoons: List<WebtoonListValue>? = null
}

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
}