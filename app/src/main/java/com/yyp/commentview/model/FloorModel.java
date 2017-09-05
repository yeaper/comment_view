package com.yyp.commentview.model;

/**
 * 楼层实体
 * Created by yyp on 2017/9/5.
 */
public class FloorModel {
    private int floorNum;
    private String username;
    private String content;

    public FloorModel(int floorNum, String username, String content) {
        this.floorNum = floorNum;
        this.username = username;
        this.content = content;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
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
}
