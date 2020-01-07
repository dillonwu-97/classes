/*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Dillon Wu*/
/* =======================================================================
   A generic GUI for the JDBC project

   Author: Shun Yan Cheung

   To compile the project: 

   javac JDBC.java

   To run the project: 

   java -cp ".:/home/cs377001/lib/mysql-connector-java.jar" JDBC
   ======================================================================= */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.*;

import java.text.DecimalFormat;

import java.util.*;

public class JDBC  
{
   public static JFrame mainFrame;

   /* ***************************************************************
      The class variables (defined using static) defined in this area
      can be accessed by other "call back" classes

      You can define your SHARED program variables below this line

      SHARED variables are variables that you need to use in
      MULTIPLE methods

      (If you ONLY use a variable inside ONE method, define that
       variable as a LOCAL variable inside THAT method)
      *************************************************************** */

   /* *********************************************************
      TODO 1: Define you SHARED program variables here

         SHARED variables are variables that will can 
         be used by methods in *different* classes
      ********************************************************* */
   public static Statement state;

   public static Connection con;

   public static ResultSet rset;

   public static ResultSetMetaData meta;

   public static int tuple; // count the number of tuples

   public static double answer; // gets the answer to the max, min, etc. queries

   public static String answer2; // for Strings instead of doubles


   /* ********************************************************************
      End area where you define your SHARED program variables
      ******************************************************************* */

   /* vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
      This area contain SHARED object variables for the GUI application

      Do NOT make any changes to the variable sections below
      You can use these GUI variables to code your GUI application:

   Input:  a JTextArea object
      Input.getText() will return the SQL query that you
      need to execute
   Output:  a JTextArea object
      (1) Use Output.setText("") to clear the text area
      (2) Then use Output.append( ... ) to print text
          Use Output.append("\n") to advance to next line

   DBName: a JTextField object. 
      DBName.getText() will return the database name
   Select: a JButton object.
      Activate this object to process "Select database" function
   Execute: a JButton object.
      Activate this object to process "Execute query" function

   Column: a JTextField object.
      Column.getText() will return the column number
   Max: a JButton object.
      Activate this object to process "Max" function
   Min: a JButton object.
      Activate this object to process "Min" function
   Avg: a JButton object.
      Activate this object to process "Avg" function
   Median: a JButton object.
      Activate this object to process "Median" function
      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ */

   /* ==========================================================
      The query input area
      ========================================================== */
   public static JTextArea Input = new JTextArea();   // Query input !!!
                                                      // Get the query text here

   /* ==========================================================
      The query output area
      ========================================================== */
   public static JTextArea Output = new JTextArea();  // Query output !!
                                                      // Print tuples here

   /* ==========================================================
      The TOP-RIGHT panel things
      ========================================================== */
   // Database label and name
   public static JLabel DBLabel = new JLabel("Database: ");
   public static JTextField DBName = new JTextField();     // Database name !!

   // The database selection button
   public static JButton Select = new JButton("Select");   // Select button !!

   // The query execution button
   public static JButton Execute = new JButton("Execute"); // Execute button !!


   /* ==========================================================
      The BOTTON-RIGHT panel things
      ========================================================== */
   // The column selection field
   public static JLabel ColumnLabel = new JLabel("Column: ");
   public static JTextField Column = new JTextField();       // Column number !!

   // The MAX output field  and button
   public static JTextField MaxText = new JTextField();
   public static JButton Max = new JButton("Maximum");       // Max button !!

   // The MIN output field  and button
   public static JTextField MinText = new JTextField();
   public static JButton Min = new JButton("Minimum");       // Min button !!

   // The Average output field  and button
   public static JTextField AvgText = new JTextField();
   public static JButton Avg = new JButton("Average");       // Avg button !!

   // The Median output field  and button
   public static JTextField MedianText = new JTextField();
   public static JButton Median = new JButton("Median");     // Median button !!



