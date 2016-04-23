package cn.accessbright.blade.core.text;

import cn.accessbright.blade.core.utils.Strings;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;


public class DateTimeHandler implements DataTypeHandler {
    public Object handle(String param, Object value) {
        String pattern = Strings.isEmpty(param) ? "yyyy-MM-dd hh:mm:ss" : param;

        Date theDate = determineTheDate(value);
        if (theDate != null) {
            return DateFormatUtils.format(theDate, pattern);
        }
        return Strings.toString(value);
    }

    private Date determineTheDate(Object value) {
        if (value instanceof Date)
            return (Date) value;
        if (value instanceof Long)
            return new Date((Long) value);
        try {
            return DateUtils.parseDate(Strings.toString(value), new String[]{"yyyy-MM", "yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss"});
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}