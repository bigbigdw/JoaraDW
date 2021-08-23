package bigbigdw.joaradw.fragment_fav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.main.OLD_MainBookListData;
import bigbigdw.joaradw.R;


public class MainBookListAdapterHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<OLD_MainBookListData> listData;
    private static final int VIEW_TYPE_ITEM = 0;

    public MainBookListAdapterHistory(List<OLD_MainBookListData> items) {
        this.listData = (ArrayList<OLD_MainBookListData>) items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_history, parent, false);
            return new MainBookListViewHolderHistory(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainBookListViewHolderHistory) {
            populateItemRows((MainBookListViewHolderHistory) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewTypeLoading = 1;
        return listData.get(position) == null ? viewTypeLoading : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    private void populateItemRows(MainBookListViewHolderHistory holder, int position) {
        OLD_MainBookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.image);

        holder.title.setText(listData.get(position).getTitle());
        holder.writer.setText(listData.get(position).getWriter());
        holder.readCount.setText(listData.get(position).getReadCount());

    }


    public static class MainBookListViewHolderHistory extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView writer;
        TextView readCount;

        MainBookListViewHolderHistory(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Img_BookFav);
            title = itemView.findViewById(R.id.Text_TitleFav);
            writer = itemView.findViewById(R.id.Text_WriterFav);
            readCount = itemView.findViewById(R.id.Text_ReadCount);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.progressBar);
        }
    }
}