package bigbigdw.joaradw.book

class BookListDataABD(
    var writer: String? = null,
    var title: String? = null,
    var bookImg: String? = null,
    var bookCode: String? = null,
    var historySortno: String? = null,
    var isAdult: String? = null,
    var listType: String? = null
)

class BookEventListData(
    var content: String? = null,
    var eventImg: String? = null,
    var enddate: String? = null,
    var idx: String? = null,
    var themeSubTitle: String? = null,
    var title: String? = null,
)

class BookListDataC(
    var writer: String ? = null,
    var title: String ? = null,
    var bookImg: String ? = null,
    var isAdult: String ? = null,
    var isFinish: String ? = null,
    var isPremium: String ? = null,
    var isNobless: String ? = null,
    var intro: String ? = null,
    var isFav: String ? = null,
    var bookCode: String ? = null,
    var bookCategory: String ? = null,
    var cntChapter: String ? = null
)


class BookListDataZ(
    var writer: String,
    var title: String,
    var bookImg: String,
    var isAdult: String,
    var isFinish: String,
    var isPremium: String,
    var isNobless: String,
    var intro: String,
    var isFav: String,
    var bookViewed: String,
    var bookFavCount: String,
    var bookRecommend: String,
    var bookBestRank: Int,
    var readCount: String,
    var bookCode: String,
    var bookCategory: String,
    var cntChapter: String
)
