package bigbigdw.joaradw.book_detail;

public class DetailBookPageData {
    String bookListNum;
    String bookImg;
    String bookListRecommend;
    String bookListComment;
    String bookChapter;
    String cid;

    public DetailBookPageData(String bookListNum, String bookImg, String bookListRecommend, String bookChapter, String cid, String bookListComment) {
        this.bookListNum = bookListNum;
        this.bookChapter = bookChapter;
        this.bookImg = bookImg;
        this.bookListRecommend = bookListRecommend;
        this.bookListComment = bookListComment;
        this.cid = cid;
    }

    public String getBookChapter() {
        return bookChapter;
    }
    public String getBookListNum() {
        return bookListNum;
    }
    public String getBookImg() {
        return bookImg;
    }
    public String getBookListRecommend() {
        return bookListRecommend;
    }
    public String getCid() {
        return cid;
    }
    public String getBookListComment() {
        return bookListComment;
    }
}
