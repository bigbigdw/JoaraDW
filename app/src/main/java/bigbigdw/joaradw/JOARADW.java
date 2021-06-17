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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getMana() {
        return mana;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public String getExpireCash() {
        return expireCash;
    }

    public void setExpireCash(String expireCash) {
        this.expireCash = expireCash;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManuscriptCoupon() {
        return manuscriptCoupon;
    }

    public void setManuscriptCoupon(String manuscriptCoupon) {
        this.manuscriptCoupon = manuscriptCoupon;
    }

    public String getSupportCoupon() {
        return supportCoupon;
    }

    public void setSupportCoupon(String supportCoupon) {
        this.supportCoupon = supportCoupon;
    }
}