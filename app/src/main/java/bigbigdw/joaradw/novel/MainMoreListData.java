package bigbigdw.joaradw.novel;

public class MainMoreListData {
    String contents;
    String startDate;
    String bookSortNo;
    String bookSurveyFinish;

    public MainMoreListData(String contents, String startDate, String bookSortNo, String bookSurveyFinish) {
        this.contents = contents;
        this.startDate = startDate;
        this.bookSortNo = bookSortNo;
        this.bookSurveyFinish = bookSurveyFinish;
    }
    public String getContents() {
        return contents;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getBookSortNo() {
        return bookSortNo;
    }
    public String getBookSurveyFinish() {
        return bookSurveyFinish;
    }
}
