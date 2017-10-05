package com.wesoft.inventory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wesoft.inventory.model.LocationModel;

import java.util.ArrayList;

/**
 * Created by USER275 on 9/5/2017.
 */

public class TestDBAdapter {
    public SQLiteDatabase mDatabase;


    public TestDBAdapter(Context context) {
        String dbPath = AssetBundle.getAppPackagePath(context) + "/wicra.sqlite";
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void CreateTable() {
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(Username VARCHAR,Password VARCHAR);");
        mDatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin','admin');");
        mDatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin1','admin1');");
        mDatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin2','admin2');");
    }

    public ArrayList<LocationModel> getTable() {
        ArrayList<LocationModel> array_list = new ArrayList<LocationModel>();

        //hp = new HashMap();
        Cursor res = mDatabase.rawQuery("select * from TutorialsPoint", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            array_list.add(new LocationModel(res.getString(res.getColumnIndex("Username")),
                    res.getString(res.getColumnIndex("Password"))));
            res.moveToNext();
        }
        return array_list;
    }

    public LocationModel getRow(String val) {
        Cursor res = mDatabase.rawQuery("select * from TutorialsPoint where username ='" + val + "'", null);
        if (res != null && res.moveToFirst()) {
//            Log.i("dlg", "getRow: Start");
//            res.moveToFirst();
//            Log.i("dlg", "getRow: moveToFirst");
            LocationModel loc = new LocationModel(res.getString(res.getColumnIndex("Username")),
                    res.getString(res.getColumnIndex("Password")));

//            Log.i("dlg", "getRow: LocationModel");
            return loc;
//        do {
//            // ...
//        } while (res.moveToNext());
//
//        res.close();
        }
        return null;
    }

    public void TruncateTable() {
        mDatabase.execSQL("DELETE FROM TutorialsPoint;");
        mDatabase.execSQL("VACUUM;");
        Log.i("dlg", "TruncateTable: Completed");
    }
}
