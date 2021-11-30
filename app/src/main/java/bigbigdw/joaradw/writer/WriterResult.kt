package bigbigdw.joaradw.writer

import bigbigdw.joaradw.book.BooksListValueC
import bigbigdw.joaradw.util.MainMenuValue
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//작가 작품 갯수
class WriterBookCountResult {
    @SerializedName("bookcount")
    @Expose
    val bookCount: WriterBookCount? = null
}

//작가 작품 갯수 상세
class WriterBookCount {
    @SerializedName("exer_count")
    @Expose
    var exerCount: String? = null

    @SerializedName("finish_count")
    var finishCount: String? = null

    @SerializedName("series_count")
    @Expose
    var seriesCount: String? = null

    @SerializedName("short_count")
    @Expose
    var shortCount: String? = null
}

//작가 펜
class WriterMemberLevelResult {

    @SerializedName("writer_level")
    @Expose
    val writerLevel: WriterMemberLevel? = null

}

//작가 펜 상세
class WriterMemberLevel {
    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("level")
    var level: String? = null

    @SerializedName("pen_exp")
    @Expose
    var penExp: String? = null

    @SerializedName("pen_name")
    @Expose
    var penName: String? = null

    @SerializedName("1")
    @Expose
    val memberLevel: MemberPenLevel? = null
}

class MemberPenLevel {
    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("level")
    var level: String? = null

    @SerializedName("pen_exp")
    @Expose
    var penExp: String? = null

    @SerializedName("pen_name")
    @Expose
    var penName: String? = null

    @SerializedName("member_level")
    @Expose
    val memberLevel: WriterMemberLevel? = null
}

class MyListResult {
    @SerializedName("books")
    @Expose
    val books: List<BooksListValueMyList>? = null
}

//작품 상세
class BooksListValueMyList {
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

    @SerializedName("is_premium")
    @Expose
    var isPremium: String? = null

    @SerializedName("is_nobless")
    @Expose
    var isNobless: String? = null

    @SerializedName("book_code")
    @Expose
    var bookCode: String? = null

    @SerializedName("category_ko_name")
    @Expose
    var categoryKoName: String? = null

    @SerializedName("cnt_chapter")
    @Expose
    var cntChapter: String? = null

    @SerializedName("created")
    @Expose
    var created: String? = null

    @SerializedName("cnt_page_read")
    @Expose
    var cntPageRead: String? = null

    @SerializedName("cnt_favorite")
    @Expose
    var cntFavorite: String? = null

    @SerializedName("cnt_recom")
    @Expose
    var cntRecom: String? = null
}