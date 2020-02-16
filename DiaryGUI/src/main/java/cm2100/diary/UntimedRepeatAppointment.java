package cm2100.diary;

/**
 * 
 * UntimedRepeatAppointment.java - created by Hristo Pavlov
 * 
 */

import java.util.*;

/**
 * 
 * 
 * 
 */

public class UntimedRepeatAppointment extends UntimedAppointment implements RepeatAppointment {
private RepeatType repeatType;
private Date endDate;

/**
 * Create an untimed-repeating appointment that occurs repeatedly within a date range.
 * 
 * @param description   The appointment description.
 * @param startDate     The starting date of repetition.
 * @param endDate       The ending date of repetition.
 * @param type          The repeat type. See {@link RepeatType}.
 */
public UntimedRepeatAppointment(String description,Date startDate,Date endDate,RepeatType type)
{
super(description,startDate);
this.endDate=endDate;
this.repeatType=type;
} //end method

/**
 * Get the repeat type of the untimed-repeat appointment.
 * @return The repeat type. See {@link RepeatType} for valid values and their meanings.
 */
public RepeatType getRepeatType()
{
return this.repeatType;
} //end method

public Date getStartDate()
{
return this.getDate();
} //end method

public Date getEndDate()
{
return this.endDate;
} //end method

/**
 * Return the appointment as a String for human reading.
 * @return The appointment as a String.
 */
@Override
public String toString()
{
    return this.getDescription()+" ("+
            this.getDate().toString()+" to "+
            this.getEndDate().toString()+") "+
            this.repeatType;    //repeat type at the end

} //end method

/**
 * Check if the appointment occurs on a certain date.
 * @param date The date to check against.
 * @return true if appointment occurs on the specified date, false otherwise.
 */
@Override
public boolean occursOn(Date date)
{
Date startDate=this.getStartDate(); //get start & end dates of untime-repeating appointment
Date endDate=this.getEndDate();

if (date.compareTo(startDate)<0)    //date is before start date of repeating appointment
    return false;                   //impossible

if (date.compareTo(endDate)>0)      //date if after end date of repeating appointment
    return false;                   //impossible

//now the other date is within repeating appointment date range
switch (this.repeatType)
    {
    //current untimed appointment is daily repeating
    case DAILY: return true;    //so it must collide with date

    //cuurent untimed appointment is weekly repeating
    //only need to check if they occur on the same day in the week
    case WEEKLY:Calendar startCalendar=Calendar.getInstance();                  //create Java Calendar
                startCalendar.set(Calendar.YEAR,startDate.getYear());           //set year, month, day to start date
                startCalendar.set(Calendar.MONTH,startDate.getMonth()-1);
                startCalendar.set(Calendar.DAY_OF_MONTH,startDate.getDay());
                Calendar dateCalendar=Calendar.getInstance();                   //do the same for date
                dateCalendar.set(Calendar.YEAR,date.getYear());
                dateCalendar.set(Calendar.MONTH,date.getMonth()-1);
                dateCalendar.set(Calendar.DAY_OF_MONTH,date.getDay());
                return startCalendar.get(Calendar.DAY_OF_WEEK)==dateCalendar.get(Calendar.DAY_OF_WEEK); //if they both occur on the same weekday

    //current untimed appointment is yearly repeating
    //check if the month and day are equal
    case YEARLY:return startDate.getMonth()==date.getMonth() &&     //same month & day, must collide
                        startDate.getDay()==date.getDay();
    default:    return false;
    } //end switch
} //end method

/**
 * Check if the untimed-repeat appointment clash with another appointment.
 * @param a The other appointment.
 * @return  true if there is a clash, false otherwise.
 */
@Override
public boolean clash(Appointment a)
{
Collection<Date> dates=this.getDates(); //get all dates this untimed-repeat appointment occurs on

for (Date date:dates)       //iterate through all dates
    {
    if (a.occursOn(date))   //the other appointment occurs on this date
        return true;        //must clash as this one is untimed
    }
return false;   //if exhausted all dates and is still here, no clash
} //end method

/**
 * Check if the current untimed-repeat appointment equals to another one.
 * @param other The other object to check.
 * @return true if both are UntimedRepeatAppointment objects and the corresponding attributes are equal.
 */
@Override
public boolean equals(Object other)
{
if (!(this.getClass().equals(other.getClass())))
    return false;

UntimedRepeatAppointment ura=(UntimedRepeatAppointment)other;
return super.equals(ura) &&
        this.getRepeatType()==ura.getRepeatType() &&
        this.getEndDate().equals(ura.getEndDate());
}

/**
 * Return the untimed-repeat appointment as a formatted String for saving to a file.
 * @return 
 */
@Override
public String formatForFile()
{
Date startDate=this.getStartDate();
Date endDate=this.getEndDate();

return "RUA,"+this.getDescription()+","+    //Note the "RUA" field value for repeat-untimed appointment
        this.getRepeatType()+","+
        startDate.getYear()+","+
        startDate.getMonth()+","+
        startDate.getDay()+","+
        endDate.getYear()+","+
        endDate.getMonth()+","+
        endDate.getDay();
} //end method

/**
 * Get all dates the repeat appointment occurs as required by the RepeatAppointment interface.
 * @return A Collection of Date objects.
 */
public Collection<Date> getDates()
{
return RepeatAppointment.getDates(this);
} //end method
} //end class
