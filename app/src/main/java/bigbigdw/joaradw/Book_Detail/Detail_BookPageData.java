package bigbigdw.joaradw.Book_Detail;

public class Detail_BookPageData {
    String BookListNum;
    String BookImg;
    String BookListRecommend;
    String BookListComment;
    String BookChapter;
    String Cid;

    public Detail_BookPageData(String BookListNum, String BookImg, String BookListRecommend, String BookChapter, String Cid, String BookListComment) {
        this.BookListNum = BookListNum;
        this.BookChapter = BookChapter;
        this.BookImg = BookImg;
        this.BookListRecommend = BookListRecommend;
        this.BookListComment = BookListComment;
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
    public String getBookListComment() {
        return BookListComment;
    }
}
