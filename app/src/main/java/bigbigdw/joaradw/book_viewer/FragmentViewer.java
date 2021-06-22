package bigbigdw.joaradw.book_viewer;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewer, container, false);
        viewerText = root.findViewById(R.id.ViewerText);
        viewerChapter = root.findViewById(R.id.ViewerChapter);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        bookCode = myApp.getBookCode();
        token = myApp.getToken();
        cid = myApp.getCid();
        sortNO = myApp.getSortNo();

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
                Log.d("@@@@", response.toString());
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
}
