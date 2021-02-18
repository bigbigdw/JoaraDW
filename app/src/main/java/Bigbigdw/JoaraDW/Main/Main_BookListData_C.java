package Bigbigdw.JoaraDW.Main;

public class Main_BookListData_C {
    String Writer;
    String Title;
    String Intro;
    String BookImg;

    public Main_BookListData_C(String Writer, String Title, String BookImg,  String Intro) {
        this.Writer = Writer;
        this.Title = Title;
        this.BookImg = BookImg;
        this.Intro = Intro;
    }
    public String getIntro() {
        return Intro;
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
}
