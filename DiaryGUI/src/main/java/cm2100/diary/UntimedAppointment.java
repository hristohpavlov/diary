package cm2100.diary;

/**
 * 
 * UntimedAppointment.java - created by Hristo Pavlov
 * 
 */

public class UntimedAppointment extends Appointment
{
/**
 * Create an untimed appointment.
 * 
 * @param description   The appointment description.
 * @param date          The appointment date.
 */
public UntimedAppointment(String description,Date date) {
super(description,date);
} //end method

/**
 * Check if the appointment clashes with another one.
 * @param a The other appointment.
 * @return true if there is a clash, false otherwise.
 */
@Override
public boolean clash(Appointment a)
{
return a.occursOn(this.getDate());  //there is a clash if the other one occurs on the same date as the current one
} //end method

/**
 * Return the appointment as a formatted String for saving to a file.
 * @return The appointment as a String.
 */
@Override
public String formatForFile()
{
Date date=this.getDate();

return "UA,"+this.getDescription()+","+     //Note the "UA" field value for untimed appointment
        date.getYear()+","+
        date.getMonth()+","+
        date.getDay();
} //end method
} //end class
