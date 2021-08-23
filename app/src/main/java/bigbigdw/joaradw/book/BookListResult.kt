package bigbigdw.joaradw.book

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


//작품 리스트
class BookListAResult {
    @SerializedName("books")
    @Expose
    val banner: List<BooksValue>? = null
}

//작품 리스트 상세
class BooksValue {
    @SerializedName("book_code")
    @Expose
    var bookCode: String? = null

    @SerializedName("book_img")
    @Expose
    var bookImg: String? = null

    @SerializedName("cnt_chapter")
    @Expose
    var cntChapter: String? = null

    @SerializedName("subject")
    @Expose
    var subject: String? = null

    @SerializedName("writer_name")
    @Expose
    var writerName: String? = null
}