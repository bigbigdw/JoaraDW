package Bigbigdw.JoaraDW.Book_Detail;

public class Detail_BookPageData {
    String BookListNum;
    String BookImg;
    String BookListRecommend;
    String BookChapter;
    String Cid;

    public Detail_BookPageData(String BookListNum, String BookImg, String BookListRecommend, String BookChapter, String Cid) {
        this.BookListNum = BookListNum;
        this.BookChapter = BookChapter;
        this.BookImg = BookImg;
        this.BookListRecommend = BookListRecommend;
        this.Cid = Cid;
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
    public String getCid() {
        return Cid;
    }
}