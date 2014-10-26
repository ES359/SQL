package me.ES359.SQL;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ES359 on 10/24/14.
 */
public class Timestamp  {

    Calendar cal = Calendar.getInstance();

    Date now = cal.getTime();

   public java.sql.Timestamp stamp = new java.sql.Timestamp(now.getTime());


    public java.sql.Timestamp getStamp() {
        return stamp;
    }

}
