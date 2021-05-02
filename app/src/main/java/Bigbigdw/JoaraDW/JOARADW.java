package Bigbigdw.JoaraDW;

import android.app.Application;

public class JOARADW extends Application {

    String BOOKCODE;
    String TOKEN;
    String API_URL;

    @Override
    public void onCreate() {
        BOOKCODE = "";
        TOKEN = "";
        API_URL = "";
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setBookCode(String BOOKCODE) {
        this.BOOKCODE = BOOKCODE;
    }

    public String getBookCode() {
        return BOOKCODE;
    }

    public void setToken(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getToken() {
        return TOKEN;
    }

    public void setAPI_URL(String API_URL) {
        this.API_URL = API_URL;
    }

    public String getAPI_URL() {
        return API_URL;
    }
}