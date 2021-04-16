package Bigbigdw.JoaraDW.Main;

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
    String BestCount;
    String BookViewed;
    String BookFavCount;
    String BookRecommend;

    public Main_BookListData(String Writer, String Title, String BookImg, String IsAdult, String IsFinish, String IsPremium, String IsNobless, String Intro, String IsFav, String BestCount, String BookViewed, String BookFavCount, String BookRecommend) {
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
        this.BestCount = BestCount;
        this.BookViewed = BookViewed;
        this.BookFavCount = BookFavCount;
        this.BookRecommend = BookRecommend;
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

    public String getBestCount() {
        return BestCount;
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
}
