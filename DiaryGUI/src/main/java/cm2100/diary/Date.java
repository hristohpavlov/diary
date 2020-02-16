package cm2100.diary;

/**
 * 
 * Date.java - created by Hristo Pavlov
 * 
 */

import java.util.Calendar;

/**
 * 
 *
 * 
 */

public class Date implements Comparable<Date> {
    private int year,month,day;

    //no. of days in a month in a non-leap year
    private static final int[] DAYS_IN_MONTH={31,28,31,30,31,30,31,31,30,31,30,31};

    //months in a year
    private static final String[] MONTHS={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    /**
     * Creates a Date object.
     * @param year  The year of the date. e.g. 2019.
     * @param month The calendar month. e.g. 1 for Jan, 2 for Feb, etc.
     * @param day   The day in a month.
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
    
    /**
     * Compare the current Date with another Date.
     * @param other The other Date.
     * @return  Negative, 0 or positive value depending on the current Date is before, equal, or after the other Date.
     */
    @Override
    public int compareTo(Date other)
    {
        return this.getYear()*10000+this.getMonth()*100+this.getDay()-
                (other.getYear()*10000+other.getMonth()*100+other.getDay());
    }
    
    /**
     * Return the current Date as a String with zero padded if needed.
     * 
     * @return The Date as a String.
     */
    @Override
    public String toString()
    {
        String result="";
        
        if (this.getDay()<10)
            result+="0";            //add leading 0 of day
        result+=this.getDay()+"-";
        
        result+=Date.MONTHS[this.getMonth()-1]+"-";
        result+=this.getYear();
        
        return result;
    }

    /**
     * Compare if the Date equals to another Date based on their year, month, and day.
     * 
     * @param other The other Date to compare with.
     * @return  true if the 2 Date objects present the same calendar date, false otherwise.
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Date))   //if the other object is not a Date
            return false;               //surely they are not equal
        
        Date otherDate=(Date)other;                         //now we know it is a Date, cast it to the proper type
        return this.getYear()==otherDate.getYear() &&       //they are equal only if all fields are equal
                this.getMonth()==otherDate.getMonth() &&
                this.getDay()==otherDate.getDay();
    }
    
    /**
     * This method returns the number of days in a month, taking into consideration of leap year.
     * 
     * @param year  The year.
     * @param month The month in question.
     * @return      The number of days in this month and year.
     */
    public static int daysInMonth(int year,int month)
    {
        if (year%4==0 && month==2)          //if it is a leap year and February
            return 29;                      //then 28 day
        return Date.DAYS_IN_MONTH[month-1]; //otherwise just look up in the table/array (Note: index is month-1)
    }
    
    /**
     * Find out a weekday based on the year, month, and day.
     * @param year  The year. e.g. 2019.
     * @param month The calendar month. e.g. 1 for Jan, 2 for Feb, etc.
     * @param day   The day in a month.
     * @return      The return value is in the range 0-6, meaning Sunday, Monday, ..., Saturday.
     */
    public static int dayInWeek(int year,int month,int day)
    {
    Calendar calendar=Calendar.getInstance();   //create Java Calendar
    calendar.set(Calendar.YEAR,year);           //set year, month, day
    calendar.set(Calendar.MONTH,month-1);
    calendar.set(Calendar.DAY_OF_MONTH,day);
    switch (calendar.get(Calendar.DAY_OF_WEEK))  //find the day in the week
        {
        case Calendar.MONDAY:   return 1;
        case Calendar.TUESDAY:   return 2;
        case Calendar.WEDNESDAY:   return 3;
        case Calendar.THURSDAY:   return 4;
        case Calendar.FRIDAY:   return 5;
        case Calendar.SATURDAY:   return 6;
        default:    return 0;
        }
    } //end method
    
} //end class
