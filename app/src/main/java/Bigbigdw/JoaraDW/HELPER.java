package Bigbigdw.JoaraDW;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface HELPER {

    String API = "https://api.joara.com";
    String API_KEY = "mw_8ba234e7801ba288554ca07ae44c7";
    String VER = "2.6.3";
    String DEVICE = "mw";
    String DEVICE_ID = "5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604";
    String DEVICE_TOKEN = "mw";
    String ETC = "?api_key=" + API_KEY + "&ver=" + VER + "&device=" + DEVICE + "&deviceuid=" + DEVICE_ID + "&devicetoken=" + DEVICE_TOKEN;
    String ResultURL = API + "?api_key=" + API_KEY + "&ver=" + VER + "&device=" + DEVICE + "&deviceuid=" + DEVICE_ID + "&devicetoken=" + DEVICE_TOKEN;
    String TOKEN = "";
}
