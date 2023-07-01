package com.example.moavara.Search

import bigbigdw.joaradw.book.BookListDataBest
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry


class BookListData(
    var platform: String = "",
    var title: String = "",
    var writer: String = "",
    var bookImg: String = "",
    var bookCode: String = "",
    var info1: String = "",
    var info2: String = "",
    var info3: String = "",
)

class BestType(
    var title: String? = null,
    var type: String? = null,
)

class WeekendDate(
    var sun: String = "",
    var mon: String = "",
    var tue: String = "",
    var wed: String = "",
    var thur: String = "",
    var fri: String = "",
    var sat: String = "",
)

data class BookListDataBestAnalyze (
    var info1: String = "",
    var info2: String = "",
    var info3: String = "",
    var info4: String = "",
    var number: Int = 0,
    var numInfo1: Int = 0,
    var numInfo2: Int = 0,
    var numInfo3: Int = 0,
    var numInfo4: Int = 0,
    var date: String = "",
    var numberDiff: Int = 0,
    var trophyCount: Int = 0,
)


class BestRankListWeekend(
    var sun: BookListDataBestAnalyze? = null,
    var mon: BookListDataBestAnalyze? = null,
    var tue: BookListDataBestAnalyze? = null,
    var wed: BookListDataBestAnalyze? = null,
    var thur: BookListDataBestAnalyze? = null,
    var fri: BookListDataBestAnalyze? = null,
    var sat: BookListDataBestAnalyze? = null,
    var mode: String = ""
)

class BookListDataBestWeekend(
    var sun: BookListDataBest? = null,
    var mon: BookListDataBest? = null,
    var tue: BookListDataBest? = null,
    var wed: BookListDataBest? = null,
    var thur: BookListDataBest? = null,
    var fri: BookListDataBest? = null,
    var sat: BookListDataBest? = null,
)

class BookListDataBestMonthNum(
    var sun: Int = 0,
    var mon: Int = 0,
    var tue: Int = 0,
    var wed: Int = 0,
    var thur: Int = 0,
    var fri: Int = 0,
    var sat: Int = 0,
)

class CalculNum(
    var num: Int = 0,
    var status: String = ""
)

class EventDetailData(
    var title: String = "",
    var imgFile: String = "",
    var wDate: String = "",
    var sDate: String = "",
    var cntRead: String = "",
    var data: ArrayList<AnayzeData>? = null
)

class EventData(
    var link: String = "",
    var imgfile: String = "",
    var title: String = "",
    var genre: String = "",
    var type: String = "",
    var memo: String = ""
)

data class BestComment(
    var comment: String = "",
    var date: String = ""
)

data class CommunityBoard(
    var title: String = "",
    var nid: String = "",
    var date: String = ""
)

data class BestChart(
    var dateList: MutableList<AnayzeData>? = null,
    var entryList: MutableList<BarEntry>? = null,
    var title: String = "",
    var color: String = ""
)

data class AnayzeData(
    var date: String = "",
    var cntRead: String = "",
)

data class BestLineChart(
    var dateList: MutableList<String>? = null,
    var entryList: MutableList<Entry>? = null,
    var title: String = "",
    var color: String = "",
    var data: ArrayList<String> = ArrayList()
)

data class UserInfo(
    var id: String = "",
    var pw: String = "",
)

data class UserPickEvent(
    var link: String = "",
    var imgfile: String = "",
    var title: String = "",
    var genre: String = "",
    var type: String = "",
    var memo: String = ""
)

data class NewsBX(
    var date: String = "",
    var keyWord: String = "",
    var NewsTitle: String = "",
    var NewsUrl: String = "",
    var summary: String = "",
    var opinion: String = "",
)
