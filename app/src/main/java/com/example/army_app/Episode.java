package com.example.army_app;

import java.math.BigInteger;
import java.util.ArrayList;

public class Episode {
    private BigInteger id;
    private int episode_number;
    public Episode( BigInteger id , int episode_number ){
        this.id = id;
        this.episode_number = episode_number;
    }


    public int getEpisode_number() {
        return episode_number;
    }

    public BigInteger getId() {
        return id;
    }
}
