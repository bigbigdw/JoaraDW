package Bigbigdw.JoaraDW.Main;

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

import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_D extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private Main_BookListAdapter_D.OnItemClickListener Listener = null;

    public void setOnItemClickListener(Main_BookListAdapter_D.OnItemClickListener listener) {
        this.Listener = listener;
    }

    public Main_BookListAdapter_D(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_d, parent, false);
            return new Main_BookListAdapter_D.Main_BookListViewHolder_D(view);
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
        int VIEW_TYPE_LOADING = 1;
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


    public class Main_BookListViewHolder_D extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;

        Main_BookListViewHolder_D(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookD);
            Title = itemView.findViewById(R.id.Text_TitleD);
            Writer = itemView.findViewById(R.id.Text_WriterD);

            Image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "BookDetail");
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
