package bigbigdw.joaradw.model;

import org.json.JSONException;
import org.json.JSONObject;

import bigbigdw.joaradw.R;
import bigbigdw.joaradw.util.Old_Util;

public class BookInfo {
    String bookImg;
    String title;
    String writer;
    String isAdult;
    String isFinish;
    String isPremium;
    String isNobless;
    String intro;
    String isFavorite;
    String bookCode;
    String categoryKoName;
    String cntPageRead;
    String cntFavorite;
    String cntRecom;
    String historySortno;
    String cntChapter;
    int bookBestRank;
    String writerID;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setIsAdult(String isAdult) {
        this.isAdult = isAdult;
    }

    public String getIsAdult() {
        return isAdult;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsNobless(String isNobless) {
        this.isNobless = isNobless;
    }

    public String getIsNobless() {
        return isNobless;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setCategoryKoName(String categoryKoName) {
        this.categoryKoName = categoryKoName;
    }

    public String getCategoryKoName() {
        return categoryKoName;
    }

    public void setCntPageRead(String cntPageRead) {
        this.cntPageRead = cntPageRead;
    }

    public String getCntPageRead() {
        return cntPageRead;
    }

    public void setCntFavorite(String cntFavorite) {
        this.cntFavorite = cntFavorite;
    }

    public String getCntFavorite() {
        return cntFavorite;
    }

    public void setCntRecom(String cntRecom) {
        this.cntRecom = cntRecom;
    }

    public String getCntRecom() {
        return cntRecom;
    }

    public void setHistorySortno(String historySortno) {
        this.historySortno = historySortno;
    }

    public String getHistorySortno() {
        return historySortno;
    }

    public void setBookBestRank(int bookBestRank) {
        this.bookBestRank = bookBestRank;
    }

    public int getBookBestRank() {
        return bookBestRank;
    }

    public void setCntChapter(String cntChapter) {
        this.cntChapter = cntChapter;
    }

    public String getCtnChapter() {
        return cntChapter;
    }

    public void setWriterID(String writerID) {
        this.writerID = writerID;
    }

    public String getWriterID() {
        return writerID;
    }

    public static BookInfo getParseData(JSONObject obj) throws JSONException {
        BookInfo tempBookInfo  = new BookInfo();

        tempBookInfo.setTitle(obj.getString("subject"));
        tempBookInfo.setBookImg(obj.getString("book_img"));
        tempBookInfo.setWriter(obj.getString("writer_name"));
        tempBookInfo.setWriterID(obj.getString("writer_id"));
        tempBookInfo.setIsAdult(obj.getString("is_adult"));
        tempBookInfo.setIsFinish(obj.getString("is_finish"));
        tempBookInfo.setIsPremium(obj.getString("is_premium"));
        tempBookInfo.setIsNobless(obj.getString("is_nobless"));
        tempBookInfo.setIntro(obj.getString("intro"));
        tempBookInfo.setIsFavorite(obj.getString("is_favorite"));
        tempBookInfo.setBookCode(obj.getString("book_code"));
        tempBookInfo.setCategoryKoName(obj.getString("category_ko_name"));
        tempBookInfo.setCntPageRead(obj.getString("cnt_page_read"));
        tempBookInfo.setCntFavorite(obj.getString("cnt_favorite"));
        tempBookInfo.setCntRecom(obj.getString("cnt_favorite"));
        tempBookInfo.setHistorySortno(Old_Util.getJSONObjStringValue(obj,"history_sortno"));
        tempBookInfo.setCntChapter("총 " + Old_Util.getJSONObjStringValue(obj,"cnt_chapter") + "편");

        return tempBookInfo;
    }

    public static BookInfo getParseBest(int i)  {
        BookInfo tempBookInfoBest  = new BookInfo();

        if(i == 0){
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_1);
        } else if (i == 1){
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_2);
        } else if (i == 2) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_3);
        } else if (i == 3) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_4);
        } else if (i == 4) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_5);
        } else if (i == 5) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_6);
        } else if (i == 6) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_7);
        } else if (i == 7) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_8);
        } else if  (i == 8) {
            tempBookInfoBest.setBookBestRank(R.drawable.icon_best_9);
        }

        return tempBookInfoBest;
    }
}
