package com.example.tictactoe3;

public class helper_class {

    private  String name,tag,score;
    private int button;
    private int room_members;


    public helper_class() {
    }

    public helper_class(String name, String tag, String score, int button,int room_members) {
        this.name = name;
        this.tag = tag;
        this.score = score;
        this.button = button;
        this.room_members=room_members;
    }

    public void setRoom_members(int room_members) {
        this.room_members = room_members;
    }

    public int getRoom_members() {
        return room_members;
    }


    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public String getScore() {
        return score;
    }

    public int getButton() {
        return button;
    }
}
