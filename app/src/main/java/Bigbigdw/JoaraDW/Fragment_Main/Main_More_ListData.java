package Bigbigdw.JoaraDW.Fragment_Main;

public class Main_More_ListData {
    String Contents;
    String StartDate;
    String BookSortNo;
    String BookSurveyFinish;

    public Main_More_ListData(String Contents, String StartDate, String BookSortNo, String BookSurveyFinish) {
        this.Contents = Contents;
        this.StartDate = StartDate;
        this.BookSortNo = BookSortNo;
        this.BookSurveyFinish = BookSurveyFinish;
    }
    public String getContents() {
        return Contents;
    }
    public String getStartDate() {
        return StartDate;
    }
    public String getBookSortNo() {
        return BookSortNo;
    }
    public String getBookSurveyFinish() {
        return BookSurveyFinish;
    }
}
