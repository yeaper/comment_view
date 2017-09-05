package com.yyp.commentview.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yyp.commentview.R;
import com.yyp.commentview.adapter.CommentAdapter;
import com.yyp.commentview.model.CommentModel;
import com.yyp.commentview.model.FloorModel;

import java.util.ArrayList;

public class MainActivity extends Activity {

    RecyclerView recyclerView;
    CommentAdapter adapter;
    ArrayList<CommentModel> data;

    RelativeLayout commentContainer;
    EditText commentBox;
    TextView commentBtn;

    int mCommentPosition = -1;
    int mPreviousFloorNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView(){
        // 初始化控件
        recyclerView = findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        adapter = new CommentAdapter(this, data);
        recyclerView.setAdapter(adapter);

        commentContainer = findViewById(R.id.main_comment_container);
        commentBox = findViewById(R.id.main_comment_box);
        commentBtn = findViewById(R.id.main_comment_btn);

        initData();

        setListener();
    }

    public void setListener(){
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commentBox.getText().toString().trim();
                if(mCommentPosition != -1 && !TextUtils.isEmpty(content)){
                    // 点击评论按钮：增加楼层，刷新数据，隐藏输入法
                    data.get(mCommentPosition).getFloorList().add(
                            new FloorModel(++mPreviousFloorNum, "fee", content));
                    adapter.notifyItemChanged(mCommentPosition);
                    recyclerView.smoothScrollToPosition(mCommentPosition);

                    showOrHideInput();
                    commentBox.setText("");
                    commentContainer.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.setOnCommentClickListener(new CommentAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(int position, int previousFloorNum) {
                // 点击评论图标：显示输入法，并滑动到评论的内容旁边
                commentContainer.setVisibility(View.VISIBLE);
                showOrHideInput();
                commentBox.requestFocus();

                recyclerView.smoothScrollToPosition(position);
                mCommentPosition = position;
                mPreviousFloorNum = previousFloorNum;
            }
        });
    }

    /**
     * 加载数据
     */
    public void initData(){
        for(int i=1; i<=11; i++){
            // 评论数据
            CommentModel commentModel = new CommentModel();
            commentModel.setAvatar("http://img5.imgtn.bdimg.com/it/u=3243490007,841167834&fm=27&gp=0.jpg");
            commentModel.setUsername("用户"+i);
            commentModel.setContent("评论内容评论内容评论内容评论内容评论内容");

            ArrayList<FloorModel> floorList = new ArrayList<>();
            for(int j=1; j<i; j++){
                // 楼层数据
                floorList.add(new FloorModel(j, "efee", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容"));
            }
            commentModel.setFloorList(floorList);

            data.add(commentModel);
        }

        adapter.refreshData(data);
    }

    /**
     * 显示或者隐藏输入法
     */
    public void showOrHideInput(){
        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
