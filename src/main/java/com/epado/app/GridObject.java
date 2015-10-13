package com.epado.app;

public class GridObject{
    private String key;
    
    public GridObject(String key){
        this.key = key;
    }
    
    public String getKey(){
        return this.key;
    }
    
    public void setKey(String key){
        this.key = key;
    }
    
    public boolean contains(String key){
        if(this.key.contains(key))
            return true;
        else
            return false;
    }
}
