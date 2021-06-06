package Bigbigdw.JoaraDW.Main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_C extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<Main_BookListData> listData;
    private final int VIEW_TYPE_ITEM = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position, String Value);
    }

    private Main_BookListAdapter_C.OnItemClickListener Listener = null;

    public void setOnItemClickListener(Main_BookListAdapter_C.OnItemClickListener listener) {
        this.Listener = listener;
    }

    public Main_BookListAdapter_C(ArrayList<Main_BookListData> items) {
        this.listData = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklistdata_booklist_c, parent, false);
            return new Main_BookListAdapter_C.Main_BookListViewHolder_C(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Main_BookListAdapter_C.Main_BookListViewHolder_C) {
            populateItemRows((Main_BookListAdapter_C.Main_BookListViewHolder_C) holder, position);
        } else if (holder instanceof Main_BookListAdapter_C.LoadingViewHolder) {
            showLoadingView((Main_BookListAdapter_C.LoadingViewHolder) holder, position);
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

    private void showLoadingView(Main_BookListAdapter_C.LoadingViewHolder holder, int position) {
    }

    private void populateItemRows(Main_BookListAdapter_C.Main_BookListViewHolder_C holder, int position) {
        Main_BookListData item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(holder.Image);

        holder.Title.setText(listData.get(position).getTitle());
        holder.Writer.setText(listData.get(position).getWriter());
        holder.Intro.setText(listData.get(position).getIntro());
        holder.BookCode.setText(listData.get(position).getBookCode());
        holder.BookFav.setText(listData.get(position).getIsFav());
        holder.Category.setText(listData.get(position).getBookCategory());

        TextView TopText = holder.TopText;
        TextView Bar = holder.Bar;
        TextView Category = holder.Category;

        if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            TextSetting(R.string.NOBLESS, 0xAAa5c500, TopText, Bar, Category);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            TextSetting(R.string.PREMIUM, 0xAA4971EF, TopText, Bar, Category);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("FALSE")) {
            TextSetting(R.string.FINISH, 0xAA767676, TopText, Bar, Category);
        } else if (listData.get(position).getIsNobless().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            TextSetting(R.string.ADULT_NOBLESS, 0xAAF44336, TopText, Bar, Category);
        } else if (listData.get(position).getIsPremium().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            TextSetting(R.string.ADULT_PREMIUM, 0xAA4971EF, TopText, Bar, Category);
        } else if (listData.get(position).getIsFinish().equals("TRUE") && listData.get(position).getIsAdult().equals("TRUE")) {
            TextSetting(R.string.ADULT_FINISH, 0xAA767676, TopText, Bar, Category);
        } else {
            TopText.setText("무료");
        }

        if (listData.get(position).getIsFav().equals("TRUE")) {
            holder.Favon.setVisibility(View.VISIBLE);
            holder.Favoff.setVisibility(View.GONE);
        } else {
            holder.Favoff.setVisibility(View.VISIBLE);
            holder.Favon.setVisibility(View.GONE);
        }
    }

    public void TextSetting(int Title, int Color, TextView TopText, TextView Bar, TextView Category) {
        TopText.setText(Title);
        TopText.setTextColor(Color);
        Bar.setTextColor(Color);
        Category.setTextColor(Color);
    }

    public class Main_BookListViewHolder_C extends RecyclerView.ViewHolder {

        ImageView Image, Favon, Favoff;
        TextView Title, Writer, Intro, TopText, BookCode, BookFav, Bar, Category;
        CardView Img_Wrap;
        String BookTitle, Book_Code, TOKEN = "";
        LinearLayout BookContentsWrapC;
        JSONObject GETUSERINFO;

        Main_BookListViewHolder_C(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_Book);
            Title = itemView.findViewById(R.id.Text_Title);
            Intro = itemView.findViewById(R.id.Text_Intro);
            Writer = itemView.findViewById(R.id.Text_Writer);
            TopText = itemView.findViewById(R.id.TopText);
            Favon = itemView.findViewById(R.id.FavON);
            Favoff = itemView.findViewById(R.id.FavOff);
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap);
            BookCode = itemView.findViewById(R.id.BookCodeText);
            BookFav = itemView.findViewById(R.id.TextBookFav);
            BookContentsWrapC = itemView.findViewById(R.id.BookContentsWrapC);
            Bar = itemView.findViewById(R.id.Bar);
            Category = itemView.findViewById(R.id.Category);

            if(Config.GETUSERINFO() != null) {
                GETUSERINFO = Config.GETUSERINFO();
                JSONObject UserInfo;
                try {
                    UserInfo = GETUSERINFO.getJSONObject("user");
                    TOKEN = UserInfo.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                    TOKEN = "";
                }
            }

            BookContentsWrapC.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    Listener.onItemClick(v, pos, "BookDetail");
                }
            });

            if (!TOKEN.equals("")) {
                Img_Wrap.setOnClickListener(v -> {
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
            } else {
                Img_Wrap.setOnClickListener(v -> {
                    Toast.makeText(itemView.getContext(), "선호작을 등록하려면 로그인이 필요합니다",
                            Toast.LENGTH_SHORT).show();
                });
            }
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