package com.yyp.commentview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yyp.commentview.R;
import com.yyp.commentview.model.FloorModel;

public class FloorFactory {

    /**
     * 构建一个楼层
     *
     * @param floorModel
     * @param group
     * @return
     */
    public static View buildFloor(FloorModel floorModel, ViewGroup group) {
        LayoutInflater inflater = (LayoutInflater) group.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate ( R.layout.floor_item, null);
        RelativeLayout show = view.findViewById(R.id.show_floor_content);
        LinearLayout hide = view.findViewById(R.id.hide_floor_content);

        show.setVisibility(View.VISIBLE);
        hide.setVisibility(View.GONE);

        TextView floorNum = view.findViewById(R.id.floor_num);
        TextView username = view.findViewById(R.id.floor_username);
        TextView content = view.findViewById(R.id.floor_content);

        // 设置数据
        floorNum.setText(String.valueOf(floorModel.getFloorNum()));
        username.setText(floorModel.getUsername());
        content.setText(floorModel.getContent());
        return view;
    }

    /**
     * 隐藏一个楼层
     *
     * @param floorModel
     * @param group
     * @return
     */
    public static View buildHideFloor(FloorModel floorModel, ViewGroup group) {
        LayoutInflater inflater = (LayoutInflater) group.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.floor_item, null);
        RelativeLayout show = view.findViewById(R.id.show_floor_content);
        LinearLayout hide = view.findViewById(R.id.hide_floor_content);

        show.setVisibility(View.GONE);
        hide.setVisibility(View.VISIBLE);

        return view;
    }
}