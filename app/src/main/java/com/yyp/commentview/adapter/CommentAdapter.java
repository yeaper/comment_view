package com.yyp.commentview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yyp.commentview.R;
import com.yyp.commentview.model.CommentModel;
import com.yyp.commentview.model.FloorModel;
import com.yyp.commentview.view.FloorFactory;
import com.yyp.commentview.widget.CommentView;

import java.util.ArrayList;

/**
 *
 * Created by yyp on 2017/9/5.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private Context ctx;
    private ArrayList<CommentModel> data;
    private LayoutInflater layoutInflater;
    private OnCommentClickListener listener;

    public CommentAdapter(Context ctx, ArrayList<CommentModel> data){
        this.ctx = ctx;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(layoutInflater.inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final CommentHolder holder, int position) {
        Glide.with(ctx)
                .load(data.get(position).getAvatar())
                .thumbnail(0.5f)
                .into(holder.avatar);
        holder.username.setText(data.get(position).getUsername());
        holder.content.setText(data.get(position).getContent());

        final ArrayList<FloorModel> floorList = data.get(position).getFloorList();
        floorAdapter(holder.commentView, floorList);

        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    // 返回要评论的下标、上一个楼层号
                    if(floorList.size() <= 0){
                        listener.onCommentClick(holder.getAdapterPosition(), 0);
                    }else{
                        listener.onCommentClick(holder.getAdapterPosition(), floorList.get(floorList.size()-1).getFloorNum());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 适配楼层数据
     * @param commentView
     * @param floorList
     */
    private void floorAdapter(final CommentView commentView, final ArrayList<FloorModel> floorList){
        // 加载之前，先清空数据
        commentView.removeAllViews();
        commentView.setVisibility(View.VISIBLE);
        // 设置边界线
        commentView.setBorder(ctx.getResources().getDrawable(R.drawable.border_drawable));


        /* 1、0层时，不显示
           2、小于等于5层，全部显示
           3、大于5层，只显示前2层和最后1层；点击展开，显示全部楼层
         */
        if(floorList.size() <= 0){
            commentView.setVisibility(View.GONE);
        }else if(floorList.size() <= 5) {
            for(int i=0; i<floorList.size(); i++){
                commentView.addView(FloorFactory.buildFloor(floorList.get(i), commentView));
            }
        }else {
            commentView.addView(FloorFactory.buildFloor(floorList.get(0), commentView));
            commentView.addView(FloorFactory.buildFloor(floorList.get(1), commentView));
            View view = FloorFactory.buildHideFloor(floorList.get(2), commentView);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick (View v) {
                    // 加载所有楼层数据
                    commentView.removeAllViews();
                    for(int i=0; i<floorList.size(); i++){
                        View view = FloorFactory.buildFloor(floorList.get(i), commentView);
                        commentView.addView(view);
                    }
                    commentView.relayoutChildren();
                }
            });
            commentView.addView(view);
            commentView.addView(FloorFactory.buildFloor(floorList.get(floorList.size()-1), commentView));
        }

        commentView.relayoutChildren();
    }

    /**
     * 更新数据
     * @param list
     */
    public void refreshData(ArrayList<CommentModel> list){
        this.data = list;
        notifyDataSetChanged();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{

        private CircularImageView avatar;
        private TextView username;
        private ImageView commentBtn;
        private TextView content;
        private CommentView commentView;

        public CommentHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.comment_avatar);
            username = itemView.findViewById(R.id.comment_username);
            commentBtn = itemView.findViewById(R.id.comment_btn);
            content = itemView.findViewById(R.id.comment_content);
            commentView = itemView.findViewById(R.id.comment_container);
        }
    }

    public void setOnCommentClickListener(OnCommentClickListener listener){
        this.listener = listener;
    }

    /**
     * 评论点击监听接口
     */
    public interface OnCommentClickListener{
        /**
         *
         * @param position 数据下标
         * @param previousFloorNum 上一个楼层号
         */
        void onCommentClick(int position, int previousFloorNum);
    }
}
