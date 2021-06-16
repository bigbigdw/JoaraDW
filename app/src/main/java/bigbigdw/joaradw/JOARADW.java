package bigbigdw.joaradw;

import android.app.Application;

public class JOARADW extends Application {

    String bookcode;                            // 북코드
    String token;                              // 토큰
    String apiUrl;
    private String userstatus;                  // 로그인 상태
    private String userName;                  //유저 이름

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}