package cm2100.diary;

/**
 * 
 * DiaryList.java - created by Hristo Pavlov
 * 
 */

import javax.swing.*;
import java.awt.*;

/**
 * 
 * 
 * 
 */

public class DiaryList extends JPanel
{
    //Declaring GUI components
    public JList<String> theList;
    public JScrollPane scrollPane;
    public DefaultListModel<String> listModel;
    public DiaryList(){
        initComponents();
    }
     public void initComponents()
      {
         Font  font  = new Font(Font.SERIF, Font.PLAIN,  18);
         listModel =new DefaultListModel<>();
         theList = new JList<>(listModel);
         theList.setFont(font);
         this.add(theList); 
      }
     public void setDiary(String[] appointmentArray)
      {
         theList.removeAll();
         theList.setListData(appointmentArray);
         theList.revalidate();  
      }
     public String getSelectedAppointment()
      {
         return theList.getModel().getElementAt(theList.getSelectedIndex());
      }
}
