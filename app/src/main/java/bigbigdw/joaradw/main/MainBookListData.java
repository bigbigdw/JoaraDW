package bigbigdw.joaradw.main;

public class MainBookListData {
    String writer;
    String title;
    String bookImg;
    String isAdult;
    String isFinish;
    String isPremium;
    String isNobless;
    String intro;
    String isFav;
    String bookViewed;
    String bookFavCount;
    String bookRecommend;
    String readCount;
    String bookCode;
    String bookCategory;
    int bookBestRank;

    public MainBookListData(String writer, String title, String bookImg, String isAdult, String isFinish, String isPremium, String isNobless, String intro, String isFav, String bookViewed, String bookFavCount, String bookRecommend, int bookBestRank, String readCount, String bookCode, String bookCategory) {
        this.writer = writer;
        this.title = title;
        this.bookImg = bookImg;
        this.isAdult = isAdult;
        this.isFinish = isFinish;
        this.isPremium = isPremium;
        this.isNobless = isNobless;
        this.intro = intro;
        this.isFav = isFav;
        this.bookViewed = bookViewed;
        this.bookFavCount = bookFavCount;
        this.bookRecommend = bookRecommend;
        this.bookBestRank = bookBestRank;
        this.readCount = readCount;
        this.bookCode = bookCode;
        this.bookCategory = bookCategory;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getBookImg() {
        return bookImg;
    }

    public String getIsAdult() {
        return isAdult;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public String getIsNobless() {
        return isNobless;
    }

    public String getIntro() {
        return intro;
    }

    public String getIsFav() {
        return isFav;
    }

    public String getBookViewed() {
        return bookViewed;
    }

    public String getBookFavCount() {
        return bookFavCount;
    }

    public String getBookRecommend() {
        return bookRecommend;
    }

    public int getBookBestRank() {
        return bookBestRank;
    }

    public String getReadCount() {
        return readCount;
    }

    public String getBookCode() {
        return bookCode;
    }
}
