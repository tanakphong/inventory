package com.wesoft.inventory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by USER275 on 8/29/2017.
 */

public class Util {
    public static String DataApp2DateDB(String strDate){
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date _date = df.parse(strDate);
            DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
            String _convert_date = df2.format(_date);
            return _convert_date;
        }catch (Exception aE){
            return strDate;
        }
    }
}
