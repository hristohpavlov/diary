package cm2100.diary;

/**
 * 
 * DiaryCalendar.java - created by Hristo Pavlov
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 * 
 * 
 */

public class DiaryCalendar extends JPanel
{

    private static final Dimension PERFERRED_CALENDAR_SIZE = new Dimension(896, 504);
    private JPanel calendarPanel;
    private Month month;
    private Year year;
    private JTextField day;
    private DiaryList diaryList;
    private Diary appointmentDiary;
    private JTextField yearTextBox;
    private JComboBox<String> monthsDropdown;
    private JScrollPane scrollPane;
    
    public DiaryCalendar() 
    {
        this(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
    }

    public DiaryCalendar(int year, int month)
    {
        super();
        this.setLayout(new GridBagLayout());
        this.year = Year.parse(String.valueOf(year));
        this.month = Month.of(month);
        this.appointmentDiary = new Diary();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel calendarControls = initCalendarControls();
           gbc.fill = GridBagConstraints.HORIZONTAL;
           gbc.gridx = 0;
           gbc.gridy = 1;
        this.add(calendarControls, gbc);

        //Calendar Panel
        this.calendarPanel = new JPanel();
        this.updateCalendar();
           gbc.fill = GridBagConstraints.HORIZONTAL;
           gbc.gridx = 0;
           gbc.gridy = 0;
        this.add(calendarPanel, gbc);
        
        //List Panel
        this.diaryList = new DiaryList();
        this.scrollPane = new JScrollPane();
        this.scrollPane.getViewport().setView(diaryList);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setPreferredSize(new Dimension(370, 725));
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
           gbc.gridx = 1;
           gbc.gridy = 0;
           gbc.insets = new Insets(-220, 0, 0,0);
        this.add(scrollPane, gbc);
    }

    public void setYear(int year) 
    {
        this.year = Year.parse(String.valueOf(year));
        setDay("1");
        this.updateCalendar();
        yearTextBox.setText(String.valueOf(this.year.getValue()));
    }

    public void setMonth(int month)
    {
        this.month = Month.of(month);
        setDay("1");
        this.updateCalendar();
        monthsDropdown.setSelectedIndex(this.month.getValue()-1);
    }

    public void setDay(String day) 
    {
        this.day.setText(day);
    }

    public LocalDate getSelectedDate()
    {
        return LocalDate.of(this.year.getValue(), this.month.getValue(), Integer.parseInt(this.day.getText()));
    }

    private JPanel initCalendarControls()
    {
        final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        JPanel calendarControls = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.day = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd")));
        this.day.setColumns(2);
        this.day.setMargin(new Insets(3, 2, 3, 2));
        this.day.setEditable(true);

        monthsDropdown = new JComboBox<>(months);
        monthsDropdown.setSelectedIndex(this.month.getValue() - 1);
        monthsDropdown.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JComboBox<String> monthsDropdown = (JComboBox<String>) e.getSource();
                setMonth(monthsDropdown.getSelectedIndex() + 1);
            }
        });
        yearTextBox = new JTextField(5);
        yearTextBox.setText(String.valueOf(this.year.getValue()));
        yearTextBox.setMargin(new Insets(3, 2, 3, 2));
        yearTextBox.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String text = yearTextBox.getText();
                try 
                {
                    setYear(Integer.parseInt(text));
                } 
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(calendarPanel, "Invalid year");
                    System.out.println("Invalid year");
                }
            }
        });

        calendarControls.add(this.day);
        calendarControls.add(monthsDropdown);
        calendarControls.add(yearTextBox);

        return calendarControls;
    }

    private Integer[][] getDaysInMonth()
    {
        YearMonth yearMonth = YearMonth.of(this.year.getValue(), this.month);
        int numberOfDaysInMonth = yearMonth.lengthOfMonth();
        Integer[][] days = new Integer[6][7];
        int week = 0;
        for (int i = 1; i <= numberOfDaysInMonth; i++) 
        {
            int dayOfTheWeek = LocalDate.of(this.year.getValue(), this.month, i).getDayOfWeek().getValue();

            if (dayOfTheWeek == 7) 
            {
                dayOfTheWeek = 0;
            }

            days[week][dayOfTheWeek] = i;

            if (dayOfTheWeek == 6)
            {
                week++;
            }
        }
        return days;
    }

    private void updateCalendar() 
    {
        final String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Integer[][] daysInMonth = getDaysInMonth();

        calendarPanel.removeAll();

        Color LIGHT_BLUE = new Color(173, 216, 230);
        Color LBLUE = new Color(135,206,250);
        calendarPanel.setLayout(new GridLayout(7, 7));
        calendarPanel.setPreferredSize(PERFERRED_CALENDAR_SIZE);
        calendarPanel.setBackground(LBLUE);

        for (String day : weekDays) 
        {
            calendarPanel.add(new JLabel(day.substring(0, 3), JLabel.CENTER));
        }

        for (int i = 0; i < daysInMonth.length; i++)
        {
            for (int j = 0; j < daysInMonth[0].length; j++)
            {
                JButton button;

                if (daysInMonth[i][j] != null) 
                {
                    button = new JButton(daysInMonth[i][j].toString());
                    if (appointmentDiary.findAppointments(new Date(this.year.getValue(), this.month.getValue(), daysInMonth[i][j])).size() > 0)
                    {
                        button.setBackground(LIGHT_BLUE);
                    }
                    else
                    {
                        button.setBackground(Color.WHITE);
                    }
                    button.setHorizontalAlignment(SwingConstants.LEFT);
                    button.setVerticalAlignment(SwingConstants.NORTH);
                    button.setFocusPainted(false);
                    button.addActionListener(new AbstractAction() 
                    {
                        @Override
                        public void actionPerformed(ActionEvent e) 
                        {
                            JButton button = (JButton) e.getSource();
                            setDay(button.getText());
                            updateAppointmentsList();
                        }
                    });
                } 
                else 
                {
                    button = new JButton("");
                    button.setBorder(null);
                    button.setEnabled(false);
                    button.setBackground(Color.WHITE);
                }
                button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0, 25)),BorderFactory.createEmptyBorder(10, 10, 0, 0)));
                calendarPanel.add(button);
            }

        }
        calendarPanel.validate();
        calendarPanel.repaint();
    }

    private void updateAppointmentsList() 
    {
        ArrayList<Appointment> appointments = appointmentDiary.findAppointments(new Date(year.getValue(), month.getValue(), Integer.parseInt(day.getText())));
        String[] appointmentsArray = new String[appointments.size()];

        for (int index = 0; index < appointments.size(); index++)
        {
            appointmentsArray[index] = appointments.get(index).toString();
        }
        //this.diaryList.removeAll();
        this.diaryList.setDiary(appointmentsArray);
        //this.diaryList.revalidate();
        //this.diaryList.repaint();
    }

    public void setDiary(Diary appointmentDiary)
    {
        this.appointmentDiary = appointmentDiary;
        updateAppointmentsList();
        updateCalendar();
    }
    
    public Appointment getSelectedAppointment()
    {
       String appointmentName = diaryList.getSelectedAppointment();
            ArrayList<Appointment> matchingAppointments = appointmentDiary.findAppointments(appointmentName.split(" ")[0]);

            for (Appointment appointment : matchingAppointments) 
            {
                if (appointment.toString().equals(appointmentName)) 
                {
                    return appointment;
                }
            } 
            return null;
    }
    
    public void deleteSelectedAppointment()
    {
        try 
        {
            String appointmentName = diaryList.getSelectedAppointment();
            ArrayList<Appointment> matchingAppointments = appointmentDiary.findAppointments(appointmentName.split(" ")[0]);

            for (Appointment appointment : matchingAppointments)
            {
                if (appointment.toString().equals(appointmentName))
                {
                    appointmentDiary.delete(appointment);
                    updateCalendar();
                    updateAppointmentsList();
                }
            }
        } catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(calendarPanel, "Select an Appointment before Deleting");
        }
    }

    public Diary getDiary() {

        return appointmentDiary;
    }
    
    public void search(String word)
    {
        ArrayList<Appointment> appointments = appointmentDiary.findAppointments(word);
        String[] appointmentsArray = new String[appointments.size()];

        for (int index = 0; index < appointments.size(); index++)
        {
            appointmentsArray[index] = appointments.get(index).toString();
        }
        diaryList.setDiary(appointmentsArray);
    }
    
    public void changeMonth(boolean isNext)
    {  
        if(isNext)
        {
            if(month.getValue()== 12)
            {
               setYear(year.getValue()+1);
               setMonth(1);
               
            }
            else
            {
           setMonth(month.getValue()+1); 
            }  
        }
        else
        {
            if(month.getValue()== 1)
            {
               setYear(year.getValue()-1);
               setMonth(12);
            }
            else
            {
           setMonth(month.getValue()-1); 
            }  
        } 
    }
}
