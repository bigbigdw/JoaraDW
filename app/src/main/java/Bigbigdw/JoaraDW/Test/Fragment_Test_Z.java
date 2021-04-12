package Bigbigdw.JoaraDW.Test;

import android.os.Bundle;
import android.os.Handler;
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                items.remove(items.size() - 1);
                int scrollPosition = items.size();
                adapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    items.add("Item " + currentSize);
                    currentSize++;
                }

                adapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

}


