package br.com.battista.myoffers.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by rabsouza on 16/04/16.
 */
public class DateFormatUtil {

    public static String format(Date date) {
        if (date == null) {
            return null;
        }

        DateTime dt = new DateTime(date);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/mm/yyyy HH:mm");
        return fmt.print(dt);
    }

}
