package bigbigdw.joaradw.Fragment_Fav;

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

import bigbigdw.joaradw.main.Main_BookListData;
import bigbigdw.joaradw.R;


public class Main_BookListAdapter_Fav extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private Main_BookListAdapter_Fav.OnItemClickListener Listener = null;

    public void setOnItemClickListener(Main_BookListAdapter_Fav.OnItemClickListener listener) {
        this.Listener = listener;
    }

    public Main_BookListAdapter_Fav(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_fav, parent, false);
            return new Main_BookListAdapter_Fav.Main_BookListViewHolder_New(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof Main_BookListAdapter_Fav.Main_BookListViewHolder_New) {
            populateItemRows((Main_BookListAdapter_Fav.Main_BookListViewHolder_New) holder, position);
        } else if (holder instanceof Main_BookListAdapter_Fav.LoadingViewHolder) {
            showLoadingView((Main_BookListAdapter_Fav.LoadingViewHolder) holder, position);
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



    private void showLoadingView(Main_BookListAdapter_Fav.LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(Main_BookListAdapter_Fav.Main_BookListViewHolder_New holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
        holder.Intro.setText(listData.get(position).getIntro());
        holder.BookCode.setText(listData.get(position).getBookCode());

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.Favon.setVisibility(View.VISIBLE);
            holder.Favoff.setVisibility(View.GONE);
        } else {
            holder.Favoff.setVisibility(View.VISIBLE);
            holder.Favon.setVisibility(View.GONE);
        }
    }

    public class Main_BookListViewHolder_New extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;
        TextView Intro;
        ImageView Favon;
        ImageView Favoff;
        LinearLayout Fav_Wrap, Img_Wrap;
        String BookTitle,Book_Code;
        TextView BookCode;

        Main_BookListViewHolder_New(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookFav);
            Title = itemView.findViewById(R.id.Text_TitleFav);
            Intro = itemView.findViewById(R.id.Text_IntroFav);
            Writer = itemView.findViewById(R.id.Text_WriterFav);
            Favon = itemView.findViewById(R.id.Icon_FavOn);
            Favoff = itemView.findViewById(R.id.Icon_FavOff);
            Fav_Wrap = itemView.findViewById(R.id.Fav_Wrap);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);
            BookCode = itemView.findViewById(R.id.BookCodeText);

            Img_Wrap.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "BookDetail");
                }
            });

            Fav_Wrap.setOnClickListener(v -> {
                if (Favoff.getVisibility() == View.VISIBLE) {
                    Favoff.setVisibility(View.GONE);
                    Favon.setVisibility(View.VISIBLE);
                    BookTitle = Title.getText().toString();
                    Book_Code = BookCode.getText().toString();
                    Toast.makeText(itemView.getContext(), "'" + BookTitle + "'이(가) 선호작에 등록되었습니다",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Favoff.setVisibility(View.VISIBLE);
                    Favon.setVisibility(View.GONE);
                    BookTitle = Title.getText().toString();
                    Book_Code = BookCode.getText().toString();
                    Toast.makeText(itemView.getContext(), "'" + BookTitle + "'을(를) 선호작에서 해제하였습니다",
                            Toast.LENGTH_SHORT).show();
                }
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "FAV");
                }
            });
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ProgressBar progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public void setItems(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    public Main_BookListData getItem(int position) {
        return listData.get(position);
    }
}