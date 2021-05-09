package Bigbigdw.JoaraDW.BookList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_D extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onClickAdapterListener_D {

    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    onClickAdapterListener_D listener;

    public Main_BookListAdapter_D(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_d, parent, false);
            return new Main_BookListAdapter_D.Main_BookListViewHolder_D(view, listener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Main_BookListAdapter_D.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Main_BookListAdapter_D.Main_BookListViewHolder_D) {
            populateItemRows((Main_BookListAdapter_D.Main_BookListViewHolder_D) holder, position);
        } else if (holder instanceof Main_BookListAdapter_D.LoadingViewHolder) {
            showLoadingView((Main_BookListAdapter_D.LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    private void showLoadingView(Main_BookListAdapter_D.LoadingViewHolder holder, int position) {
    }

    private void populateItemRows(Main_BookListAdapter_D.Main_BookListViewHolder_D holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
    }

    @Override
    public void onClickAdapter_D(Main_BookListAdapter_D.Main_BookListViewHolder_D holder, View view, int position, String Value) {
        if (listener != null) {
            listener.onClickAdapter_D(holder, view, position, Value);
        }
    }

    public void setOnItemClicklistener(onClickAdapterListener_D listener) {
        this.listener = listener;
    }


    static class Main_BookListViewHolder_D extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;

        Main_BookListViewHolder_D(@NonNull View itemView , final onClickAdapterListener_D listener) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookD);
            Title = itemView.findViewById(R.id.Text_TitleD);
            Writer = itemView.findViewById(R.id.Text_WriterD);

            Image.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onClickAdapter_D(Main_BookListAdapter_D.Main_BookListViewHolder_D.this, v, position, "BookDetail");
                }
            });
        }

    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    public Main_BookListData getItem(int position) {
        return listData.get(position);
    }
}