   /* ********************************************************************
      The main program
      ******************************************************************** */
   public static void main( String[] args )
   {
      /* ===============================================
    Make the window
         =============================================== */
      MakeGUIWindow();


      /* =====================================================
    Make the Select button active:
         ===================================================== */
      Select.addActionListener(new SelectListener() );
      // Make Select button execute the actionPerform() method
      // inside the class SelectListener (see below)

      /* **********************************************************
    TODO 2: make the OTHER buttons active 
                 (Execute, Max, Min, Avg, Median)

       Make the buttons perform the functions given in HW
         ********************************************************** */

       // I did this in class....
       Execute.addActionListener(new myExecuteClass() );
       Max.addActionListener(new maxClass() );
       Min.addActionListener(new minClass());
       Avg.addActionListener(new avgClass());
       Median.addActionListener(new medClass());





   }
// calculating median
   static class medClass implements ActionListener {
      public void actionPerformed (ActionEvent e) {
         try{
            rset.beforeFirst(); // reset the result set

            String num = Column.getText(); // get the input
            int number = Integer.parseInt(num); // integer value of num
   if (number > meta.getColumnCount()) {
               MedianText.setText("col # error");
   }
            int type = meta.getColumnType(number); // return column type;

            double[] result = new double[tuple]; // get the number of values
            
            int track = 0; // keep track of position of the array
            double store;

            // this is for alphabetical
            String[] result2 = new String[tuple];
            String store2;

            int counter = 0;
         
            if (type == java.sql.Types.DECIMAL || type == java.sql.Types.INTEGER ) {
               // find the max value
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     if (counter == tuple) {
                        break outer;
                     }
                     rset.next();
                  }
                  counter++; // keep track of whether or not we are at the limit
                  store = rset.getDouble(number);
                  result[track] = store;
                  track++;
               }
               // get the max value
               Arrays.sort(result, 0, track);
               if (track % 2 == 1) {
                  answer = result[track/2];        
               } else {
                  answer = result[track/2 - 1];
               }
               MedianText.setText(   Double.toString(answer)   );
            } else {
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     rset.next();
                     if (counter == tuple) {
                        break outer;
                     }
                  }
                  counter++;
                  store2 = rset.getString(number);
                  result2[track] = store2;
                  track++;
               }
               // get the max value
               Arrays.sort(result2, 0, track);
               if (track % 2 == 1) {
                  answer2 = result2[track/2];        
               } else {
                  answer2 = result2[track/2 - 1];
               }         
              MedianText.setText(   answer2   );
               // find the alphabetically last value

            }
         } catch (Exception f) {
               String errMsg = f.getMessage();
               Output.append(errMsg);
         }
      }
   }


