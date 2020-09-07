package com.example.army_app;

public class Comment  {
    String user_name;
    String user_photo_url;
    String date;
    String comment_body;
    public Comment(String user_name , String user_photo_url , String date , String comment_body){
        this.user_name = user_name;
        this.user_photo_url = user_photo_url;
        this.date = date;
        this.comment_body = comment_body;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public String getUser_photo_url(){
        return this.user_photo_url;
    }
    public String getDate(){
        return this.date;
    }
    public String getComment_body(){
        return this.comment_body;
    }
}
