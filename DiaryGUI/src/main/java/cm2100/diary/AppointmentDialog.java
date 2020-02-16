package cm2100.diary;

/**
 * 
 * AppointmentDialog.java - created by Hristo Pavlov
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.stream.*;

/**
 *
 * 
 * 
 */

public class AppointmentDialog extends JFrame {

    //Declaring components
    private JPanel thePanel, row1, row2, row3, row4, row5, row6, row7, row8, newPanel;
    private GridBagConstraints gbc;
    private JTextField descTextField, yearTextField, endYearTextField;
    private JLabel descLabel, dateLabel, repeatLabel, endDateLabel, startTimeLabel, endTimeLabel, semiColumnLabelStart, semiColumnLabelEnd;
    private JComboBox monthComboBox, dayComboBox, repeatComboBox, endMonthComboBox, endDayComboBox, startTimeHours, startTimeMinutes, endTimeHours, endTimeMinutes;
    private JCheckBox repeatCheckBox, timedCheckBox;
    private final String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final String repeatTypes[] = {"Daily", "Weekly", "Yearly"};
    private final Integer[] days = IntStream.range(1, 32).boxed().toArray(Integer[]::new);
    private final Integer[] hours = IntStream.range(0, 24).boxed().toArray(Integer[]::new);
    private final Integer[] minutes = IntStream.range(0, 60).boxed().toArray(Integer[]::new);

