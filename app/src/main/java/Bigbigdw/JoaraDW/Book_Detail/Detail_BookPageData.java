package Bigbigdw.JoaraDW.Book_Detail;

public class Detail_BookPageData {
    String BookListNum;
    String BookImg;
    String BookListRecommend;
    String BookChapter;

    public Detail_BookPageData(String BookListNum, String BookImg, String BookListRecommend, String BookChapter) {
        this.BookListNum = BookListNum;
        this.BookChapter = BookChapter;
        this.BookImg = BookImg;
        this.BookListRecommend = BookListRecommend;
    }

    public String getBookChapter() {
        return BookChapter;
    }
    public String getBookListNum() {
        return BookListNum;
    }
    public String getBookImg() {
        return BookImg;
    }
    public String getBookListRecommend() {
        return BookListRecommend;
    }
}
