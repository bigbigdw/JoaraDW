package Bigbigdw.JoaraDW.Main;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;

        import java.util.ArrayList;

        import Bigbigdw.JoaraDW.R;


public class Main_BookListAdapter_B extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Main_BookListData_A> listData = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_booklist_b, parent, false);
        return new Main_BookListAdapter_B.Main_BookListViewHolder_B(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Main_BookListData_A item = listData.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getBookImg())
                .into(((Main_BookListAdapter_B.Main_BookListViewHolder_B) holder).Image);

        ((Main_BookListViewHolder_B) holder).Title.setText(listData.get(position).getTitle());
        ((Main_BookListViewHolder_B) holder).Writer.setText(listData.get(position).getWriter());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setItems(ArrayList<Main_BookListData_A> items) {
        this.listData = items;
    }

    static class Main_BookListViewHolder_B extends RecyclerView.ViewHolder {

        ImageView Image;
        TextView Title;
        TextView Writer;

        Main_BookListViewHolder_B(@NonNull View itemView) {
            super(itemView);
            Image = itemView.findViewById(R.id.Img_BookB);
            Title = itemView.findViewById(R.id.Text_TitleB);
            Writer = itemView.findViewById(R.id.Text_WriterB);
        }

    }

}
