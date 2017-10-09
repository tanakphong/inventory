package com.wesoft.inventory.Model;

/**
 * Created by USER275 on 8/29/2017.
 */

public class PreCountModel {
    String DocNo;
    String Barcode;
    String Location;
    String Lot;
    String SerialNo;
    Double QtyPreCount;
    String ExpDate;

    public final static String TABLE_NAME = "wicra_precount";
    public final static String COLUMN_DOCNO = "pct_docno";
    public final static String COLUMN_BARCODE = "pct_barcode";
    public final static String COLUMN_LOCATION = "pct_loccode";
    public final static String COLUMN_LOT= "pct_lotno";
    public final static String COLUMN_SERIALNO= "pct_serialno";
    public final static String COLUMN_QTYPERCOUNT = "pct_qty_precount";
    public final static String COLUMN_EXPDATE = "pct_expdate";

    public PreCountModel(String docNo, String barcode, String location, String lot, String serialNo, Double qtyPreCount, String expDate) {
        DocNo = docNo;
        Barcode = barcode;
        Location = location;
        Lot = lot;
        SerialNo = serialNo;
        QtyPreCount = qtyPreCount;
        ExpDate = expDate;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLot() {
        return Lot;
    }

    public void setLot(String lot) {
        Lot = lot;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public Double getQtyPreCount() {
        return QtyPreCount;
    }

    public void setQtyPreCount(Double qtyPreCount) {
        QtyPreCount = qtyPreCount;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }
}