    private void initComponents() 
    {
        Font font = new Font(Font.SERIF, Font.PLAIN, 18);
        
       //main Panel with gbc constraints
         thePanel = new JPanel();
         thePanel.setSize(500, 600);
         gbc = new GridBagConstraints();
         thePanel.setLayout(new GridBagLayout());
         row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         row8 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //Elements for row1 //DESCRIPTION
        descTextField = new JTextField();
         descTextField.setFont(font);
         descTextField.setPreferredSize(new Dimension(360, 30));
        descLabel = new JLabel("Description:");
         descLabel.setFont(font);
        row1.add(descLabel);
        row1.add(descTextField);
           gbc.gridx = 0;
           gbc.gridy = 0;
        thePanel.add(row1, gbc);
        //Elements for row2 //START DATE
        dateLabel = new JLabel("Date:");
         dateLabel.setFont(font);
        yearTextField = new JTextField();
         yearTextField.setFont(font);
         yearTextField.setPreferredSize(new Dimension(80, 30));
         yearTextField.setText(String.valueOf(LocalDate.now().getYear()));
        monthComboBox = new JComboBox(months);
         monthComboBox.setSelectedIndex(LocalDate.now().getMonthValue()-1);
        dayComboBox = new JComboBox(days);
         dayComboBox.setSelectedIndex(LocalDate.now().getDayOfMonth()-1);
        row2.add(dateLabel);
        row2.add(dayComboBox);
        row2.add(monthComboBox);
        row2.add(yearTextField);
           gbc.gridx = 0;
           gbc.gridy = 1;
        thePanel.add(row2, gbc);
        //Elements for row3 //REPEAT
        repeatCheckBox = new JCheckBox("Repeat");
         repeatCheckBox.setFont(font);
         ActionListener repeatActionListener = new ActionListener() 
         {
              public void actionPerformed(ActionEvent actionEvent) 
             {
              AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
              boolean selected = abstractButton.getModel().isSelected();
              if(selected == true)
              {
                  repeatComboBox.setEnabled(true);
                  endYearTextField.setEnabled(true);
                  endMonthComboBox.setEnabled(true);
                  endDayComboBox.setEnabled(true);
              }
              else
              {
                repeatComboBox.setEnabled(false);
                endYearTextField.setEnabled(false);
                endMonthComboBox.setEnabled(false);
                endDayComboBox.setEnabled(false);  
              }
             }
         };
         repeatCheckBox.addActionListener(repeatActionListener);
        repeatLabel = new JLabel("Repeat Type");
         repeatLabel.setFont(font);
        repeatComboBox = new JComboBox(repeatTypes);
         repeatComboBox.setEnabled(false);
        row3.add(repeatCheckBox);
        row3.add(repeatLabel);
        row3.add(repeatComboBox);
           gbc.gridx = 0;
           gbc.gridy = 2;
        thePanel.add(row3, gbc);
        //Elements for row4 //END DATE
        endDateLabel = new JLabel("End Date:");
         endDateLabel.setFont(font);
        endYearTextField = new JTextField();
         endYearTextField.setFont(font);
         endYearTextField.setPreferredSize(new Dimension(80, 30));
         endYearTextField.setEnabled(false);
        endMonthComboBox = new JComboBox(months);
         endMonthComboBox.setEnabled(false);
        endDayComboBox = new JComboBox(days);
         endDayComboBox.setEnabled(false);
        row4.add(endDateLabel);
        row4.add(endDayComboBox);
        row4.add(endMonthComboBox);
        row4.add(endYearTextField);
           gbc.gridx = 0;
           gbc.gridy = 3;
        thePanel.add(row4, gbc);
        //Elements for row5 //TIMED
        timedCheckBox = new JCheckBox("Timed");
         timedCheckBox.setFont(font);
        ActionListener timedActionListener = new ActionListener() 
         {
              public void actionPerformed(ActionEvent actionEvent) 
             {
              AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
              boolean selected = abstractButton.getModel().isSelected();
              if(selected == true)
              {
                startTimeMinutes.setEnabled(true);
                startTimeHours.setEnabled(true);
                endTimeMinutes.setEnabled(true);
                endTimeHours.setEnabled(true);
              }
              else
              {
                startTimeMinutes.setEnabled(false);
                startTimeHours.setEnabled(false);
                endTimeMinutes.setEnabled(false);
                endTimeHours.setEnabled(false); 
              }
             }
         };
         timedCheckBox.addActionListener(timedActionListener);
        row5.add(timedCheckBox);
           gbc.gridx = 0;
           gbc.gridy = 4;
        thePanel.add(row5, gbc);
        //Elements for row6 //START TIME
        startTimeLabel = new JLabel("Start Time");
         startTimeLabel.setFont(font);
        semiColumnLabelStart = new JLabel(":");
         semiColumnLabelStart.setFont(font);
        startTimeMinutes = new JComboBox(minutes);
         startTimeMinutes.setEnabled(false);
        startTimeHours = new JComboBox(hours);
         startTimeHours.setEnabled(false);
        row6.add(startTimeLabel);
        row6.add(startTimeHours);
        row6.add(semiColumnLabelStart);
        row6.add(startTimeMinutes);
           gbc.gridx = 0;
           gbc.gridy = 5;
        thePanel.add(row6, gbc);
        //Elements for row 7 //END TIME
        endTimeLabel = new JLabel("End Time");
         endTimeLabel.setFont(font);
        semiColumnLabelEnd = new JLabel(":");
         semiColumnLabelEnd.setFont(font);
        endTimeMinutes = new JComboBox(minutes);
         endTimeMinutes.setEnabled(false);
        endTimeHours = new JComboBox(hours);
         endTimeHours.setEnabled(false);
        row7.add(endTimeLabel);
        row7.add(endTimeHours);
        row7.add(semiColumnLabelEnd);
        row7.add(endTimeMinutes);
           gbc.gridx = 0;
           gbc.gridy = 6;
        thePanel.add(row7, gbc);
          //Elements for row 8 //BUTTONS
          //saveButton = new JButton("Save");
          //closeButton = new JButton("Close");
          //row8.add(saveButton);
          //row8.add(closeButton);
          //gbc.gridx = 0;
          //gbc.gridy = 7;
          //thePanel.add(row8,gbc);
    }

