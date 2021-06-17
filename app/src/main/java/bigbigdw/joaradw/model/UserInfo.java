package bigbigdw.joaradw.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
    String token = "";
    String name;
    String status;                  // 로그인 상태
    String mana;
    String expireCash;
    String cash;
    String manuscriptCoupon;
    String supportCoupon;

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

    public static UserInfo getParseData(JSONObject obj) throws JSONException {
        UserInfo tempUserInfo  = new UserInfo();

        tempUserInfo.setStatus(obj.getString("status"));
        JSONObject userInfo = obj.getJSONObject("user");
        tempUserInfo.setToken(userInfo.getString("token"));
        tempUserInfo.setMana(userInfo.getString("mana"));
        tempUserInfo.setExpireCash(userInfo.getString("expireCash"));
        tempUserInfo.setCash(userInfo.getString("cash"));
        tempUserInfo.setName(userInfo.getString("nickname"));
        tempUserInfo.setManuscriptCoupon(userInfo.getString("manuscriptCoupon"));
        tempUserInfo.setSupportCoupon(userInfo.getString("supportCoupon"));

        return tempUserInfo;
    }
}
