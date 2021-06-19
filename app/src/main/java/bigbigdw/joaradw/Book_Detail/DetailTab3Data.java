package bigbigdw.joaradw.book_detail;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.fragment_main.MainMoreListData;

public class DetailTab3Data {
    ArrayList<MainMoreListData> items = new ArrayList<>();
    private static final String NOTICE = "NOTICE";
    private static final String NOTICE_PAGE = "NOTICE_PAGE";
    private static final String SETTING = "SETTING";
    private static final String SETTING_PAGE = "SETTING_PAGE";

    public ArrayList<MainMoreListData> getData(RequestQueue queue, LinearLayout wrap, String value) {
        String resultETC = HELPER.ETC + "&category=0&book_code=" + 738760;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, getApiUrl(value, resultETC), null, response -> {
            try {
                JSONArray jsonArray = null;
                int jsonlength = 5;
                jsonArray = response.getJSONArray(getJsonArray(value));

                if(value.equals(NOTICE_PAGE) || value.equals(SETTING_PAGE) || value.equals("SURVEY_PAGE")){
                    jsonlength = jsonArray.length();
                }


                for (int i = 0; i < jsonlength; i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String title = jo.getString("title");
                    String sortNoText = null;
                    String startDate = null;
                    String surveyStatus = null;
                    String date = null;

                    if (value.equals(NOTICE) || value.equals(SETTING) || value.equals(NOTICE_PAGE) || value.equals(SETTING_PAGE)) {
                        startDate = jo.getString("created");
                        date = startDate.substring(2, 4) + '.' + startDate.substring(4, 6) + '.' + startDate.substring(6, 8);
                    }

                    switch (value) {
                        case NOTICE:
                        case NOTICE_PAGE:
                            items.add(new MainMoreListData(title, date, "", ""));
                            break;
                        case SETTING:
                        case SETTING_PAGE:
                            String sortno = jo.getString("sortno");

                            sortNoText = sortnoCheck(sortno);
                            items.add(new MainMoreListData(title, date, sortNoText, ""));
                            break;
                        default:
                            String isFinish = jo.getString("chkfinish");

                            surveyStatus = finishCheck(isFinish);

                            String surveyStartDate = jo.getString("start_date");
                            String surveyEndDate = jo.getString("end_date");

                            String surveyStart = surveyStartDate.substring(2, 4) + '.' + surveyStartDate.substring(5, 7) + '.' + surveyStartDate.substring(8, 10);
                            String surveyEnd = surveyEndDate.substring(2, 4) + '.' + surveyEndDate.substring(5, 7) + '.' + surveyEndDate.substring(8, 10);

                            items.add(new MainMoreListData(title, surveyAdd(value, surveyStart, surveyEnd), "", surveyStatus));

                            break;
                    }
                }
                wrap.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Detail_Tab_3_Data", "에러!!");
            }
        }, error ->  Log.d("Detail_Tab_3_Data", "연결 실패"));
        queue.add(jsonRequest);
        return items;
    }

    public static String sortnoCheck(String sortno){
        if (sortno.equals("0")) {
            return"[주 설정]";
        } else {
            return "[" + sortno + "편]";
        }
    }

    public static String surveyAdd(String value, String surveyStart, String surveyEnd){
        if(value.equals("SURVEY_PAGE")){
           return surveyStart + " ~ " + surveyEnd;
        } else {
            return surveyEnd;
        }
    }

    public static String finishCheck(String isFinish){
        if (isFinish.equals("y")) {
            return "[마감]";
        } else {
            return "[진행중]";
        }
    }

    public static String getApiUrl(String value, String resultETC) {
        if(value.equals(NOTICE)){
            return HELPER.API + API.BOARD_BOOK_NOTICE_JOA + resultETC + "&page=1&offset=20";
        } else if(value.equals(SETTING) || value.equals(SETTING_PAGE)){
            return HELPER.API + API.BOARD_BOOK_SETTING_JOA + resultETC + "&page=1&offset=20";
        } else {
            return HELPER.API + API.BOARD_BOOK_POLL_JOA + resultETC + "&chkfinish=&page=1&offset=100";
        }
    }

    public static String getJsonArray(String value) {
        if(value.equals(NOTICE)){
            return "notices";
        } else if(value.equals(SETTING) || value.equals(SETTING_PAGE)){
            return "data";
        } else {
            return "poll";
        }
    }
}