// calculating average
   static class avgClass implements ActionListener {
      public void actionPerformed (ActionEvent e) {
         try {
            rset.beforeFirst();
            String num = Column.getText(); // get the input
       int number = Integer.parseInt(num); // integer value of num
    if (number > meta.getColumnCount()) {

               AvgText.setText("col # error");
            }
            int type = meta.getColumnType(number); // return column type;

            double[] result = new double[tuple]; // get the number of values
            
            int track = 0; // keep track of position of the array
            double store;
      int counter = 0;
            if (type == java.sql.Types.DECIMAL || type == java.sql.Types.INTEGER ) {
               // find the max value
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     rset.next();
                     if (counter == tuple) {
                        break outer;
                     }
                  }
                  counter++;
                  store = rset.getDouble(number);
                  result[track] = store;
                  track++;
               }
               // get the max value
               answer = avg(result, track);  
               DecimalFormat form = new DecimalFormat("0.00");    
               String finalAnswer = form.format(answer).toString();
               AvgText.setText(   finalAnswer  );
            } else {
               // there is an error since you cannot calculate the avg of alphabet
               AvgText.setText("Not num");
            }
         } catch (Exception o) {
            String errMsg = o.getMessage();
            Output.append(errMsg);
         }
      }
   }

   public static double avg(double[] array, int track) {
      double result = 0;
      for (int i = 0; i < track; i++) {
         result = result + array[i];
      }
      result = result / track;
      return result;
   }

   static class minClass implements ActionListener {
      public void actionPerformed(ActionEvent e) {

        // takes the input from column       
   try{
            rset.beforeFirst(); // reset the result set

            String num = Column.getText(); // get the input
            int number = Integer.parseInt(num); // integer value of num
       if (number > meta.getColumnCount()) {
               MinText.setText("col # error");
            }
            int type = meta.getColumnType(number); // return column type;

            double[] result = new double[tuple]; // get the number of values
            
            int track = 0; // keep track of position of the array
            double store;

            // this is for alphabetical
            String[] result2 = new String[tuple];
            String store2;

            int counter = 0;
            // if the types are numerical
            if (type == java.sql.Types.DECIMAL || type == java.sql.Types.INTEGER ) {
               // find the max value
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     if (counter == tuple) {
                        break outer;
                     }
                     rset.next();
                  }
                  counter++; // keep track of whether or not we are at the limit
                  store = rset.getDouble(number);
                  result[track] = store;
                  track++;
               }
               // get the max value
               answer = min(result, track);          
              MinText.setText(   Double.toString(answer)   );
            } else {
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     rset.next();
                     if (counter == tuple) {
                        break outer;
                     }
                  }
                  counter++;
                  store2 = rset.getString(number);
                  result2[track] = store2;
                  track++;
               }
               // get the max value
               answer2 = min(result2, track);          
              MinText.setText(   answer2   );
               // find the alphabetically last value

            }
         } catch (Exception z) {
               String errMsg = z.getMessage();
               Output.append(errMsg);
         }
      }
   }

   public static String min(String[] array, int track) {
      String min = array[0];
      for (int i = 1; i < track; i++) {
         if (array[i].compareTo(min) <0) {
            min = array[i];
         }
      }
      return min;
   }

   public static double min(double[] array, int track) {
      double min = array[0];
      for (int i = 1; i < track; i++) {
         if (min > array[i]) {
            min = array[i];
         }
      }
      return min;
   }


   static class maxClass implements ActionListener {
      public void actionPerformed(ActionEvent e) {

        // takes the input from column       
   try{
            rset.beforeFirst(); // reset the result set

            String num = Column.getText(); // get the input
            int number = Integer.parseInt(num); // integer value of num
            if (number > meta.getColumnCount()) {
               MaxText.setText("col # error");
            }
               int type = meta.getColumnType(number); // return column type;

            double[] result = new double[tuple]; // get the number of values
            
            int track = 0; // keep track of position of the array
            double store;

            // this is for alphabetical
            String[] result2 = new String[tuple];
            String store2;
            int counter = 0;
            // if the types are numerical
            if (type == java.sql.Types.DECIMAL || type == java.sql.Types.INTEGER ) {
               // find the max value
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     rset.next();
                     if (counter == tuple) {
                        break outer;
                     }
                  }
                  counter++;
                  store = rset.getDouble(number);
                  result[track] = store;
                  track++;
               }
               // get the max value
               answer = max(result, track);          
              MaxText.setText(   Double.toString(answer)   );
            } else {
               outer:
               while (rset.next()) {
                  while (rset.getString(number) == null) {
                     counter++;
                     rset.next();
                     if (counter == tuple) {
                        break outer;
                     }
                  }
                  counter++;
                  store2 = rset.getString(number);
                  result2[track] = store2;
                  track++;
               }
               // get the max value
               answer2 = max(result2, track);          
              MaxText.setText(   answer2   );
               // find the alphabetically last value

            }
         } catch (Exception z) {
               String errMsg = z.getMessage();
               Output.append(errMsg);
         }
      }
   }

   public static String max(String[] array, int track) {
      String max = array[0];
      for (int i = 1; i < track; i++) {
         if (array[i].compareTo(max) >0) {
            max = array[i];
         }
      }
      return max;
   }

   public static double max(double[] array, int track) {
      double max = array[0];
      for (int i = 1; i < track; i++) {
         if (max < array[i]) {
            max = array[i];
         }
      }
      return max;
   }

   // I did this in class....
   static class myExecuteClass implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         Boolean done = false;
         Output.setText("");        
         while (!done) {
            tuple = 0;
            String s;
            s = Input.getText( );
            if ( s.equalsIgnoreCase("exit") ) {
               done = true;
               continue;
            }
            try {
               rset = state.executeQuery(s); // Executes query
               meta = rset.getMetaData();  // Get meta data
               int NCols = meta.getColumnCount();  // get all the number of columns
               int[] length = new int[NCols]; // keep track of the length of each column
               String[] colType = new String[NCols]; // keep track of the type of each column
               for ( int i = 1; i <= NCols; i++ ) {
                  String name;                     // get name of each column
                  name = meta.getColumnName(i);
                  int type = meta.getColumnType(i);
                  if (meta.getColumnType(i) == java.sql.Types.DECIMAL ||      
                      meta.getColumnType(i) == java.sql.Types.INTEGER ) {
                     // Needs to be of length 10 
                     colType[i-1] = "num"; // if numeric
                     if (name.length() <= 10) {
                        Output.append(name);
                        for (int j = 0; j < (10 - name.length()); j++) {
                           Output.append(" ");
                        }
                     }
                     if (name.length() > 10){
                        Output.append(name.substring(0,10));
                     }
                     length[i-1] = 10;

                  } else {       // else it is a char, so should only be length 6
                     colType[i-1] = "str"; // if string
                     int current = Math.max(6, meta.getColumnDisplaySize(i));
                     if (name.length() <= current) {
                        Output.append(name);
                        for (int j = 0; j < (current - name.length()); j++) {
                           Output.append(" ");
                        }
                     }
                     if (name.length() > current){
                        Output.append(name.substring(0,current));
                     }
                     length [i-1] = current;
                  }
                  Output.append(" "); // to add a space between the end of each column 
               }
               Output.append("\n");
               
               // prints out the separating lines
               for ( int i = 1; i <= NCols; i++ ) {
                  for ( int j = 0; j < length[i-1]; j++) {
                     Output.append("=");   // Print a dividing line + NCols extra dividers
                  }
                  if (i < NCols) {
                     Output.append("=");
                  }
               }
               Output.append("\n");
               
               while ( rset.next() ) {
                  // to count the number of tuples
                  tuple++;
                  for(int i = 1; i <= NCols; i++) {
                     String nextItem;
                     nextItem = rset.getString(i);
                     if (colType[i-1] == "num") {
                        // right aligned
                        
                        if (nextItem == null) {
                           for (int j = 4; j < length[i-1]; j++){ // add an extra 1 for a space at the end
                              Output.append(" "); 
                           }       
                           Output.append("NULL");                                   
                        } else {
                           
                           for (int j = nextItem.length(); j < length[i-1]; j++){ // add an extra 1 for a space at the end
                              Output.append(" ");
                           }
                           Output.append(nextItem);
                        }
                        Output.append(" ");
                     } else if (colType[i-1] == "str") {
                        if (nextItem == null) {
                           Output.append("NULL");
                           for (int j = 4; j < length[i-1] + 1; j++) {
                              Output.append(" ");
                           }
                        } else {
                           Output.append(nextItem);
                           for (int j = nextItem.length(); j < length[i-1] + 1; j++){ // add an extra 1 for a space at the end
                              Output.append(" ");
                           }
                        }
                     }
                  }
                  Output.append("\n");
               }
               //rset.close();
               done = true;
            } catch (Exception x) {
               String errMsg = x.getMessage();
               Output.append(errMsg);
               done = true; // Print the error message
            }
         }
         try {    
            //state.close();
            //con.close();
         } catch (Exception y) {
            String errMsg = y.getMessage();
            Output.append(errMsg);
         }

      }
   }


   /* ===============================================================
      ***** Sample "call back" class

      Listener class for the Select button
      =============================================================== */
   static class SelectListener implements ActionListener            
   {  
      /* =============================================================
         The "actionPerformed( )" method will be called
    when a button is pressed

    You must ASSOCIATE a button to this class using this call:

       ButtonObject.addActionListener( new SelectListener( ) )
         ============================================================= */
      public void actionPerformed(ActionEvent e)
      {  
    /* ===========================================================
       You must replace these statements with statements that
       perform the "SELECT button" function:

      1. make a connection to the database server
      2. allocate statement object to prepare for 
                   query execution
       =========================================================== */

         // =============================================================
         // Dummy statements to show that the activation was successful
         // =============================================================
         //System.out.println("The method actionPerformed"); 
         //System.out.println("in the class SelectListener was called\n");
   
   String url = "jdbc:mysql://holland.mathcs.emory.edu:3306/";
   String dbName = DBName.getText();
   String user = "cs377";
   String pass = "abc123";
   Output.setText("");
   try {
      Class.forName("com.mysql.jdbc.Driver");
   } catch (Exception f) {
      //System.out.println("Cannot load the JDBC driver.");
      Output.append("Cannot load the JDBC driver.");
            return;
      }

   con = null;
   state = null;  
   try { 
      con = DriverManager.getConnection(url+dbName,user,pass);
   if (DBName.getText().trim().isEmpty() == true) {
   Output.append("No database was selected");
   }
      state = con.createStatement();
   } catch (Exception f) {
         Output.append("Cannot open database: " + dbName);
      
      //System.out.println("Cannot open database: " + dbName);
   }

      }
   }

   /* ===============================================================
      TODO 3:
      ***** Write OTHER "call back" classes here for other buttons
            (Execute, Max, Min, Avg, Median)
      =============================================================== */
















  /* **********************************************************
     This section of the program makes the GUI

     DO NOT make any changes to the code below !!!
     ********************************************************** */

   /* ===============================================================
      Make GUI window
      =============================================================== */
   public static void MakeGUIWindow()
   {
      Font ss_font = new Font("SansSarif",Font.BOLD,16) ;
      Font ms_font = new Font("Monospaced",Font.BOLD,16) ;

      JPanel P1 = new JPanel();   // Top panel
      JPanel P2 = new JPanel();

      P1.setLayout( new BorderLayout() );
      P2.setLayout( new BorderLayout() );

      /* =============================================
         Make top panel
         ============================================= */
      JScrollPane d1 = new JScrollPane(Input, 
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      Input.setFont( ss_font );

      JPanel s1 = new JPanel(); // Side panel
      s1.setLayout( new GridLayout( 8,1 ) );
      s1.add( DBLabel );
      s1.add( DBName );
      DBName.setEditable(true);
      DBName.setFont( ss_font );
      s1.add( Select );
      s1.add( Execute );
      Execute.setPreferredSize(new Dimension(140, 30)) ;

      P1.add(d1, "Center");
      P1.add(s1, "East");

      /* =============================================
         Make bottom panel
         ============================================= */
      JScrollPane d2 = new JScrollPane(Output, 
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

      Output.setFont( ms_font );
      Output.setEditable(false);

      JPanel s3 = new JPanel(); // Put ColumnLabel and Column on 1 row
      s3.add(ColumnLabel);
      s3.add(Column);
      Column.setFont( ss_font );

      Column.setPreferredSize(new Dimension(40, 30)) ;

      JPanel s2 = new JPanel(); // Side panel
      s2.setLayout( new GridLayout( 10,1 ) );
      s2.add( s3 );

      MaxText.setPreferredSize(new Dimension(140, 30)) ;
      s2.add( MaxText );
      MaxText.setFont( ss_font );
      MaxText.setEditable(false);
      s2.add( Max );

      s2.add( MinText );
      MinText.setFont( ss_font );
      MinText.setEditable(false);
      s2.add( Min );

      s2.add( AvgText );
      AvgText.setFont( ss_font );
      AvgText.setEditable(false);
      s2.add( Avg );

      s2.add( MedianText );
      MedianText.setFont( ss_font );
      MedianText.setEditable(false);
      s2.add( Median );


      P2.add(d2, "Center");
      P2.add(s2, "East");

      mainFrame = new JFrame("CS377 JDBC project");
      mainFrame.getContentPane().setLayout( new GridLayout(2,1) );
      mainFrame.getContentPane().add( P1 );
      mainFrame.getContentPane().add( P2 );
      mainFrame.setSize(900, 700);

      /* =================================================
         Exit application if user press close on window

         This will clean up the network connection....
         ================================================= */
      // mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      mainFrame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent we) {
             System.out.println("\nGood-bye.Thanks for using CS377-sql...\n");
             System.exit(0);
          }
      });


      mainFrame.setVisible(true);
   }
}