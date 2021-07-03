package bigbigdw.joaradw.book_viewer;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;

public class FragmentViewer extends Fragment {
    String cid;
    String token;
    String apiurl;
    String bookCode;
    String sortNO;
    String cryptKeyURL;
    TextView viewerText;
    JSONArray data;
    JSONObject viwerJSON;
    TextView viewerChapter;
    NestedScrollView viewerBG;
    String fontType;
    String normal = "기본";
    String bold = "굵게";
    String italic = "이탤릭";
    String boldItalic = "굵은 이탤릭";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewer, container, false);
        viewerText = root.findViewById(R.id.ViewerText);
        viewerChapter = root.findViewById(R.id.ViewerChapter);
        viewerBG = root.findViewById(R.id.ViewerBG);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        bookCode = myApp.getBookCode();
        token = myApp.getToken();
        cid = myApp.getCid();
        sortNO = myApp.getSortNo();

        viewerText.setTextColor(myApp.getTextColor());
        if(myApp.getViewerBGType().equals("BG")){
            viewerBG.setBackgroundColor(myApp.getViewerBG());
        } else {
            viewerBG.setBackgroundResource(myApp.getViewerBGTheme());
        }
        fontType= myApp.getTextType();
        changeFontType();
        textSpaceOption(myApp.getTextLineSpace(), viewerText);
        textSizeOption(myApp.getTextSize(), viewerText);

        apiurl = HELPER.API + API.BOOK_CHAPTER_JOA + HELPER.ETC + "&cid=" + cid + "&sortno=" + sortNO;
        cryptKeyURL = HELPER.API + API.BOOK_CHAPTER_VALID_JOA + HELPER.ETC;

        setLayout();

        return root;
    }

    public void setLayout(){
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        final JsonObjectRequest cryptKey = new JsonObjectRequest(Request.Method.GET, cryptKeyURL, null, response -> {
            try {
                data = response.getJSONArray("data");
                data.getString(1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(cryptKey);

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, apiurl, null, response -> {
            try {
                JSONObject chapter = response.getJSONObject("chapter");
                String chapterTotal = "제 " + chapter.getString("sortno") + "화 " + chapter.getString("sub_title");
                viewerChapter.setText(chapterTotal);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(jsonRequest);

        AssetManager assetManager = requireContext().getAssets();

        viwerJSON = Config.getfakeviewer(assetManager);
        try {
            String result = viwerJSON.getString("books");
            viewerText.setText(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void changeFontType() {
        if (fontType.equals(normal)) {
            viewerText.setTypeface(null, Typeface.NORMAL);
        } else if (fontType.equals(bold)) {
            viewerText.setTypeface(viewerText.getTypeface(), Typeface.BOLD);
        } else if (fontType.equals(italic)) {
            viewerText.setTypeface(viewerText.getTypeface(), Typeface.ITALIC);
        } else if (fontType.equals(boldItalic)) {
            viewerText.setTypeface(viewerText.getTypeface(), Typeface.BOLD_ITALIC);
        }
    }

    public void textSpaceOption(int progress, TextView viewerText) {
        if (progress == 0) {
            viewerText.setLineSpacing(0, 1.0f);
        } else if (progress == 1) {
            viewerText.setLineSpacing(0, 1.2f);
        } else if (progress == 2) {
            viewerText.setLineSpacing(0, 1.4f);
        } else if (progress == 3) {
            viewerText.setLineSpacing(0, 1.6f);
        } else if (progress == 4) {
            viewerText.setLineSpacing(0, 1.8f);
        } else if (progress == 5) {
            viewerText.setLineSpacing(0, 2.0f);
        }
    }

    public void textSizeOption(int progress, TextView viewerText) {
        if (progress == 0) {
            viewerText.setTextSize(14);
        } else if (progress == 1) {
            viewerText.setTextSize(16);
        } else if (progress == 2) {
            viewerText.setTextSize(18);
        } else if (progress == 3) {
            viewerText.setTextSize(20);
        } else if (progress == 4) {
            viewerText.setTextSize(22);
        } else if (progress == 5) {
            viewerText.setTextSize(24);
        }
    }
}
