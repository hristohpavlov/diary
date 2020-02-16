package cm2100.diary;

/**
 * 
 * MainApp.java - created by Hristo Pavlov
 * 
 */

public class MainApp {
        public static void main(String args[]) 
        {
        Runnable userInterface = new Runnable() 
        {
            @Override
            public void run() 
            {
                GUI userInterface = new GUI();
                userInterface.setVisible(true);
            }
        };
        java.awt.EventQueue.invokeLater(userInterface);
    }
}
