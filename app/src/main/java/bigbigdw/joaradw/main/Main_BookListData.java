package bigbigdw.joaradw.main;

public class Main_BookListData {
    String Writer;
    String Title;
    String BookImg;
    String IsAdult;
    String IsFinish;
    String IsPremium;
    String IsNobless;
    String Intro;
    String IsFav;
    String BookViewed;
    String BookFavCount;
    String BookRecommend;
    String ReadCount;
    String BookCode;
    String BookCategory;
    int BookBestRank;

    public Main_BookListData(String Writer, String Title, String BookImg, String IsAdult, String IsFinish, String IsPremium, String IsNobless, String Intro, String IsFav, String BookViewed, String BookFavCount, String BookRecommend, int BookBestRank, String ReadCount, String BookCode, String BookCategory) {
        this.Writer = Writer;
        this.Title = Title;
        this.BookImg = BookImg;
        this.IsAdult = IsAdult;
        this.IsFinish = IsFinish;
        this.IsPremium = IsPremium;
        this.IsNobless = IsNobless;
        this.Intro = Intro;
        this.IsFav = IsFav;
        this.IsPremium = IsPremium;
        this.IsNobless = IsNobless;
        this.Intro = Intro;
        this.IsFav = IsFav;
        this.BookViewed = BookViewed;
        this.BookFavCount = BookFavCount;
        this.BookRecommend = BookRecommend;
        this.BookBestRank = BookBestRank;
        this.ReadCount = ReadCount;
        this.BookCode = BookCode;
        this.BookCategory = BookCategory;
    }

    public String getBookCategory() {
        return BookCategory;
    }

    public String getWriter() {
        return Writer;
    }

    public String getTitle() {
        return Title;
    }

    public String getBookImg() {
        return BookImg;
    }

    public String getIsAdult() {
        return IsAdult;
    }

    public String getIsFinish() {
        return IsFinish;
    }

    public String getIsPremium() {
        return IsPremium;
    }

    public String getIsNobless() {
        return IsNobless;
    }

    public String getIntro() {
        return Intro;
    }

    public String getIsFav() {
        return IsFav;
    }

    public String getBookViewed() {
        return BookViewed;
    }

    public String getBookFavCount() {
        return BookFavCount;
    }

    public String getBookRecommend() {
        return BookRecommend;
    }

    public int getBookBestRank() {
        return BookBestRank;
    }

    public String getReadCount() {
        return ReadCount;
    }

    public String getBookCode() {
        return BookCode;
    }
}
