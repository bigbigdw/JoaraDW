package bigbigdw.joaradw.joara_post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.main.MainBookListAdapterC;
import bigbigdw.joaradw.main.MainBookListData;

public class AdapterPostList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MainBookListData> listData;
    private static final int VIEW_TYPE_ITEM = 0;
    String f = "FALSE";

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String value);
    }

    private MainBookListAdapterC.OnItemClickListener listener = null;

    public void setOnItemClickListener(MainBookListAdapterC.OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterPostList(List<MainBookListData> items) {
        this.listData = (ArrayList<MainBookListData>) items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_c, parent, false);
            return new AdapterPostList.PostListViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new AdapterPostList.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdapterPostList.PostListViewHolder) {
            populateItemRows((AdapterPostList.PostListViewHolder) holder, position);
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

    private void populateItemRows(AdapterPostList.PostListViewHolder holder, int position) {
        MainBookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.image);

        holder.title.setText(listData.get(position).getTitle());
        holder.writer.setText(listData.get(position).getWriter());
        holder.intro.setText(listData.get(position).getIntro());
        holder.bookCodeWrap.setText(listData.get(position).getBookCode());
        holder.bookFav.setText(listData.get(position).getIsFav());
        holder.category.setText(listData.get(position).getBookCategory());
        holder.textCntChapter.setText(listData.get(position).getCntChapter());

        TextView topText = holder.topText;
        TextView bar = holder.bar;
        TextView category = holder.category;

        if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals(f)) {
            textSetting(R.string.NOBLESS, 0xAAa5c500, topText, bar, category);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals(f)) {
            textSetting(R.string.PREMIUM, 0xAA4971EF, topText, bar, category);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals(f)) {
            textSetting(R.string.FINISH, 0xAA767676, topText, bar, category);
        } else if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            textSetting(R.string.ADULT_NOBLESS, 0xAAF44336, topText, bar, category);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            textSetting(R.string.ADULT_PREMIUM, 0xAA4971EF, topText, bar, category);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            textSetting(R.string.ADULT_FINISH, 0xAA767676, topText, bar, category);
        } else {
            topText.setText("무료");
            textSetting(R.string.FREE, 0xAA000000, topText, bar, category);
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.favon.setVisibility(View.VISIBLE);
            holder.favoff.setVisibility(View.GONE);
        } else {
            holder.favoff.setVisibility(View.VISIBLE);
            holder.favon.setVisibility(View.GONE);
        }
    }

    public void textSetting(int title, int color, TextView topText, TextView bar, TextView category) {
        topText.setText(title);
        topText.setTextColor(color);
        bar.setTextColor(color);
        category.setTextColor(color);
    }

    public class PostListViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ImageView favon;
        ImageView favoff;
        TextView title;
        TextView writer;
        TextView intro;
        TextView topText;
        TextView bookCodeWrap;
        TextView bookFav;
        TextView textCntChapter;
        TextView bar;
        TextView category;
        CardView imgWrap;
        String bookTitle;
        String bookCode;
        String token = "";
        LinearLayout bookContentsWrapC;
        JSONObject getuserinfo;

        PostListViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Img_Book);
            title = itemView.findViewById(R.id.Text_Title);
            intro = itemView.findViewById(R.id.Text_Intro);
            writer = itemView.findViewById(R.id.Text_Writer);
            topText = itemView.findViewById(R.id.TopText);
            favon = itemView.findViewById(R.id.FavON);
            favoff = itemView.findViewById(R.id.FavOff);
            imgWrap = itemView.findViewById(R.id.Img_Wrap);
            bookCodeWrap = itemView.findViewById(R.id.BookCodeText);
            bookFav = itemView.findViewById(R.id.TextBookFav);
            bookContentsWrapC = itemView.findViewById(R.id.BookContentsWrapC);
            bar = itemView.findViewById(R.id.Bar);
            category = itemView.findViewById(R.id.Category);
            textCntChapter = itemView.findViewById(R.id.Text_CntChapter);

            if (Config.getuserinfo() != null) {
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

            bookContentsWrapC.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos, "BookDetail");
                }
            });

            if (!token.equals("")) {
                imgWrap.setOnClickListener(v -> {
                    if (favoff.getVisibility() == View.VISIBLE) {
                        favoff.setVisibility(View.GONE);
                        favon.setVisibility(View.VISIBLE);
                        bookTitle = title.getText().toString();
                        bookCode = bookCodeWrap.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + bookTitle + "'이(가) 선호작에 등록되었습니다",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        favoff.setVisibility(View.VISIBLE);
                        favon.setVisibility(View.GONE);
                        bookTitle = title.getText().toString();
                        bookCode = bookCodeWrap.getText().toString();
                        Toast.makeText(itemView.getContext(), "'" + bookTitle + "'을(를) 선호작에서 해제하였습니다",
                                Toast.LENGTH_SHORT).show();
                    }
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, pos, "FAV");
                    }
                });
            } else {
                imgWrap.setOnClickListener(v ->
                        Toast.makeText(itemView.getContext(), "선호작을 등록하려면 로그인이 필요합니다", Toast.LENGTH_SHORT).show()
                );
            }
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
