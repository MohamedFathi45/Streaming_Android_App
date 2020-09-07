package com.example.army_app;

public enum GeneralShowType {
    MOVIES ,TV_SHOWS , DVD , BANGTAN_TV , VLIVE , OTHERS;
    public String toString(){
        switch (this){
            case MOVIES:    return "movies";
            case TV_SHOWS:  return "tv_shows";
            case DVD:       return "dvds";
            case BANGTAN_TV:return "bangTan_tv";
            case VLIVE:     return "vLive";
            case OTHERS:    return "others";
            default:        return "none";
        }
    }
}
