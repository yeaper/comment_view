package com.yyp.commentview.model;

import java.util.ArrayList;

/**
 * 评论实体
 * Created by yyp on 2017/9/5.
 */
public class CommentModel {

    private String avatar;
    private String username;
    private String content;
    private ArrayList<FloorModel> floorList;

    public CommentModel(String avatar, String username, String content, ArrayList<FloorModel> floorList) {
        this.avatar = avatar;
        this.username = username;
        this.content = content;
        this.floorList = floorList;
    }

    public CommentModel(){

    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<FloorModel> getFloorList() {
        return floorList;
    }

    public void setFloorList(ArrayList<FloorModel> floorList) {
        this.floorList = floorList;
    }
}
