package com.yyp.commentview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 评论盖楼 View
 *
 * Created by yyp on 2017/9/4.
 */
public class CommentView extends LinearLayout {

    private int density;
    private Drawable drawer;

    public CommentView(Context context) {
        super(context);
        init(context);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(LinearLayout.VERTICAL);
        density = (int) context.getResources().getDisplayMetrics().density;
    }

    /**
     * 设置边界线
     * @param drawable
     */
    public void setBorder(Drawable drawable) {
        drawer = drawable;
    }

    /**
     * 设置每个子 View 的外边距
     */
    public void relayoutChildren() {
        int count = getChildCount();
        // 从内向外设置边距
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layout.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            // 只设置5层的边距
            int margin = Math.min((count - i - 1), 4) * 3 * density;
            layout.leftMargin = margin ;
            layout.rightMargin = margin ;
            // 最后一层不设置上外边距
            if (i == count - 1) {
                layout.topMargin = 0;
            } else {
                layout.topMargin = Math.min((count - i), 4) * 3 * density;
            }

            view.setLayoutParams(layout);
        }
    }

    /**
     * 为每个子 View 画出边界
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        int count = getChildCount();
        if (null != drawer && count > 0) {
            for (int i = count - 1; i >= 0; i--) {
                View view = getChildAt(i);
                // 在这个矩形区域内边界, top = left
                drawer.setBounds(view.getLeft(), view.getLeft(), view.getRight(), view.getBottom());
                drawer.draw(canvas);
            }
        }

        super.dispatchDraw(canvas);
    }
}