package com.example.army_app;

import android.content.Context;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

public class GeneralShow implements Serializable {

    private GeneralShowType generalShowType;
    private GeneralShowSpec spec;
    private BigInteger id;
    private int cat_id;
    private String country;
    private String name;
    private String poster_image;
    public GeneralShow(BigInteger id,int cat_id , String name , String poster_image  , String country ,GeneralShowSpec spce){
        this.id = id ;
        this.cat_id = cat_id;
        this.name = name;
        this.poster_image = poster_image;
        this.country = country;
        this.spec = spce;
    }


    public void setId(BigInteger id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPosterImagePath(String poster_image){
        this.poster_image = poster_image;
    }
    public BigInteger getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getPosterImagePath(){
        return this.poster_image;
    }
    public String getCountry(){return this.country;}
    public void setSpec(GeneralShowSpec spec){
        this.spec = spec;
    }
    public GeneralShowSpec getSpec(){
        return this.spec;
    }
    public GeneralShowType getGeneralShowType(){
        return this.generalShowType;
    }

    public int getCat_id() {
        return cat_id;
    }

    public static void assignCat(int cat_id ,Context context , TextView tv){
        if(cat_id == 1)
            tv.setText(context.getString(R.string.tv_show));
        else if(cat_id == 2)
            tv.setText(context.getString(R.string.movie));
        else if(cat_id == 3)
            tv.setText(context.getString(R.string.dvd));
        else if(cat_id == 4)
            tv.setText(context.getString(R.string.bangTan_tv));
        else if(cat_id == 6)
            tv.setText(context.getString(R.string.others));
        else if(cat_id == 7)
            tv.setText(context.getString(R.string.v_live));
        else {
            tv.setText(context.getString(R.string.show));
        }
    }
}
