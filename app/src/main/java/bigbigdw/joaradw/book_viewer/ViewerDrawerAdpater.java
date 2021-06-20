package bigbigdw.joaradw.book_viewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.book_detail.DetailBookPageData;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.R;


public class ViewerDrawerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<DetailBookPageData> listData;
    private static final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String value);
    }

    private ViewerDrawerAdpater.OnItemClickListener listener = null;

    public void setOnItemClickListener(ViewerDrawerAdpater.OnItemClickListener listener) {
        this.listener = listener;
    }


    public ViewerDrawerAdpater(List<DetailBookPageData> items) {
        this.listData = (ArrayList<DetailBookPageData>) items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewer_drawer_item, parent, false);
            return new DetailBookListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new ViewerDrawerAdpater.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DetailBookListViewHolder) {
            populateItemRows((DetailBookListViewHolder) holder, position);
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

    private void populateItemRows(DetailBookListViewHolder holder, int position) {

        holder.title.setText(listData.get(position).getBookChapter());
        holder.bookList.setText(listData.get(position).getBookListNum());
        holder.bookListRecommend.setText(listData.get(position).getBookListRecommend());
        holder.bookCid.setText(listData.get(position).getCid());
        holder.bookListComment.setText(listData.get(position).getBookListComment());

    }

    public class DetailBookListViewHolder extends RecyclerView.ViewHolder {

        String token;
        LinearLayout imgWrap;
        TextView bookCid;
        TextView title;
        TextView bookList;
        TextView bookListRecommend;
        TextView bookListComment;
        JSONObject getuserinfo;

        DetailBookListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ChapterTitle);
            bookListRecommend = itemView.findViewById(R.id.BookListRecommend);
            bookListComment = itemView.findViewById(R.id.BookListComment);
            bookList = itemView.findViewById(R.id.BookListNum);
            imgWrap = itemView.findViewById(R.id.Img_Wrap);
            bookCid = itemView.findViewById(R.id.Cid);

            if(Config.getuserinfo() != null) {
                getuserinfo = Config.getuserinfo();
                JSONObject userInfo;
                try {
                    userInfo = getuserinfo.getJSONObject("user");
                    token = userInfo.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    token = "";
                }
            }

            imgWrap.setOnClickListener(v -> {
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


    public void setItems(List<DetailBookPageData> items) {
        this.listData = (ArrayList<DetailBookPageData>) items;
    }

    public DetailBookPageData getItem(int position) {
        return listData.get(position);
    }
}