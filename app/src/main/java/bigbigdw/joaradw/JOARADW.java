package bigbigdw.joaradw;

import android.app.Application;

public class JOARADW extends Application {

    String bookcode;                            // 북코드
    String apiUrl;
    String token = "";
    String name;
    String status;                  // 로그인 상태
    String mana;
    String expireCash;
    String cash;
    String manuscriptCoupon;
    String supportCoupon;
    String cid;
    String sortNo;
    int viewerBG = -1;                                              //뷰어 배경
    int viewerBGTheme = R.drawable.viewer_full_bg01;                   //뷰어 테마
    int textColor = -16777216;                                      //뷰어 텍스트 색
    String textType = "기본";                                         //뷰어 텍스트 타입
    String viewerBGType = "BG";                                     //뷰어 배경 타입
    int textSize = 0;                                               //뷰어 텍스트 크기
    int textLineSpace = 0;                                          //뷰어 텍스트 행간

    public void setTextLineSpace(int textLineSpace) {
        this.textLineSpace = textLineSpace;
    }

    public int getTextLineSpace() {
        return textLineSpace;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setViewerBGTheme(int viewerBGTheme) {
        this.viewerBGTheme = viewerBGTheme;
    }

    public int getViewerBGTheme() {
        return viewerBGTheme;
    }

    public void setViewerBGType(String viewerBGType) {
        this.viewerBGType = viewerBGType;
    }

    public String getViewerBGType() {
        return viewerBGType;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public String getTextType() {
        return textType;
    }

    public void setViewerBG(int ViewerBG) {
        this.viewerBG = ViewerBG;
    }

    public int getViewerBG() {
        return viewerBG;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }

    public void setBookCode(String bookcode) {
        this.bookcode = bookcode;
    }

    public String getBookCode() {
        return bookcode;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public String getMana() {
        return mana;
    }

    public void setExpireCash(String expireCash) {
        this.expireCash = expireCash;
    }

    public String getExpireCash() {
        return expireCash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getCash() {
        return cash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setManuscriptCoupon(String manuscriptCoupon) {
        this.manuscriptCoupon = manuscriptCoupon;
    }

    public String getManuscriptCoupon() {
        return manuscriptCoupon;
    }

    public void setSupportCoupon(String supportCoupon) {
        this.supportCoupon = supportCoupon;
    }

    public String getSupportCoupon() {
        return supportCoupon;
    }

}