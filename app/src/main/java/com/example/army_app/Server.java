package com.example.army_app;

import java.math.BigInteger;
import java.util.ArrayList;

public class Server {
    private String server_name;
    private String streaming_link;
    private String downloading_link;
    private String quality;

    public Server(String server_name , String streaming_link , String downloading_link,String quality){
        this.server_name =server_name;
        this.downloading_link = downloading_link;
        this.streaming_link = streaming_link;
        this.quality=quality;
    }



    public String getServerName(){
        return this.server_name;
    }
    public String getStreaming_link(){return this.streaming_link;}
    public String getDownloading_link(){return this.downloading_link;}
    public String getQuality(){return this.quality;}

}
