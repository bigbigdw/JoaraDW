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