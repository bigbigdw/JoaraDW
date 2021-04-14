package Bigbigdw.JoaraDW.Test;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.R;

public class Fragment_Test_Z extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> items = new ArrayList<>();
    private boolean isLoading = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);
        populateData();
        initAdapter();
        initScrollListener();

        return root;
    }

    private void populateData() {
        for (int i=0; i<10; i++) {
            items.add("Item " + i);
        }
    }

    private void initAdapter() {
        adapter = new RecyclerViewAdapter(items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initScrollListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == items.size() - 1) {
                        //리스트 마지막
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        items.add(null);
        adapter.notifyItemInserted(items.size() - 1);

//        Log.d("TEST-items.size()", Integer.toString(items.size()));
        Log.d("TEST-items.size()", "1111111111111111111");

        Handler handler = new Handler();
        handler.postDelayed(() -> {
//            Log.d("TEST-items.size()22222", Integer.toString(items.size()));
            Log.d("TEST-items.size()22222", "2222222222222222222222222");
            items.remove(items.size() - 1);
//            Log.d("TEST-items.size()33333", Integer.toString(items.size()));
            Log.d("TEST-items.size()33333", "333333333333333333333333333");
            int scrollPosition = items.size();
//            Log.d("TEST-scrollPosition", Integer.toString(scrollPosition));
            Log.d("TEST-scrollPosition", "444444444444444444444444444444444");
            adapter.notifyItemRemoved(scrollPosition);
            int currentSize = scrollPosition;
//            Log.d("TEST-currentSize", Integer.toString(currentSize));
            Log.d("TEST-currentSize", "5555555555555555555555555555555555");
            int nextLimit = currentSize + 10;
//            Log.d("TEST-nextLimit", Integer.toString(currentSize));
            Log.d("TEST-nextLimit", "66666666666666666666666666666666666");
            while (currentSize - 1 < nextLimit) {
                items.add("Item " + currentSize);
                currentSize++;
            }
            Log.d("TEST-TEST", "7777777777777777777777777777777");

            adapter.notifyDataSetChanged();
            Log.d("TEST-TEST", "888888888888888888888888888");
            isLoading = false;
            Log.d("TEST-TEST", "999999999999999999999999999");
        }, 2000);
    }

}


