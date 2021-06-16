package bigbigdw.joaradw.Book_Detail;

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

import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.fragment_main.MainMoreListData;

public class Detail_Tab_3_Data {
    ArrayList<MainMoreListData> items = new ArrayList<>();

    public ArrayList<MainMoreListData> getData(RequestQueue queue, LinearLayout Wrap, String BookCode, String Value) {
        String Result_URL = null;
        String Result_ETC = HELPER.ETC + "&category=0&book_code=" + 738760;

        switch (Value) {
            case "NOTICE":
            case "NOTICE_PAGE":
                Result_URL = HELPER.API + "/v1/board/book_notice.joa" + Result_ETC + "&page=1&offset=20";
                break;
            case "SETTING":
            case "SETTING_PAGE":
                Result_URL = HELPER.API + "/v1/board/book_setting.joa" + Result_ETC + "&page=1&offset=20";
                break;
            case "SURVEY":
            case "SURVEY_PAGE":
                Result_URL = HELPER.API + "/v1/board/book_poll.joa" + Result_ETC + "&chkfinish=&page=1&offset=100";
                break;
        }

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, Result_URL, null, response -> {
            try {
                JSONArray JSONArray = null;
                int JSONLength = 5;
                switch (Value) {
                    case "NOTICE":
                    case "NOTICE_PAGE":
                        JSONArray = response.getJSONArray("notices");
                        break;
                    case "SETTING":
                    case "SETTING_PAGE":
                        JSONArray = response.getJSONArray("data");
                        break;
                    case "SURVEY":
                    case "SURVEY_PAGE":
                        JSONArray = response.getJSONArray("poll");
                        break;
                }

                if(Value.equals("NOTICE_PAGE") || Value.equals("SETTING_PAGE") || Value.equals("SURVEY_PAGE")){
                    JSONLength = JSONArray.length();
                }

                String SortNoText = null, StartDate = null, SurveyStatus = null, Date = null;
                for (int i = 0; i < JSONLength; i++) {
                    JSONObject jo = JSONArray.getJSONObject(i);
                    String Title = jo.getString("title");

                    if (Value.equals("NOTICE") || Value.equals("SETTING") || Value.equals("NOTICE_PAGE") || Value.equals("SETTING_PAGE")) {
                        StartDate = jo.getString("created");
                        Date = StartDate.substring(2, 4) + '.' + StartDate.substring(4, 6) + '.' + StartDate.substring(6, 8);
                    }

                    switch (Value) {
                        case "NOTICE":
                        case "NOTICE_PAGE":
                            items.add(new MainMoreListData(Title, Date, "", ""));
                            break;
                        case "SETTING":
                        case "SETTING_PAGE":
                            String Sortno = jo.getString("sortno");
                            if (Sortno.equals("0")) {
                                SortNoText = "[주 설정]";
                            } else {
                                SortNoText = "[" + Sortno + "편]";
                            }
                            items.add(new MainMoreListData(Title, Date, SortNoText, ""));
                            break;
                        case "SURVEY":
                        case "SURVEY_PAGE":
                            String isFinish = jo.getString("chkfinish");
                            if (isFinish.equals("y")) {
                                SurveyStatus = "[마감]";
                            } else {
                                SurveyStatus = "[진행중]";
                            }
                            String SurveyStartDate = jo.getString("start_date");
                            String SurveyEndDate = jo.getString("end_date");

                            String SurveyStart = SurveyStartDate.substring(2, 4) + '.' + SurveyStartDate.substring(5, 7) + '.' + SurveyStartDate.substring(8, 10);
                            String SurveyEnd = SurveyEndDate.substring(2, 4) + '.' + SurveyEndDate.substring(5, 7) + '.' + SurveyEndDate.substring(8, 10);

                            if(Value.equals("SURVEY_PAGE")){
                                String SurveyDateTotal = SurveyStart + " ~ " + SurveyEnd;
                                items.add(new MainMoreListData(Title, SurveyDateTotal, "", SurveyStatus));
                            } else {
                                items.add(new MainMoreListData(Title, SurveyEnd, "", SurveyStatus));
                            }

                            break;
                    }
                }
                Wrap.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Detail_Tab_3_Data", "에러!!");
            }
        }, error -> {
            Log.d("Detail_Tab_3_Data", "연결 실패");
        });
        queue.add(jsonRequest);
        return items;
    }
}