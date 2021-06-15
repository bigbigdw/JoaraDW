package Bigbigdw.JoaraDW;

import android.app.Application;
import android.content.Intent;

import com.android.volley.RequestQueue;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Main.Main_BookListData;

public class JOARADW extends Application {

    String BOOKCODE;                            // 북코드
    String TOKEN;                              // 토큰
    String API_URL;
    private String USERSTATUS;                  // 로그인 상태
        private String UserName;                  //유저 이름

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getUSERSTATUS() {
        return USERSTATUS;
    }

    public void setUSERSTATUS(String USERSTATUS) {
        this.USERSTATUS = USERSTATUS;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setBookCode(String BOOKCODE) {
        this.BOOKCODE = BOOKCODE;
    }

    public String getBookCode() {
        return BOOKCODE;
    }

    public void setAPI_URL(String API_URL) {
        this.API_URL = API_URL;
    }

    public String getAPI_URL() {
        return API_URL;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return UserName;
    }
}