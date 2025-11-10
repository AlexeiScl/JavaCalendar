package calendarproject;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtils {

public static boolean isLeapYear(int year) {
return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
}

public static int getDaysInMonth(int month, int year) {
int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30,
31, 31, 30, 31, 30, 31};
if (month == 2 && isLeapYear(year)) {
return 29;
}
return daysInMonth[month];
}

public static int getStartDay(int month, int year) {
Calendar cal = new GregorianCalendar(year, month - 1, 1);
return cal.get(Calendar.DAY_OF_WEEK);
}

public static boolean isToday(int day, int month, int year) {
Calendar today = Calendar.getInstance();
return (day == today.get(Calendar.DAY_OF_MONTH)
&& month == today.get(Calendar.MONTH) + 1
&& year == today.get(Calendar.YEAR));
}
}
