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

import Bigbigdw.JoaraDW.Main.Main_BookData_A;
import Bigbigdw.JoaraDW.R;

public class New_Tab_77FES extends Fragment {
    private final Main_BookListAdapter_New NewBookListAdapter = new Main_BookListAdapter_New();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_new_77fes, container, false);
        AssetManager assetManager = getActivity().getAssets();
        NewBookList(root, assetManager, "Main_Tab_Best_77FES.json");

        return root;
    }

    public void NewBookList(View root, AssetManager assetManager, String BookType){
        RecyclerView recyclerView = root.findViewById(R.id.Main_New77FES);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
        NewBookListAdapter.setItems(new Main_BookData_A().getData(assetManager, BookType));
        NewBookListAdapter.notifyDataSetChanged();
    }
}