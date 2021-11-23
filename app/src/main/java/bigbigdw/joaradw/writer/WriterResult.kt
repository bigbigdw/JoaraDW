package bigbigdw.joaradw.writer

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

//작가 작품 갯수
class WriterMemberLevelResult {
    @SerializedName("member_level")
    @Expose
    val member_level: WriterMemberLevel? = null
}

//작가 작품 갯수 상세
class WriterMemberLevel {
    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("level")
    var level: String? = null

    @SerializedName("pen_exp")
    @Expose
    var pen_exp: String? = null

    @SerializedName("pen_name")
    @Expose
    var pen_name: String? = null
}