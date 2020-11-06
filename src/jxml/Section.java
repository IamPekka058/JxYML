package jxml;

import java.util.HashMap;

public class Section {
    private String classname = "";
    private HashMap<String, String> params = new HashMap();

    public Section(String classname){
        this.classname = classname;
    }

    public void transmitParams(HashMap parameters) {
        params.putAll(parameters);
    }

    public HashMap getParams() {
        return params;
    }

    public String getParam(String key){
        return params.get(key);
    }

    public void addParam(String key, String value){
        params.put(key,value);
    }

    public void removeParam(String key){
        params.remove(key);
    }

    public String getClassname() {
        return classname;
    }
}
