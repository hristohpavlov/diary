package cm2100.diary;

/**
 * 
 * GUI.java - created by Hristo Pavlov
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.*;

/**
 *
 * 
 *
 */

public class GUI extends JFrame 
{
    //Declating GUI Components
    private JPanel thePanel, gridPanelCalendar, panelButtonsGrid;
    private JButton monthPlus, monthMinus, today, createDiary, deleteDiary, editDiary, searchButton;
    private JLabel searchLabel, filler1, filler2, filler3;
    private JTextField searchField;
    private DiaryCalendar calendar;
    private JMenu fileMenu;
    private JMenuBar fileMenuBar;
    private JMenuItem Save, Open, Exit;
    private GridBagConstraints gbc;
    private Diary appointmentDiary;
    public DiaryList diaryList;

    public GUI() {
        initComponents();
    }

    private void initComponents()
    {
        Font font = new Font(Font.SERIF, Font.PLAIN, 18);
        this.setSize(1285, 725);
        final ImageIcon img = new ImageIcon("images/diaryIcon.png");
        this.setIconImage(img.getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Personal Diary");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Init Diary
        appointmentDiary = new Diary();
        //Main Panel
        thePanel = new JPanel();
        thePanel.setLayout(new BorderLayout());
        //Grid Panel for Calendar and List
        gbc = new GridBagConstraints();
        gridPanelCalendar = new JPanel();
        gridPanelCalendar.setLayout(new GridBagLayout());
        //Calendar
           gbc.fill = GridBagConstraints.HORIZONTAL;
           gbc.gridx = 0;
           gbc.gridy = 0;
           gbc.gridwidth = 2;
           gbc.insets = new Insets(-34, 0, 0, 30);
        calendar = new DiaryCalendar();
         calendar.setBorder(BorderFactory.createEmptyBorder(-25, 0, 0, 0));
        gridPanelCalendar.add(calendar, gbc);
        thePanel.add(calendar, BorderLayout.CENTER);
        //Buttons Panel
        panelButtonsGrid = new JPanel();
        panelButtonsGrid.setLayout(new GridBagLayout());
        //MonthMinus
           gbc.gridx = 6;
           gbc.gridy = 1;
           gbc.gridwidth = 1;
           gbc.insets = new Insets(0, 0, 10, 10);
        monthMinus = new JButton("<");
         monthMinus.setFont(font);
         monthMinus.setPreferredSize(new Dimension(90, 30));
        monthMinus.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ev) 
            {
                calendar.changeMonth(false);
            }
        });
        panelButtonsGrid.add(monthMinus, gbc);
        //today
           gbc.gridx = 7;
           gbc.gridy = 1;
           gbc.gridwidth = 1;
        today = new JButton("Today");
         today.setFont(font);
         today.setBounds(50, 100, 95, 30);
         today.setPreferredSize(new Dimension(60, 30));
        today.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ev) 
            {
                calendar.setYear(LocalDate.now().getYear());
                calendar.setMonth(LocalDate.now().getMonthValue());
                calendar.setDay(String.valueOf(LocalDate.now().getDayOfMonth()));
            }
        });
        panelButtonsGrid.add(today, gbc);
        //MonthPlus
           gbc.gridx = 8;
           gbc.gridy = 1;
           gbc.gridwidth = 1;
        monthPlus = new JButton(">");
         monthPlus.setFont(font);
         monthPlus.setPreferredSize(new Dimension(90, 30));
        monthPlus.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ev)
            {
                calendar.changeMonth(true);
            }
        });
        panelButtonsGrid.add(monthPlus, gbc);
        thePanel.add(panelButtonsGrid, BorderLayout.SOUTH);
        //Fillers
           gbc.gridx = 3;
           gbc.gridy = 0;
           gbc.gridwidth = 1;
        filler1 = new JLabel(" ");
        panelButtonsGrid.add(filler1, gbc);
           gbc.gridx = 4;
           gbc.gridy = 0;
           gbc.gridwidth = 1;
        filler2 = new JLabel(" ");
        panelButtonsGrid.add(filler2, gbc);
           gbc.gridx = 5;
           gbc.gridy = 0;
           gbc.gridwidth = 1;
        filler3 = new JLabel(" ");
        panelButtonsGrid.add(filler3, gbc);
        //Search
           gbc.gridx = 6;
           gbc.gridy = 0;
           gbc.gridwidth = 1;
        searchLabel = new JLabel("Keyword:");
         searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
         searchLabel.setFont(font);
        panelButtonsGrid.add(searchLabel, gbc);
           gbc.gridx = 7;
           gbc.gridy = 0;
           gbc.gridwidth = 1;
        searchField = new JTextField(null);
         searchField.setFont(font);
         searchField.setPreferredSize(new Dimension(150, 30));
        panelButtonsGrid.add(searchField, gbc);
           gbc.gridx = 8;
           gbc.gridy = 0;
           gbc.gridwidth = 1;
        searchButton = new JButton("Search");
         searchButton.setFont(font);
         searchButton.setPreferredSize(new Dimension(90, 30));
        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ev) 
            {
                calendar.search(searchField.getText());
            }
        });
        panelButtonsGrid.add(searchButton, gbc);
        //Create Appointment
           gbc.gridx = 3;
           gbc.gridy = 1;
           gbc.gridwidth = 1;
        createDiary = new JButton("Create Appointment");
         createDiary.setFont(font);
         createDiary.setPreferredSize(new Dimension(200, 40));
        createDiary.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ev) 
            {
                AppointmentDialog appointmentDialog = new AppointmentDialog();
                Appointment appointment = appointmentDialog.createDialog();

                if (appointment != null) 
                {
                    appointmentDiary.add(appointment);
                    calendar.setDiary(appointmentDiary);
                }
                //Appointment newAppointment = appointmentDialog.createDialog();
                //appointmentDiary.add(appointmentDialog.createDialog());
                //calendar.setDiary(appointmentDiary);
                //diaryList.setDiary(appointmentDiary);
            }
        });
        panelButtonsGrid.add(createDiary, gbc);
        //Edit Diary
           gbc.gridx = 4;
           gbc.gridy = 1;
           gbc.gridwidth = 1;
        editDiary = new JButton("Edit Appointment");
         editDiary.setFont(font);
         editDiary.setPreferredSize(new Dimension(200, 40));
        editDiary.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ev) 
            {
                try 
                {
                    Appointment appointment = calendar.getSelectedAppointment();
                    AppointmentDialog appointmentDialog = new AppointmentDialog();
                    Appointment newAppointment = appointmentDialog.editDialog(appointment);
                    if (newAppointment != null)
                    {
                        int result = JOptionPane.showConfirmDialog(calendar, "Save changes?", "Confirm Edit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION)
                        {
                            appointmentDiary.add(newAppointment);
                            appointmentDiary.delete(appointment);
                            calendar.setDiary(appointmentDiary);
                        }
                    }
                    //Appointment newAppointment = appointmentDialog.createDialog();
                    //appointmentDiary.add(appointmentDialog.editDialog(appointment));
                    //appointmentDiary.delete(appointment);
                    //calendar.setDiary(appointmentDiary);
                    //calendar.setDiary(appointmentDiary);
                    //diaryList.setDiary(appointmentDiary);
                } 
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(calendar, "Select an Appointment before editing");
                }
            }
        });
        panelButtonsGrid.add(editDiary, gbc);
        //Delete Diary
           gbc.gridx = 5;
           gbc.gridy = 1;
           gbc.gridwidth = 1;
        deleteDiary = new JButton("Delete Appointment");
         deleteDiary.setFont(font);
         deleteDiary.setPreferredSize(new Dimension(200, 40));
        deleteDiary.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ev)
            {
                try 
                {
                    Appointment appointment = calendar.getSelectedAppointment();
                    if (appointment != null) 
                    {
                        int result = JOptionPane.showConfirmDialog(calendar, "Save changes?", "Confirm Deleteion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION)
                        {
                            calendar.deleteSelectedAppointment();
                            appointmentDiary = calendar.getDiary();
                        }
                    }
                }
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(calendar, "Select an Appointment before deleting");
                }
            }
        });
         panelButtonsGrid.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panelButtonsGrid.add(deleteDiary, gbc);
        //File Menu
        fileMenu = new JMenu("File");
        fileMenuBar = new JMenuBar();
        Save = new JMenuItem("Open Diary");
        Save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                JFileChooser fileChooser = new JFileChooser();
                int choice = fileChooser.showOpenDialog(fileMenuBar);

                if (choice == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();

                    try
                    {
                        appointmentDiary.load(file);
                        calendar.setDiary(appointmentDiary);
                        //calendar.setDay(String.valueOf(LocalDate.now().getDayOfMonth()));
                        JOptionPane.showMessageDialog(calendar, "File Opened");
                    } 
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(calendar, "Something went wrong with Importing");
                    }
                }
            }
        });
        Open = new JMenuItem("Save Diary");
        Open.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                int choice = fileChooser.showSaveDialog(fileMenuBar);
                if (choice == JFileChooser.APPROVE_OPTION) 
                {
                    File file = fileChooser.getSelectedFile();

                    try
                    {
                        appointmentDiary.save(file);
                        JOptionPane.showMessageDialog(calendar, "File saved");
                    } 
                    catch (Exception ex) 
                    {
                        JOptionPane.showMessageDialog(calendar, "Something went wrong with Exporting");
                    }
                }
            }
        });
        Exit = new JMenuItem("Exit");
        Exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ev) 
            {
                System.exit(0);
            }
        });
        fileMenu.add(Save);
        fileMenu.add(Open);
        fileMenu.add(Exit);
        fileMenuBar.add(fileMenu);
        thePanel.add(fileMenuBar, BorderLayout.NORTH);
        //add the main panel to the frame
        this.add(thePanel);
    }

}
