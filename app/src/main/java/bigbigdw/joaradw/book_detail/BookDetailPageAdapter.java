package bigbigdw.joaradw.book_detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.fragment_main.MainMoreListData;
import bigbigdw.joaradw.R;

public class BookDetailPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MainMoreListData> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_list, parent, false);
        return new BookDetailPageAdapter.PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((BookDetailPageAdapter.PageViewHolder) holder).contents.setText(listData.get(position).getContents());
        ((BookDetailPageAdapter.PageViewHolder) holder).date.setText(listData.get(position).getStartDate());

        ((BookDetailPageAdapter.PageViewHolder) holder).sortNo.setText(listData.get(position).getBookSortNo());
        if(((BookDetailPageAdapter.PageViewHolder) holder).sortNo != null){
            ((BookDetailPageAdapter.PageViewHolder) holder).sortNo.setVisibility(View.VISIBLE);
        }

        ((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus.setText(listData.get(position).getBookSurveyFinish());
        if(((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus != null){
            ((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(List<MainMoreListData> items) {
        this.listData = (ArrayList<MainMoreListData>)items;
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {

        TextView contents;
        TextView date;
        TextView sortNo;
        TextView bookSurveyStatus;

        PageViewHolder(@NonNull View itemView) {
            super(itemView);
            contents = itemView.findViewById(R.id.Contents);
            date = itemView.findViewById(R.id.Date);
            sortNo = itemView.findViewById(R.id.BookSortNo);
            bookSurveyStatus = itemView.findViewById(R.id.BookSurveyStatus);
        }

    }

}
