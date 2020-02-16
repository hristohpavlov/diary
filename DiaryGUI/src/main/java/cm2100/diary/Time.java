package cm2100.diary;

/**
 * 
 * Time.java - created by Hristo Pavlov
 * 
 */

public class Time implements Comparable<Time> {
private int hour,minute;

/**
 * Create a Time object with the given hour and minute.
 * @param hour      The hour in the range 0-23.
 * @param minute    The minute in the range 0-59.
 */
public Time(int hour, int minute) {
    this.hour = hour;
    this.minute = minute;
} //end method

public int getHour() {
    return hour;
} //end method

public void setHour(int hour) {
    this.hour = hour;
} //end method

public int getMinute() {
    return minute;
} //end method

public void setMinute(int minute) {
    this.minute = minute;
} //end method

/**
 * Check equality of this Time with another object.
 * @param other The other Time to check with.
 * @return      true if they represent the same time, false otherwise.
 */
@Override
public boolean equals(Object other)
{
if (!(other instanceof Time))   //the other one is not even a Time
    return false;               //of course not equal

Time otherTime=(Time)other;                     //now we know the other object is Time, cast it accordingly for easier manipulation
return this.getHour()==otherTime.getHour() &&   //check if both hour and minute fields are equal
        this.getMinute()==otherTime.getMinute();
} //end method

/**
 * Return the Time object in a 24-hour format with zero-padded.
 * @return The Time as a String.
 */
@Override
public String toString()
{
String result="";

if (this.hour<10)
    result+="0";        //add leading 0 to hour
result+=this.hour+":";

if (this.minute<10)
    result+="0";        //add leading 0 to minute
result+=this.minute;

return result;
} //end method

/**
 * Compare the Time object with another Time.
 * 
 * @param other The other time.
 * @return      Positive, 0, or negative depends on the current Time is before, equal, or after the other time.
 */
public int compareTo(Time other)
{
return this.getHour()*100+this.getMinute()-other.getHour()*100-other.getMinute();
} //end method
} //end class
