package Bigbigdw.JoaraDW.Main;

public class Main_BookListData_A {
    String Writer;
    String Title;
    String BookImg;

    public Main_BookListData_A(String Writer, String Title, String BookImg) {
        this.Writer = Writer;
        this.Title = Title;
        this.BookImg = BookImg;
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
