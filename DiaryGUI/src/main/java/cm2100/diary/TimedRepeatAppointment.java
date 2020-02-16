package cm2100.diary;

/**
 * 
 * TimedRepeatAppointment.java - created by Hristo Pavlov
 * 
 */

import java.util.*;

/**
 * 
 * 
 * 
 */

public class TimedRepeatAppointment extends TimedAppointment implements RepeatAppointment {
private RepeatType repeatType;
private Date endDate;
/**
 * Create a timed-repeat appointment.
 * @param description   The appointment description.
 * @param startDate     The starting date of repetition.
 * @param endDate       The ending date of repetition.
 * @param startTime     The starting time.
 * @param endTime       The ending time.
 * @param type          The repeat type according to {@link RepeatType}.
 */
public TimedRepeatAppointment(String description, Date startDate,Date endDate,Time startTime, Time endTime,RepeatType type)
{
super(description,startDate,startTime,endTime);
this.endDate=endDate;
this.repeatType=type;
} //end method

/**
 * Get the repeat type of this timed-repeat appointment.
 * @return  The appointment repeat type. See {@link RepeatType} for possible values.
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
 * Check if the appointment occurs on a certain date.
 * @param date The date to check against.
 * @return true if appointment occurs on the specified date, false otherwise.
 */
@Override
public boolean occursOn(Date date)
{
Date startDate=this.getStartDate(); //get start & end dates of untime-repeating appointment
Date endDate=this.getEndDate();

if (date.compareTo(startDate)<0)    //date is before start date of appointment
    return false;                   //impossible

if (date.compareTo(endDate)>0)      //date is after end date of appointment
    return false;                   //impossible

//now date is within the range of appointment start & end dates
switch (this.repeatType)
    {
    //daily repeating, so it must collide with date
    case DAILY: return true;
    
    //weekly repeating, check if they occur on the same weekday
    case WEEKLY:Calendar startCalendar=Calendar.getInstance();                  //create Java Calendar for start date
                startCalendar.set(Calendar.YEAR,startDate.getYear());           //set year, month, day to start date
                startCalendar.set(Calendar.MONTH,startDate.getMonth()-1);
                startCalendar.set(Calendar.DAY_OF_MONTH,startDate.getDay());
                Calendar dateCalendar=Calendar.getInstance();                   //do the same for date
                dateCalendar.set(Calendar.YEAR,date.getYear());
                dateCalendar.set(Calendar.MONTH,date.getMonth()-1);
                dateCalendar.set(Calendar.DAY_OF_MONTH,date.getDay());
                return startCalendar.get(Calendar.DAY_OF_WEEK)==dateCalendar.get(Calendar.DAY_OF_WEEK); //if they both occur on the same weekday, then clash

    //yearly repeating, check if they are on the same month and day
    case YEARLY:return startDate.getMonth()==date.getMonth() &&     //same month & day, must collide
                        startDate.getDay()==date.getDay();
    default:    return false;
    } //end switch
} //end method
/**
 * Return the appointment as a String for human reading.
 * @return The appointment formatted as a String.
 */
@Override
public String toString()
{
    return this.getDescription()+" ("+
            this.getDate().toString()+" to "+
            this.getEndDate().toString()+", "+
            this.getStartTime().toString()+"-"+
            this.getEndTime().toString()+") "+
            this.repeatType;    //repeat type at the end
} //end method


/**
 * Check if the current timed-repeat appointment equals to another one.
 * @param other The other object to check.
 * @return true if both are TimedRepeatAppointment objects and the corresponding attributes are equal.
 */
@Override
public boolean equals(Object other)
{
if (!(this.getClass().equals(other.getClass())))
    return false;

TimedRepeatAppointment tra=(TimedRepeatAppointment)other;
return super.equals(tra) &&
        this.getRepeatType()==tra.getRepeatType() &&
        this.getEndDate().equals(tra.getEndDate());
}


/**
 * Check if the appointment clashes with another one.
 * @param a The other appointment.
 * @return true if there is a clash, false otherwise.
 */
@Override
public boolean clash(Appointment a)
{
//if the other one is timed, we check for time-slot overlap first
//if there is no overlap, they won't clash even if occurring on the same date    
if (a instanceof TimedAppointment)
    {
    TimedAppointment ta=(TimedAppointment)a;    //cast the other one into TimedAppointment

    Time st1=this.getStartTime();   //get this one's starting time
    Time et1=this.getEndTime();     //ending time
    Time st2=ta.getStartTime();     //get ther other ones' start time
    Time et2=ta.getEndTime();       //ending time

    if (et1.compareTo(st2)<=0)  //1st one ends before the 2nd one starts
        return false;           //no clash (even if they occur on the same day)

    if (et2.compareTo(st1)<=0)  //and 2nd one ends before the 1st one starts
        return false;           //no clash
    }

//now either the other one is untimed
//or it is timed and its time overlaps with the current one
//in both cases if the other one occurs on the same date as the current one, there is a clash

Collection<Date> dates=this.getDates(); //get all dates this appointment occurs on
for (Date date:dates)
    {
    if (a.occursOn(date))   //the other one occurs on a date of this appointment
        return true;        //clash
    }
return false;   //if we are still here after going through all dates, no clash
} //end method

/**
 * Return the timed-repeat-appointment as a formatted String for saving to a file.
 * @return The appointment as a String.
 */
@Override
public String formatForFile()
{
Date startDate=this.getStartDate();
Date endDate=this.getEndDate();
Time st=this.getStartTime();
Time et=this.getEndTime();

return "RTA,"+this.getDescription()+","+    //Note "RTA" field value for a repeat-timed-appointment
        this.getRepeatType()+","+
        startDate.getYear()+","+
        startDate.getMonth()+","+
        startDate.getDay()+","+
        endDate.getYear()+","+
        endDate.getMonth()+","+
        endDate.getDay()+","+
        st.getHour()+","+
        st.getMinute()+","+
        et.getHour()+","+
        et.getMinute();
} //end method

/**
 * Get all dates this repeat appointment occurs as required by the {@link RepeatAppointment} interface.
 * @return A Collection of Date objects.
 */
public Collection<Date> getDates()
{
return RepeatAppointment.getDates(this);    //call handy method provided in RepeatAppointment interface to do the job
} //end method
} //end class

