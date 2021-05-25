package Bigbigdw.JoaraDW.Book_Viewer;

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

import java.util.Objects;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.R;

public class Fragment_Viewer extends Fragment {
    String CID, TOKEN, API_URL, BookCode, SortNO;
    TextView ViewerText;
    JSONArray Data;
    JSONObject ViwerJSON;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewer, container, false);
        ViewerText = root.findViewById(R.id.ViewerText);

        RequestQueue queue = Volley.newRequestQueue(requireContext());


//        final JsonObjectRequest CryptKey = new JsonObjectRequest(Request.Method.GET, CryptKey_URL, null, response -> {
//            try {
//                Data = response.getJSONArray("data");
//                Data.getString(1);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Log.d("Book_Pagination", "에러!"));
//        queue.add(CryptKey);

//        API_URL = HELPER.API + "/v1/book/chapter.joa" + HELPER.ETC + "&book_code=" + BookCode + "&cid=" + CID + "&token=" + TOKEN + "&sortno=" + SortNO;
//
//        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, response -> {
//            try {
//                JSONObject CHAPTER = response.getJSONObject("chapter");
//                CrptedContents = CHAPTER.getString("content");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Log.d("Book_Pagination", "에러!"));
//        queue.add(jsonRequest);

        AssetManager assetManager = requireContext().getAssets();

        ViwerJSON = Config.GETFAKEVIEWER(assetManager);
        try {
            String Result = ViwerJSON.getString("books");
            ViewerText.setText(Result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return root;
    }
}
