package com.wesoft.inventory.Model;

/**
 * Created by USER275 on 8/29/2017.
 */

public class ProductModel {
    String Barcode;
    String Code;
    String Type;
    String Name;
    String Unit;
    Double Price;

    public final static String TABLE_NAME = "wicra_product";
    public final static String COLUMN_BARCODE = "prod_barcode";
    public final static String COLUMN_CODE = "prod_code";
    public final static String COLUMN_TYPE = "prod_type";
    public final static String COLUMN_NAME= "prod_name";
    public final static String COLUMN_UNIT = "prod_unit";
    public final static String COLUMN_PRICE = "prod_price";

    public ProductModel(String barcode, String code, String type, String name, String unit, Double price) {
        Barcode = barcode;
        Code = code;
        Type = type;
        Name = name;
        Unit = unit;
        Price = price;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
