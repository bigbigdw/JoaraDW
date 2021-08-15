package bigbigdw.joaradw.joara_post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import bigbigdw.joaradw.R;

//public class Old_AdapterPostComment extends RecyclerView.Adapter<Old_AdapterPostComment.AdapterPostCommentHolder>{
//
//    //아이템 클릭시 실행 함수
//    private RecyclerViewItemClick itemClick;
//
//    //아이템 클릭시 실행 함수 등록 함수
//    public void setItemClick(RecyclerViewItemClick itemClick) {
//        this.itemClick = itemClick;
//    }
//
//    private NetManager mNetManager;
//
//    private Context mContext;
//
//    private String mBookCode = "";
//
//    private List<ItemCriticismComment> mItemCriticismComment;
//    private List<ItemViewCommentV2> mItemCommentView;
//
//    private int mEditModeLastPosition;
//
//    private Boolean[] mItemCommentEditMode;
//
//    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
//
//    public Old_AdapterPostComment(Context context, LoaderManager lm, String bookcode, List<ItemCriticismComment> list) {
//        mContext = context;
//
//        this.mNetManager = NetManager.getCurrentNetManager(mContext, lm);
//
//        mBookCode = bookcode;
//
//        mItemCriticismComment = list;
//        mItemCommentView = new ArrayList<ItemViewCommentV2>();
//
//        initCommentEditMode();
//    }
//
//    @Override
//    public Old_AdapterPostComment.AdapterPostCommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_criticism_comment, parent , false);
//        return new Old_AdapterPostComment.AdapterPostCommentHolder(v);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItemCriticismComment.size();
//    }
//
//    @Override
//    public void onBindViewHolder(Old_AdapterPostComment.AdapterPostCommentHolder holder, int position) {
//
//
//        if (holder == null) return;
//
//        // View Save
//        ItemViewCommentV2 itemViewCommentV2 = new ItemViewCommentV2();
//        itemViewCommentV2.setTview_item_comment_contents(holder.tview_item_comment_contents);
//        itemViewCommentV2.setEtext_item_comment_contents(holder.etext_item_comment_contents);
//        itemViewCommentV2.setTview_item_comment_report(holder.tview_item_comment_report);
//        itemViewCommentV2.setTview_item_comment_edit(holder.tview_item_comment_edit);
//        itemViewCommentV2.setTview_item_comment_delete(holder.tview_item_comment_delete);
//        itemViewCommentV2.setTview_item_comment_cancel(holder.tview_item_comment_cancel);
//        itemViewCommentV2.setTview_item_comment_commit(holder.tview_item_comment_commit);
//
//        addItemCommentView(position, itemViewCommentV2);
//
//        // 데이터 입력
//        ItemCriticismComment itemComment = (ItemCriticismComment) getItem(position);
//
//        // 코멘트 작성자
//        holder.tview_item_comment_writer.setText(itemComment.getMember_name());
//
//        // 코멘트 등록일
//        holder.tview_item_comment_created.setText(Util.getDateDisplayString(itemComment.getCreated(), "yyyy.MM.dd HH:mm"));
//
//        // 코멘트 내용
//        holder.tview_item_comment_contents.setText(itemComment.getComment());
//        holder.etext_item_comment_contents.setText(itemComment.getComment());
//
//
//        // 표지
//        holder.iview_item_comment_writer_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//
//        GlideApp.with(mContext)
//                .load(itemComment.getProfile())
//                .into(holder.iview_item_comment_writer_image);
//
//        // 본인이 작성한 코멘트 인지 확인
//        boolean commentWriter = false;
//        if (AuthManager.isLogin(mContext)) {
//            if (itemComment.getMember_id().equals(InfoPrivate.getString(mContext, InfoPrivate.SP_KEY_USER_MEMBER_ID))) {
//                commentWriter = true;
//            }
//        }
//
//        // 댓글 작성자 본인인지 확인
//        if (commentWriter) {
//            // 현재 수정중인지 확인
//            if (mItemCommentEditMode[position]) {
//                setCommentMode(position, Old_AdapterPostComment.KindCommentMode.WriterEdit);
//
//            } else {
//                setCommentMode(position, Old_AdapterPostComment.KindCommentMode.Writer);
//            }
//
//        } else {
//            setCommentMode(position, Old_AdapterPostComment.KindCommentMode.Read);
//        }
//
//    }
//
//    public Object getItem(int position) {
//        if (mItemCriticismComment.size() > position && position >= 0) {
//            return mItemCriticismComment.get(position);
//        }
//
//        return null;
//    }
//
//    public void setData(List<ItemCriticismComment> list) {
//        mItemCriticismComment = list;
//
//        initCommentEditMode();
//
//        notifyDataSetChanged();
//    }
//
//    public void addData(List<ItemCriticismComment> list) {
//        int startPosition = this.mItemCriticismComment.size() ;
//        mItemCriticismComment.addAll(list);
//        initCommentEditMode();
//        notifyItemRangeInserted(startPosition , list.size());
//    }
//
//    public List<ItemViewCommentV2> getmItemCommentView() {
//        return mItemCommentView;
//    }
//
//    public void addItemCommentView(int position, ItemViewCommentV2 itemViewCommentV2) {
//        if (mItemCommentView.size() <= position) {
//            for (int idx = mItemCommentView.size(); idx <= position; idx++) {
//                mItemCommentView.add(idx, null);
//            }
//        }
//        mItemCommentView.set(position, itemViewCommentV2);
//    }
//
//    public void initCommentEditMode() {
//        this.mItemCommentEditMode = new Boolean[mItemCriticismComment.size()];
//        if (mItemCommentEditMode.length > 0) {
//            for (int idx = 0; idx < mItemCommentEditMode.length; idx++) {
//                mItemCommentEditMode[idx] = false;
//            }
//        }
//
//        mEditModeLastPosition = -1;
//    }
//
//    public String getmBookCode() {
//        return mBookCode;
//    }
//
//    public void setmBookCode(String mBookCode) {
//        this.mBookCode = mBookCode;
//    }
//
//    private enum KindCommentMode {
//        Read            // 일반
//        , Writer        // 작성자
//        , WriterEdit    // 작성자 - 댓글 수정시
//    }
//
//    /**
//     * 상황에 따른 화면 설정
//     * @param position
//     * @param kind
//     */
//    private void setCommentMode(final int position, Old_AdapterPostComment.KindCommentMode kind) {
//        if (mItemCommentView.get(position) != null) {
//
//            // 코멘트 신고
//            mItemCommentView.get(position).getTview_item_comment_report().setVisibility(View.GONE);
//
//            if (kind == Old_AdapterPostComment.KindCommentMode.Writer) {
//                // 댓글 내용 보기 보임
//                mItemCommentView.get(position).getTview_item_comment_contents().setVisibility(View.VISIBLE);
//
//                // 댓글 내용 수정 숨김
//                mItemCommentView.get(position).getEtext_item_comment_contents().setVisibility(View.GONE);
//
//                // 코멘트 삭제
//                mItemCommentView.get(position).getTview_item_comment_delete().setVisibility(View.VISIBLE);
//
//                // 코멘트 수정 - UI변경
//                mItemCommentView.get(position).getTview_item_comment_edit().setVisibility(View.VISIBLE);
//
//                // 코멘트 수정 - 취소
//                mItemCommentView.get(position).getTview_item_comment_cancel().setVisibility(View.GONE);
//
//                // 코멘트 수정 - 등록
//                mItemCommentView.get(position).getTview_item_comment_commit().setVisibility(View.GONE);
//
//            } else if (kind == Old_AdapterPostComment.KindCommentMode.WriterEdit) {
//                // 댓글 보기 숨김
//                mItemCommentView.get(position).getTview_item_comment_contents().setVisibility(View.GONE);
//
//                // 댓글 수정 보임
//                mItemCommentView.get(position).getEtext_item_comment_contents().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mItemCommentView.get(position).getEtext_item_comment_contents().setVisibility(View.VISIBLE);
//                        mItemCommentView.get(position).getEtext_item_comment_contents().requestFocus();
//                        mItemCommentView.get(position).getEtext_item_comment_contents().setSelection(mItemCommentView.get(position).getEtext_item_comment_contents().getText().toString().length());
//
//                        // 키보드 보임
//                        Util.showSoftKeyboard(mContext, mItemCommentView.get(position).getEtext_item_comment_contents());
//                    }
//                }, 100);
//
//                // 코멘트 삭제
//                mItemCommentView.get(position).getTview_item_comment_delete().setVisibility(View.GONE);
//
//                // 코멘트 수정 - UI변경
//                mItemCommentView.get(position).getTview_item_comment_edit().setVisibility(View.GONE);
//
//                // 코멘트 수정 - 취소
//                mItemCommentView.get(position).getTview_item_comment_cancel().setVisibility(View.VISIBLE);
//
//                // 코멘트 수정 - 등록
//                mItemCommentView.get(position).getTview_item_comment_commit().setVisibility(View.VISIBLE);
//
//            } else {
//                // 댓글 내용 보기 보임
//                mItemCommentView.get(position).getTview_item_comment_contents().setVisibility(View.VISIBLE);
//
//                // 댓글 내용 수정 숨김
//                mItemCommentView.get(position).getEtext_item_comment_contents().setVisibility(View.GONE);
//
//                // 코멘트 삭제
//                mItemCommentView.get(position).getTview_item_comment_delete().setVisibility(View.GONE);
//
//                // 코멘트 수정
//                mItemCommentView.get(position).getTview_item_comment_edit().setVisibility(View.GONE);
//
//                // 코멘트 수정 - 취소
//                mItemCommentView.get(position).getTview_item_comment_cancel().setVisibility(View.GONE);
//
//                // 코멘트 수정 - 등록
//                mItemCommentView.get(position).getTview_item_comment_commit().setVisibility(View.GONE);
//
//            }
//        }
//    }
//
//    public class AdapterPostCommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public View root;
//
//        // ViewHolder
//        public TextView tview_item_comment_writer ;
//        public TextView tview_item_comment_created ;
//        public TextView tview_item_comment_contents ;
//        public EditText etext_item_comment_contents ;
//        public TextView tview_item_comment_report ;
//        public TextView tview_item_comment_edit ;
//        public TextView tview_item_comment_delete ;
//        public TextView tview_item_comment_cancel ;
//        public TextView tview_item_comment_commit ;
//
//        public ImageView iview_item_comment_writer_image;
//
//        public AdapterPostCommentHolder(View itemView) {
//            super(itemView);
//            root = itemView;
//
//            // ViewHolder
//            tview_item_comment_writer = itemView.findViewById(R.id.tview_item_comment_writer);
//            tview_item_comment_created = itemView.findViewById( R.id.tview_item_comment_created);
//            tview_item_comment_contents = itemView.findViewById( R.id.tview_item_comment_contents);
//            etext_item_comment_contents =itemView.findViewById(R.id.etext_item_comment_contents);
//            tview_item_comment_report =itemView.findViewById(R.id.tview_item_comment_report);
//            tview_item_comment_edit = itemView.findViewById( R.id.tview_item_comment_edit);
//            tview_item_comment_delete = itemView.findViewById(R.id.tview_item_comment_delete);
//            tview_item_comment_cancel = itemView.findViewById(R.id.tview_item_comment_cancel);
//            tview_item_comment_commit = itemView.findViewById( R.id.tview_item_comment_commit);
//            iview_item_comment_writer_image = itemView.findViewById(R.id.iview_item_comment_writer_image);
//
//            root.setOnClickListener(this);
//
//            // 코멘트 삭제
//            tview_item_comment_delete.setOnClickListener(this);
//            // 코멘트 수정 - UI변경
//            tview_item_comment_edit.setOnClickListener(this);
//            // 코멘트 수정 - 취소
//            tview_item_comment_cancel.setOnClickListener(this);
//            // 코멘트 수정 - 등록
//            tview_item_comment_commit.setOnClickListener(this);
//        }
//
//
//        @Override
//        public void onClick(View view) {
//
//            int position = getAdapterPosition();
//            // 데이터 입력
//            ItemCriticismComment itemComment = (ItemCriticismComment) getItem(position);
//
//            if(itemComment == null) return;
//
//            if(view == root ){
//                if(itemClick != null){
//                    itemClick.onClick(view, position);
//                }
//            }else if(view == tview_item_comment_delete) { // 코멘트 삭제
//                if (mContext instanceof ActivityPostDetail) {
//                    ((ActivityPostDetail) mContext).confirmCommentDelete(itemComment.getComment_id());
//                }
//            }else if(view == tview_item_comment_edit){ // 코멘트 수정 - UI변경
//
//                mItemCommentEditMode[position] = true;
//
//                // 이전 수정 댓글 화면 설정
//                if (mEditModeLastPosition >= 0) {
//                    setCommentMode(mEditModeLastPosition, Old_AdapterPostComment.KindCommentMode.Writer);
//                    mItemCommentEditMode[mEditModeLastPosition] = false;
//
//                    mEditModeLastPosition = position;
//
//                } else {
//                    mEditModeLastPosition = position;
//                }
//
//                // 화면 설정
//                setCommentMode(position, Old_AdapterPostComment.KindCommentMode.WriterEdit);
//
//                // 등록창 숨김
//                if (mContext instanceof ActivityCriticismDetail) {
//                    ((ActivityCriticismDetail) mContext).hideCommentWriteBox();
//                }
//
//            }else if(view == tview_item_comment_cancel){ // 코멘트 수정 - 취소
//                mItemCommentEditMode[position] = false;
//                mEditModeLastPosition = -1;
//
//                // 화면 설정
//                setCommentMode(position, Old_AdapterPostComment.KindCommentMode.Writer);
//
//                // 등록 UI 보이기
//                if (mContext instanceof ActivityCriticismDetail) {
//                    ((ActivityCriticismDetail) mContext).showCommentWriteBox();
//                }
//
//                // 키보드 숨기기
//                Util.hideSoftKeyboard(mContext, etext_item_comment_contents);
//            }else if(view == tview_item_comment_commit){  // 코멘트 수정 - 등록
//                mItemCommentEditMode[position] = false;
//                mEditModeLastPosition = -1;
//
//                // 수정
//                if (mContext instanceof ActivityPostDetail) {
//                    ((ActivityPostDetail) mContext).sendCommentEdit(itemComment.getComment_id(), getmItemCommentView().get(position).getEtext_item_comment_contents().getText().toString());
//                    ((ActivityPostDetail) mContext).movePosition(position + 3);
//                }
//
//                // 화면 설정
//                setCommentMode(position, Old_AdapterPostComment.KindCommentMode.Writer);
//
//                // 등록 UI 보이기
//                if (mContext instanceof ActivityCriticismDetail) {
//                    ((ActivityCriticismDetail) mContext).showCommentWriteBox();
//                }
//
//                // 키보드 숨기기
//                Util.hideSoftKeyboard(mContext, etext_item_comment_contents);
//            }
//        }
//    }
//}

