package richmj.com.details;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/15.
 */
public class TimeUtil {
    private static final long Minute=1000*60;
    //一小时
    private static final long Hour = 1000 * 60 * 60;
    //一天
    private static final long Day = Hour * 24;
    //显示月份时候的格式
    private static final String MonthFormat = "MM-dd";

    //显示小时时候的格式
    private static final String HourFormat = "%d小时之前";
    //显示分钟时候的格式
    private static final String MinueteFormat = "%d分钟之前";
    //显示昨天时候的格式
    private static final String YesterdayFormat = "昨天HH:mm";

    //显示年月日时候的格式
    private static final String FullFormat = "yyyy-MM-dd";

    public String getIntervalTimeFromCreateTime(String createTime, String timeSrcFormat) throws ParseException {
        String result = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeSrcFormat);
        Date createData = simpleDateFormat.parse(createTime);
       return getIntervalTimeFromCreateTime(createData);
    }
    public String getIntervalTimeFromCreateTime(Date createData) throws ParseException{
        String result = null;
        Calendar curCalendar = Calendar.getInstance();
        Date curTime = curCalendar.getTime();
        long intervalTime = curTime.getTime() - createData.getTime();
        if (amongOneHour(intervalTime)) {
            result = transformToMinute(intervalTime);
        } else if (amongOneDay(intervalTime)) {
            result = transformToHour(intervalTime);
        } else if (isYesterday(createData)) {
            result = transformToYesterday(createData);
        } else if (amonSameYear(createData, curCalendar)) {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(MonthFormat);
            result = simpleDateFormat1.format(createData);
        } else {
            SimpleDateFormat simpleDateFormatFull = new SimpleDateFormat(FullFormat);
            result = simpleDateFormatFull.format(createData);
        }
        return result;
    }

    public String getIntervalTimeFromCreateTime(String createTime) throws ParseException {
        return getIntervalTimeFromCreateTime(createTime, "yyyy-MM-dd HH:mm:ss");
    }

    private boolean amonSameYear(Date dateOne, Calendar calendarTwo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOne);
        return calendar.get(Calendar.YEAR) == calendarTwo.get(Calendar.YEAR);
    }

    private boolean amongOneHour(long intervalTime) {
        return intervalTime <= Hour&&intervalTime >0;
    }

    private boolean amongOneDay(long intervalTime) {
        return intervalTime <= Day&&intervalTime>0;
    }

    private String transformToMinute(long intervalTime) {
        int minue = (int) (intervalTime / Minute);
        return String.format(MinueteFormat, minue);
    }

    private String transformToHour(long intervalTime) {
        int hour = (int) (intervalTime / Hour);
        return String.format(HourFormat, hour);
    }

    private String transformToYesterday(Date createDate) {
        return (new SimpleDateFormat(YesterdayFormat)).format(createDate);
    }

    private boolean isYesterday(Date createDate) {
        //计算出昨天的时间
        Calendar calendarCur = Calendar.getInstance();
        Calendar calendarYesterday = Calendar.getInstance();
        Date curTime = calendarCur.getTime();
        calendarYesterday.setTimeInMillis(curTime.getTime() - Day);
        calendarYesterday.set(Calendar.HOUR, 0);
        calendarYesterday.set(Calendar.MINUTE, 0);
        calendarYesterday.set(Calendar.SECOND, 0);
        return createDate.getTime() > calendarYesterday.getTimeInMillis() && createDate.getTime() < (calendarYesterday.getTimeInMillis() + Day);
    }


}
