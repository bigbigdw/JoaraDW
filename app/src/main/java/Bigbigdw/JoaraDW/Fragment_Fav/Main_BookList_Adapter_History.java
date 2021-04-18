package Bigbigdw.JoaraDW.Fragment_Fav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Main_BookList_Adapter_History extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public Main_BookList_Adapter_History(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_history, parent, false);
            return new Main_BookList_Adapter_History.Main_BookListViewHolder_New(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new Main_BookList_Adapter_History.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Main_BookList_Adapter_History.Main_BookListViewHolder_New) {
            populateItemRows((Main_BookList_Adapter_History.Main_BookListViewHolder_New) holder, position);
        } else if (holder instanceof Main_BookList_Adapter_History.LoadingViewHolder) {
            showLoadingView((Main_BookList_Adapter_History.LoadingViewHolder) holder, position);
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



    private void showLoadingView(Main_BookList_Adapter_History.LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(Main_BookList_Adapter_History.Main_BookListViewHolder_New holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
        holder.ReadCount.setText(listData.get(position).getReadCount());

    }


    static public class Main_BookListViewHolder_New extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView ReadCount;

        Main_BookListViewHolder_New(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookFav);
            Title = itemView.findViewById(R.id.Text_TitleFav);
            Writer = itemView.findViewById(R.id.Text_WriterFav);
            ReadCount = itemView.findViewById(R.id.Text_ReadCount);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}