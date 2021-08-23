package bigbigdw.joaradw.fragment_fav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.main.OLD_MainBookListData;
import bigbigdw.joaradw.R;


public class MainBookListAdapterFav extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<OLD_MainBookListData> listData;
    private static final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String value);
    }

    private MainBookListAdapterFav.OnItemClickListener listener = null;

    public void setOnItemClickListener(MainBookListAdapterFav.OnItemClickListener listener) {
        this.listener = listener;
    }

    public MainBookListAdapterFav(List<OLD_MainBookListData> items) {
        this.listData = (ArrayList<OLD_MainBookListData>) items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_fav, parent, false);
            return new MainBookListViewHolderNew(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainBookListViewHolderNew) {
            populateItemRows((MainBookListViewHolderNew) holder, position);
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


    private void populateItemRows(MainBookListViewHolderNew holder, int position) {
        OLD_MainBookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.image);

        holder.title.setText(listData.get(position).getTitle());
        holder.writer.setText(listData.get(position).getWriter());
        holder.intro.setText(listData.get(position).getIntro());
        holder.textViewBookCode.setText(listData.get(position).getBookCode());
        holder.textCntChapter.setText(listData.get(position).getCntChapter());

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.favon.setVisibility(View.VISIBLE);
            holder.favoff.setVisibility(View.GONE);
        } else {
            holder.favoff.setVisibility(View.VISIBLE);
            holder.favon.setVisibility(View.GONE);
        }
    }

    public class MainBookListViewHolderNew extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView writer;
        TextView intro;
        ImageView favon;
        ImageView favoff;
        LinearLayout favWrap;
        LinearLayout imgWrap;
        String booktitle;
        String bookCode;
        TextView textViewBookCode;
        TextView textCntChapter;

        MainBookListViewHolderNew(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Img_BookFav);
            title = itemView.findViewById(R.id.Text_TitleFav);
            intro = itemView.findViewById(R.id.Text_IntroFav);
            writer = itemView.findViewById(R.id.Text_Writer);
            favon = itemView.findViewById(R.id.Icon_FavOn);
            favoff = itemView.findViewById(R.id.Icon_FavOff);
            favWrap = itemView.findViewById(R.id.Fav_Wrap);
            imgWrap = itemView.findViewById(R.id.Img_Wrap);
            textViewBookCode = itemView.findViewById(R.id.BookCodeText);
            textCntChapter = itemView.findViewById(R.id.Text_CntChapter);

            imgWrap.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos, "BookDetail");
                }
            });

            favWrap.setOnClickListener(v -> {
                if (favoff.getVisibility() == View.VISIBLE) {
                    favoff.setVisibility(View.GONE);
                    favon.setVisibility(View.VISIBLE);
                    booktitle = title.getText().toString();
                    bookCode = textViewBookCode.getText().toString();
                    Toast.makeText(itemView.getContext(), "'" + booktitle + "'이(가) 선호작에 등록되었습니다",
                            Toast.LENGTH_SHORT).show();
                } else {
                    favoff.setVisibility(View.VISIBLE);
                    favon.setVisibility(View.GONE);
                    booktitle = title.getText().toString();
                    bookCode = textViewBookCode.getText().toString();
                    Toast.makeText(itemView.getContext(), "'" + booktitle + "'을(를) 선호작에서 해제하였습니다",
                            Toast.LENGTH_SHORT).show();
                }
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos, "FAV");
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

    public void setItems(List<OLD_MainBookListData> items) {
        this.listData = (ArrayList<OLD_MainBookListData>) items;
    }

    public OLD_MainBookListData getItem(int position) {
        return listData.get(position);
    }
}