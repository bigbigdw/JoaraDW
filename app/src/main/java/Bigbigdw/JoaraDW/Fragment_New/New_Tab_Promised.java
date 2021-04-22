package Bigbigdw.JoaraDW.Fragment_New;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.R;


public class New_Tab_Promised extends Fragment {

    private final Main_BookListAdapter_C NewBookListAdapter = new Main_BookListAdapter_C();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab_promised, container, false);
        AssetManager assetManager = getActivity().getAssets();
        NewBookList(root, assetManager, "Main_PromisedBookList.json");

        return root;
    }

    public void NewBookList(View root, AssetManager assetManager, String BookType){
        RecyclerView recyclerView = root.findViewById(R.id.Main_NewPromised);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
        NewBookListAdapter.setItems(new Main_BookData_JSON().getData(assetManager, BookType));
        NewBookListAdapter.notifyDataSetChanged();
    }
}
