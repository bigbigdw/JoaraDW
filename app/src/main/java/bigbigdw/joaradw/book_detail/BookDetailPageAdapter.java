package bigbigdw.joaradw.book_detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.novel.MainMoreListData;
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

        if(!listData.get(position).getBookSortNo().equals("")){
            String sortNoText = listData.get(position).getBookSortNo() + " ";
            ((BookDetailPageAdapter.PageViewHolder) holder).sortNo.setText(sortNoText);
            ((BookDetailPageAdapter.PageViewHolder) holder).sortNo.setVisibility(View.VISIBLE);
        } else {
            ((BookDetailPageAdapter.PageViewHolder) holder).sortNo.setText("");
            ((BookDetailPageAdapter.PageViewHolder) holder).sortNo.setVisibility(View.GONE);
        }

        if(!listData.get(position).getBookSurveyFinish().equals("")){
            String surveyText = listData.get(position).getBookSurveyFinish() + " ";
            ((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus.setText(surveyText);
            ((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus.setVisibility(View.VISIBLE);
        } else {
            ((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus.setText("1");
            ((BookDetailPageAdapter.PageViewHolder) holder).bookSurveyStatus.setVisibility(View.GONE);
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
