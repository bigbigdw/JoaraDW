package bigbigdw.joaradw.Book_Detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bigbigdw.joaradw.fragment_main.MainMoreListData;
import bigbigdw.joaradw.R;

public class Book_Detail_PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainMoreListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_list, parent, false);
        return new Book_Detail_PageAdapter.PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((Book_Detail_PageAdapter.PageViewHolder) holder).Contents.setText(listData.get(position).getContents());
        ((Book_Detail_PageAdapter.PageViewHolder) holder).Date.setText(listData.get(position).getStartDate());

        ((Book_Detail_PageAdapter.PageViewHolder) holder).SortNo.setText(listData.get(position).getBookSortNo());
        if(((Book_Detail_PageAdapter.PageViewHolder) holder).SortNo != null){
            ((Book_Detail_PageAdapter.PageViewHolder) holder).SortNo.setVisibility(View.VISIBLE);
        }

        ((Book_Detail_PageAdapter.PageViewHolder) holder).BookSurveyStatus.setText(listData.get(position).getBookSurveyFinish());
        if(((Book_Detail_PageAdapter.PageViewHolder) holder).BookSurveyStatus != null){
            ((Book_Detail_PageAdapter.PageViewHolder) holder).BookSurveyStatus.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<MainMoreListData> items) {
        this.listData = items;
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {

        TextView Contents, Date, SortNo, BookSurveyStatus;

        PageViewHolder(@NonNull View itemView) {
            super(itemView);
            Contents = itemView.findViewById(R.id.Contents);
            Date = itemView.findViewById(R.id.Date);
            SortNo = itemView.findViewById(R.id.BookSortNo);
            BookSurveyStatus = itemView.findViewById(R.id.BookSurveyStatus);
        }

    }

}
