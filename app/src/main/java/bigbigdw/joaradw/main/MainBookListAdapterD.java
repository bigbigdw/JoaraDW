package bigbigdw.joaradw.main;

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

import bigbigdw.joaradw.R;


public class MainBookListAdapterD extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MainBookListData> listData;
    private static final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String value);
    }

    private MainBookListAdapterD.OnItemClickListener listener = null;

    public void setOnItemClickListener(MainBookListAdapterD.OnItemClickListener listener) {
        this.listener = listener;
    }

    public MainBookListAdapterD(List<MainBookListData> items) {
        this.listData = (ArrayList<MainBookListData>) items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_d, parent, false);
            return new MainBookListViewHolderD(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new MainBookListAdapterD.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainBookListViewHolderD) {
            populateItemRows((MainBookListViewHolderD) holder, position);
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


    private void populateItemRows(MainBookListViewHolderD holder, int position) {
        MainBookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.image);

        holder.title.setText(listData.get(position).getTitle());
        holder.writer.setText(listData.get(position).getWriter());
    }


    public class MainBookListViewHolderD extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView writer;

        MainBookListViewHolderD(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Img_BookD);
            title = itemView.findViewById(R.id.Text_TitleD);
            writer = itemView.findViewById(R.id.Text_WriterD);

            image.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos, "BookDetail");
                }
            });
        }

    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.progressBar);
        }
    }


    public void setItems(List<MainBookListData> items) {
        this.listData = (ArrayList<MainBookListData>) items;
    }

    public MainBookListData getItem(int position) {
        return listData.get(position);
    }
}