    private Appointment openDialog() {
        Appointment appointment;
        Object[] choices = {"Save", "Cancel"};
        int result = JOptionPane.showOptionDialog(thePanel.getParent(), thePanel, "Appointment dialog", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
        if (result == JOptionPane.OK_OPTION)
        {
            String description = descTextField.getText();
            Date startDate = new Date(Integer.parseInt(yearTextField.getText()), monthComboBox.getSelectedIndex() + 1, dayComboBox.getSelectedIndex()+1);

            if (repeatCheckBox.isSelected()) 
            {
                Date endDate = new Date(Integer.parseInt(endYearTextField.getText()), endMonthComboBox.getSelectedIndex() + 1, endDayComboBox.getSelectedIndex()+1);
                RepeatType type = RepeatType.values()[repeatComboBox.getSelectedIndex()];

                if (timedCheckBox.isSelected()) 
                {
                    Time startTime = new Time((startTimeHours.getSelectedIndex()), (startTimeMinutes.getSelectedIndex()));
                    Time endTime = new Time((endTimeHours.getSelectedIndex()), (endTimeMinutes.getSelectedIndex()));

                    appointment = new TimedRepeatAppointment(description, startDate, endDate, startTime, endTime,
                            type);
                } 
                else 
                {
                    appointment = new UntimedRepeatAppointment(description, startDate, endDate, type);
                }
            } 
            else
            {
                if (timedCheckBox.isSelected())
                {
                    Time startTime = new Time((startTimeHours.getSelectedIndex()), (startTimeMinutes.getSelectedIndex()));
                    Time endTime = new Time((endTimeHours.getSelectedIndex()), (endTimeMinutes.getSelectedIndex()));

                    appointment = new TimedAppointment(description, startDate, startTime, endTime);
                } 
                else 
                {
                    appointment = new UntimedAppointment(description, startDate);
                }
            }
            return appointment;
        } 
        else
        {
            return null;
        }
    }

    public Appointment createDialog() {
        initComponents();
        return openDialog();
    }
    public Appointment editDialog(Appointment appointment) {
        initComponents();
        descTextField.setText(appointment.getDescription());
        yearTextField.setText((String.valueOf(appointment.getDate().getYear())));
        monthComboBox.setSelectedIndex(appointment.getDate().getMonth()-1);
        dayComboBox.setSelectedIndex(appointment.getDate().getDay()-1);
        if(appointment instanceof RepeatAppointment)
        {      
            repeatCheckBox.setSelected(true);
             repeatComboBox.setSelectedIndex(RepeatType.valueOf(((RepeatAppointment)appointment).getRepeatType().toString()).ordinal());
            repeatComboBox.setEnabled(true);
             endDayComboBox.setSelectedIndex(((RepeatAppointment)appointment).getEndDate().getDay()-1);
            endDayComboBox.setEnabled(true);
             endMonthComboBox.setSelectedIndex(((RepeatAppointment)appointment).getEndDate().getMonth()-1);
            endMonthComboBox.setEnabled(true);
             endYearTextField.setText(String.valueOf(((RepeatAppointment)appointment).getEndDate().getYear()));
            endYearTextField.setEnabled(true);
 
            if(appointment instanceof TimedRepeatAppointment)
            {
                timedCheckBox.setSelected(true);
                 startTimeHours.setSelectedIndex(((TimedRepeatAppointment)appointment).getStartTime().getHour());
                startTimeHours.setEnabled(true);
                 startTimeMinutes.setSelectedIndex(((TimedRepeatAppointment)appointment).getStartTime().getMinute());
                startTimeMinutes.setEnabled(true);
                 endTimeHours.setSelectedIndex(((TimedRepeatAppointment)appointment).getEndTime().getHour());
                endTimeHours.setEnabled(true);
                 endTimeMinutes.setSelectedIndex(((TimedRepeatAppointment)appointment).getEndTime().getMinute());
                endTimeMinutes.setEnabled(true);
            }
            else
            {
                timedCheckBox.setSelected(false);
            }
        }
        else
        {
            repeatCheckBox.setSelected(false);
 
            if(appointment instanceof TimedAppointment)
            {
                timedCheckBox.setSelected(true);
                 startTimeHours.setSelectedIndex(((TimedAppointment)appointment).getStartTime().getHour());
                startTimeHours.setEnabled(true);
                 startTimeMinutes.setSelectedIndex(((TimedAppointment)appointment).getStartTime().getMinute());
                startTimeMinutes.setEnabled(true);
                 endTimeHours.setSelectedIndex(((TimedAppointment)appointment).getEndTime().getHour());
                endTimeHours.setEnabled(true);
                 endTimeMinutes.setSelectedIndex(((TimedAppointment)appointment).getEndTime().getMinute());
                endTimeMinutes.setEnabled(true);
                
            }
            else
            {
                timedCheckBox.setSelected(false);
            }
 
        }
        return openDialog();
    }
}
