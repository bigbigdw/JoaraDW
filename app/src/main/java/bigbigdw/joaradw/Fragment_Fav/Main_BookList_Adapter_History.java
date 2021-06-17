package bigbigdw.joaradw.Fragment_Fav;

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

import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;


public class Main_BookList_Adapter_History extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MainBookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;

    public Main_BookList_Adapter_History(ArrayList<MainBookListData> items) {
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
            return new LoadingViewHolder(view);
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
        int VIEW_TYPE_LOADING = 1;
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }



    private void showLoadingView(Main_BookList_Adapter_History.LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(Main_BookList_Adapter_History.Main_BookListViewHolder_New holder, int position) {
        MainBookListData item = listData.get(position);

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

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}