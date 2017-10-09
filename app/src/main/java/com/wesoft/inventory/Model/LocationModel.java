package com.wesoft.inventory.Model;

/**
 * Created by USER275 on 8/29/2017.
 */

public class LocationModel {
    String Code;
    String Name;

    public final static String TABLE_NAME = "wicra_location";
    public final static String COLUMN_CODE = "loc_code";
    public final static String COLUMN_NAME = "loc_name";

    public LocationModel(String code, String name) {
        Code = code;
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
