package com.example.army_app;

import java.util.HashMap;
import java.util.Map;

public class GeneralShowSpec {
    private Map properties;

    public GeneralShowSpec(Map properties){
        if(properties == null){
            this.properties= new HashMap();
        }
        else{
            this.properties = new HashMap(properties);
        }
    }
    public Object getProperty(String propertyName){
        return properties.get(propertyName);
    }
    public Map getProperties(){
        return properties;
    }

}
