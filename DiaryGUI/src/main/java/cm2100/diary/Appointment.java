package cm2100.diary;

/**
 * 
 * Appointment.java - created by Hristo Pavlov
 * 
 */

import java.util.Comparator;

/**
 * 
 * 
 * 
 */

public abstract class Appointment implements Comparable<Appointment> {
    private String description;
    private Date date;

    /**
     * Constructor to fill in the description and date of an appointment.
     * 
     * @param description   A simple description of the appointment.
     * @param date          The date on which the appointment occurs.
     */
    public Appointment(String description,Date date) {
        this.description = description;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.getDescription()+" ("+this.getDate().toString()+")";
    }
    
    /**
     * Check if the appointment occurs on a specific date.
     * @param date  The given date.
     * @return true if appointment occurs on the date, false otherwise.
     */
    public boolean occursOn(Date date)
    {
        return this.getDate().equals(date);
    }
    
    /**
     * Check if the appointment clashes with another one.
     * @param a The other appointment.
     * @return true if there is a clash, false otherwise.
     */
    public abstract boolean clash(Appointment a);
    
    /**
     * Check if the current appointment equals to the other object.
     * @param other The other object.
     * @return true if both are Appointment objects and the corresponding attributes are equal.
     */
    @Override
    public boolean equals(Object other)
    {
    if (!this.getClass().equals(other.getClass()))
        return false;
    Appointment otherA=(Appointment)other;
    
    return this.getDescription().equals(otherA.getDescription()) &&
            this.getDate().equals(otherA.getDate());
    }
    @Override
    public int compareTo(Appointment other)
    {
        return this.getDescription().compareTo(other.getDescription());
    }
   
    /**
     * A comparator that sort appointments by their description field.
     */
    public static class DateTimeComparator implements Comparator<Appointment>
    {
        public int compare(Appointment a,Appointment b)
        {
            int result=a.getDate().compareTo(b.getDate());
            if (result!=0)
                return result;
            
            Time aTime,bTime;

            if (a instanceof TimedAppointment)
                aTime=((TimedAppointment)a).getStartTime();
            else aTime=new Time(0,0);

            if (b instanceof TimedAppointment)
                bTime=((TimedAppointment)b).getStartTime();
            else bTime=new Time(0,0);
            
            result=aTime.compareTo(bTime);
            
            if (result!=0)
                return result;
            
            return a.getDescription().compareTo(b.getDescription());
        }
    }
    
    /**
     * This abstract method returns the appointment as a formatted string for writing to a file.
     * @return The appointment as a String.
     */
    public abstract String formatForFile();
    
} //end class
