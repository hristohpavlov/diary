package cm2100.diary;

/**
 * 
 * RepeatAppointment.java - created by Hristo Pavlov
 * 
 */

import java.util.*;

/**
 * 
 * 
 * 
 */

public interface RepeatAppointment {
/**
 * Get the repeating type of the repeat appointment.
 * It should be either daily, monthly or yearly.
 * See the enumerated type {@link RepeatType}.
 * @return The repeating type.
 */
public RepeatType getRepeatType();

/**
 * Get the starting date of the repeat appointment.
 * This is the same as the generic date field of a appointment.
 * @return The starting date of repetition.
 */
public Date getStartDate();

/**
 * Get the ending date of the repeat appointment.
 * @return The ending date.
 */
public Date getEndDate();

/**
 * Return all the dates that the repeat appointment occurs.
 * @return A Collection of Date objects.
 */
public Collection<Date> getDates();

/**
 * This handy method is used to find all dates that a repeat appointment occurs.
 * I put it here as it is used by all implementing concrete classes.
 * 
 * Return all occurring dates of a repeat appointment.
 * @param a The repeating appointment.
 * @return A Collection of Date on which the appointment occurs.
 */
public static Collection<Date> getDates(RepeatAppointment a)
{
Date startDate=a.getStartDate();    //get start date
Date endDate=a.getEndDate();        //get end date

ArrayList<Date> result=new ArrayList<>();   //create empty list to hold result

//convert starting date to Java Calendar for calculation
Calendar calendar=Calendar.getInstance();
calendar.set(Calendar.YEAR,startDate.getYear());        //set calendar year, month and day to starting date of appointment
calendar.set(Calendar.MONTH,startDate.getMonth()-1);    //Note that in Java Calendar, month is 0 for January, etc.
calendar.set(Calendar.DAY_OF_MONTH,startDate.getDay());

//convert ending date too
Calendar endCalendar=Calendar.getInstance();
endCalendar.set(Calendar.YEAR,endDate.getYear());
endCalendar.set(Calendar.MONTH,endDate.getMonth()-1);
endCalendar.set(Calendar.DAY_OF_MONTH,endDate.getDay());

//depending on the repeating type, we may increment the day or year by a certain value
int fieldToIncrement;
int valueToIncrement;

switch (a.getRepeatType())
    {
    //daily repeat will increment day by 1
    case DAILY: fieldToIncrement=Calendar.DAY_OF_MONTH;
                valueToIncrement=1;
                break;

    //weekly repeat will increment day by 7
    case WEEKLY:fieldToIncrement=Calendar.DAY_OF_MONTH;
                valueToIncrement=7;
                break;

    //default yearly repeat will increment year by 1
    default:    fieldToIncrement=Calendar.YEAR;
                valueToIncrement=1;
    } //end switch

//while still in the repeating range
while (!calendar.after(endCalendar))
    {
    Date date=new Date(calendar.get(Calendar.YEAR),         //create new Date
                        calendar.get(Calendar.MONTH)+1,     //Note: Need to map Java Calendar month back to our representation in Date
                        calendar.get(Calendar.DAY_OF_MONTH));
    result.add(date);                                   //add to result list
    calendar.add(fieldToIncrement,valueToIncrement);    //increment field, keep looping until we go out of repeating date range
    }
return result;
} //end method
} //end interface
