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

    public Main_BookListData(String Writer, String Title, String BookImg, String IsAdult, String IsFinish, String IsPremium, String IsNobless, String Intro, String IsFav) {
        this.Writer = Writer;
        this.Title = Title;
        this.BookImg = BookImg;
        this.IsAdult = IsAdult;
        this.IsFinish = IsFinish;
        this.IsPremium = IsPremium;
        this.IsNobless = IsNobless;
        this.Intro = Intro;
        this.IsFav = IsFav;
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
}
