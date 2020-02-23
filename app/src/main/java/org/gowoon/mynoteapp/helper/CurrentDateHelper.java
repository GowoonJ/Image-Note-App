package org.gowoon.mynoteapp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDateHelper {
    public SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();

    public String getDateString() {
        return format1.format(date);
    }

    public Date getDate(){
        return date;
    }
}
