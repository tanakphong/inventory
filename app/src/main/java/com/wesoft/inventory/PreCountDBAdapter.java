package com.wesoft.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wesoft.inventory.model.PreCountModel;

/**
 * Created by tanakorn.pho on 13/03/2560.
 */

public class PreCountDBAdapter {
    public SQLiteDatabase mDatabase;

    public PreCountDBAdapter(Context context) {
        String dbPath = AssetBundle.getAppPackagePath(context) + "/wicra.sqlite";
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void insert(PreCountModel model) {
        ContentValues values = new ContentValues();
        values.put(PreCountModel.COLUMN_DOCNO, model.getDocNo());
        values.put(PreCountModel.COLUMN_BARCODE, model.getBarcode());
        values.put(PreCountModel.COLUMN_LOCATION, model.getLocation());
        values.put(PreCountModel.COLUMN_LOT, model.getLot());
        values.put(PreCountModel.COLUMN_SERIALNO, model.getSerialNo());
        values.put(PreCountModel.COLUMN_QTYPERCOUNT, model.getQtyPreCount());
        values.put(PreCountModel.COLUMN_EXPDATE, model.getExpDate());

        mDatabase.insert(PreCountModel.TABLE_NAME, null, values);
    }

    public PreCountModel query(String _barcode) {
        //UserBean result=null;
        //mDatabase.rawQuery();
        //mDatabase.execSQL();
        String[] selectionAgrs = new String[]{_barcode};
        String[] columns = new String[]{PreCountModel.COLUMN_DOCNO, PreCountModel.COLUMN_LOCATION, PreCountModel.COLUMN_LOT, PreCountModel.COLUMN_SERIALNO, PreCountModel.COLUMN_QTYPERCOUNT, PreCountModel.COLUMN_EXPDATE};
        Cursor cursor = mDatabase.query(PreCountModel.TABLE_NAME, columns, PreCountModel.COLUMN_BARCODE + " = ?", selectionAgrs, null, null, PreCountModel.COLUMN_BARCODE + " ASC");
        //PreCountModel.COLUMN_BARCODE,
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String _doc = cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_DOCNO));
//            String _bar = cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_BARCODE));
            String _loc = cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_LOCATION));
            String _lot = cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_LOT));
            String _serial = cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_SERIALNO));
            Double _qty = Double.valueOf(cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_QTYPERCOUNT)));
            String _expdate = cursor.getString(cursor.getColumnIndex(PreCountModel.COLUMN_EXPDATE));


            cursor.close();
            return new PreCountModel(_doc, _barcode, _loc, _lot, _serial, _qty, Util.DataApp2DateDB(_expdate));
        }

        return null;
    }

    public void update(PreCountModel model) {
        ContentValues values = new ContentValues();
        values.put(PreCountModel.COLUMN_DOCNO, model.getDocNo());
        values.put(PreCountModel.COLUMN_BARCODE, model.getBarcode());
        values.put(PreCountModel.COLUMN_LOCATION, model.getLocation());
        values.put(PreCountModel.COLUMN_LOT, model.getLot());
        values.put(PreCountModel.COLUMN_SERIALNO, model.getSerialNo());
        values.put(PreCountModel.COLUMN_QTYPERCOUNT, model.getQtyPreCount());
        values.put(PreCountModel.COLUMN_EXPDATE, model.getExpDate());

        String whereClause = PreCountModel.COLUMN_BARCODE + " = ?";
        String[] whereAgrs = new String[]{model.getBarcode()};
        mDatabase.update(PreCountModel.TABLE_NAME, values, whereClause, whereAgrs);
    }

}
