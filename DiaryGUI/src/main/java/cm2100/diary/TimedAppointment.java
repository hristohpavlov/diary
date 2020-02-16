package cm2100.diary;

/**
 * 
 * TimedAppointment.java - created by Hristo Pavlov
 * 
 */

public class TimedAppointment extends Appointment
{
private Time startTime,endTime;

/**
 * Create a timed appointment.
 * @param description   The appointment description.
 * @param date          The date on which the appointment occurs.
 * @param startTime     The starting time.
 * @param endTime       The ending time.
 */
public TimedAppointment(String description, Date date,Time startTime, Time endTime) {
    super(description, date);   //use superclass constructor to initialise description and date
    this.startTime = startTime;
    this.endTime = endTime;
} //end method

public Time getStartTime() {
    return startTime;
} //end method

public void setStartTime(Time startTime) {
    this.startTime = startTime;
} //end method

public Time getEndTime() {
    return endTime;
} //end method

public void setEndTime(Time endTime) {
    this.endTime = endTime;
} //end method

/**
 * Return the appointment as a String for human reading.
 * @return The appointment as a String.
 */
@Override
public String toString()
{
return this.getDescription()+" ("+
        this.getDate().toString()+", "+
        this.getStartTime().toString()+"-"+
        this.getEndTime().toString()+")";
} //end method

/**
 * Check if the current timed-appointment equals to another one.
 * @param other The other object to check.
 * @return true if both are TimedAppointment objects and the corresponding attributes are equal.
 */
@Override
public boolean equals(Object other)
{
if (!(this.getClass().equals(other.getClass())))
    return false;

TimedAppointment ta=(TimedAppointment)other;
return super.equals(ta) &&
        this.getStartTime().equals(ta.getStartTime()) &&
        this.getEndTime().equals(ta.getEndTime());
}

/**
 * Check if the appointment clashes with another appointment.
 * @param a The other appointment.
 * @return  true if there is a clash, false otherwise.
 */
public boolean clash(Appointment a)
{
if (!a.occursOn(this.getDate()))    //the other one does on occur on the date of this one
    return false;                   //no clash

//now it occurs on the same date as this one
if (a instanceof UntimedAppointment)        //the other one is untimed which spans the whole date
    return true;                            //surely clash

if (a instanceof TimedAppointment)          //the other one is also timed, need to check if time slots overlap
    {
    TimedAppointment ta=(TimedAppointment)a;    //cast the other one for easier manipulation

    Time st1=this.getStartTime();   //get current one start & end time
    Time et1=this.getEndTime();
    Time st2=ta.getStartTime();     //get the other one start & end time
    Time et2=ta.getEndTime();

    if (et1.compareTo(st2)<=0)  //1st one ends before 2nd one starts
        return false;           //no clash
    if (et2.compareTo(st1)<=0)  //2nd one ends before 1st one starts
        return false;           //no clash
    return true;                //in all other cases the time slots overlap
    }
return false;   //if we come to here, no clash is detected
} //end method

/**
 * Return the appointment as a formatted String for saving to a file.
 * @return 
 */
@Override
public String formatForFile()
{
Date date=this.getDate();
Time st=this.getStartTime();
Time et=this.getEndTime();

return "TA,"+this.getDescription()+","+
        date.getYear()+","+
        date.getMonth()+","+
        date.getDay()+","+
        st.getHour()+","+
        st.getMinute()+","+
        et.getHour()+","+
        et.getMinute();
} //end method
} //end class