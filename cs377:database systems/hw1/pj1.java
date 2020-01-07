//THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS
// Dillon Wu

import java.io.*;

class DataDescription
{
   String fieldName;
   String fieldType;
   int    fieldSize;
}

public class pj1
{
   static int MAXNFIELDS = 10;      // Max. # fields in 1 record

   static int n_fields;             // Actual number of fields

   /* ------------------------------------------------------------
      (1) Variables used to store the DESCRIPTION of the data
      ------------------------------------------------------------ */
   static DataDescription[] dataDes = new DataDescription[MAXNFIELDS];

   // This is a CONSTRUCTOR method for static variables
   static
   {
      // Need to create objects for the dataDes[]
      for ( int i = 0; i < MAXNFIELDS; i++ )
      {
	 dataDes[i] = new DataDescription();
      }
   }

   /* -------------------------------------------------------
      (2) Variables used to store the ACTUAL data
      ------------------------------------------------------- */
   static String[] c_buf=new String[MAXNFIELDS]; // Used to store String fields
   static int[]    i_buf=new int[MAXNFIELDS];    // Used to store int fields

   // TODO: add variable(s) to support double data

   // Newly added code start:
   static double[] f_buf = new double[MAXNFIELDS]; // used to store double fields 
   // newly added code end




   public static void main(String[] args) throws IOException
   {

      /* ===========================================================
	 We must first find out the STRUCTURE of the data file
	 This information is stored in the data DESCRIPTION file
	 =========================================================== */

      DataFile descrFile = new DataFile("db-description");
			 // 1. Open the data description file

      /* -------------------------------------------------------
	 Read in the data description and store them in the
	 DataDes[] array (define in (1))
         ------------------------------------------------------- */

      n_fields = 0;                 // Count the actual number of fields in data
      while ( true )
      {
         try
         {
            dataDes[n_fields].fieldName = descrFile.ReadString(24);
            dataDes[n_fields].fieldType = descrFile.ReadString(4);
            dataDes[n_fields].fieldSize = descrFile.ReadInt();

            System.out.println("Field: " + dataDes[n_fields].fieldName
                + ", type: " + dataDes[n_fields].fieldType
                + ", size: " + dataDes[n_fields].fieldSize);

	    n_fields++;
         }
         catch ( IOException e )
         {
            System.out.println("\nFinish reading data description file....\n");
            break;   // Read error: no more data !!!
         }
      }


      DataFile dataFile =  new DataFile("db-data");   // First open data file

      System.out.println( "The data file contains these records:\n");

      // TODO:  Write a loop to print out the records
      //        You can use the PrintRecord() method given below
      //        but you must add support for double in that method
      // 

      // start of new code:
      


      while (true) {
         try {   
            for (int j = 0; j < n_fields; j++) {
               if (dataDes[j].fieldType.equals("I")) {
                  //System.out.println("test:" + dataFile.ReadInt());
                  i_buf[j] = dataFile.ReadInt();
               } else if (dataDes[j].fieldType.equals("C")) {
                  c_buf[j] = dataFile.ReadString(dataDes[j].fieldSize);
               } else {
                  f_buf[j] = dataFile.ReadDouble();
               }
            }
            PrintRecord();
         } catch (IOException e) {
            break;
         }
      }
      // end of new code


      // 
      // The correct output is:
      //
      //               123 Disk Drive 109.99 14
      //               444 CPU 899.99 5
      //               333 Printer 219.97 2

      // sum of ID: 900
      // sum of Item: 0
      // sum of Price: 1229.95
      // sum of Inventory: 21


      System.out.print("\nFind max value in the field " + args[0] + "\n");

      dataFile.rewind();      // Rewind data file


      // TODO:  Define additional variables and write additional code 
      //        to find the maximum value in the specified fieldName 
      //        (the fieldName is given as args[0])
      //
      // Use the >= operator to compare 2 integer or 2 double values
      // Use the compareTo() method in the String class to compare 
      // 2 Strings
      // 


      // TODO:  Write additional code to print out the max value found

      // start of new code:

      //System.out.println("ID".equals(args[0]));
      // Initializing the three return types
      int iTrack = 0; // detects if there was ever an equivalency on the name comparisons
      int fTrack = 0; 
      int cTrack = 0; 
      // stores the max values of each type
      int iMax = 0;
      String cMax = "";
      double fMax = 0;
      while (true) {
         try {
            // this is to put the values in the array x_buf
            for (int j = 0; j < n_fields; j++) {
               if (dataDes[j].fieldType.equals("I")) {
                  i_buf[j] = dataFile.ReadInt();
               } else if (dataDes[j].fieldType.equals("C")) {
                  c_buf[j] = dataFile.ReadString(dataDes[j].fieldSize);
               } else {
                  f_buf[j] = dataFile.ReadDouble();
               }
            }

            for (int i = 0; i < n_fields; i++) {
               // if the entry field name is the same as the field name in dataDes
               if (dataDes[i].fieldName.equals(args[0])) {
                  // compares the type with the type we are looking for 
                  if (dataDes[i].fieldType.equals("I")) {
                     iTrack++;
                     if (iMax == 0) {
                        iMax = i_buf[i];
                     } else if (i_buf[i] > iMax) {
                        iMax = i_buf[i];
                     }
                  } else if (dataDes[i].fieldType.equals("F")) {
                     fTrack++;
                     if (fMax == 0) {
                        fMax = f_buf[i];
                     } else if (f_buf[i] > fMax) {
                        fMax = f_buf[i];
                     }
                  } else if (dataDes[i].fieldType.equals("C")) {
                     cTrack++;
                     if (cMax.equals("")) {
                        cMax = c_buf[i];
                     } else if (c_buf[i].compareTo(cMax) > 0) {
                        cMax = c_buf[i];
                     }
                  }
               }
            }
         } catch (IOException e) {
            // three different print statements for each type of max
            if (iTrack > 0) {
               System.out.println("Max = " + iMax);
               break;
            } else if (fTrack > 0) {
               System.out.println("Max = " + fMax);
               break;
            } else if (cTrack > 0) {
               System.out.println("Max = " + cMax);
               break;
            } else {
               // if the field name doesn't exist
               System.out.println("--- Error: field name not found.");
               break;
            }
         }

      }
      
      // end of new code
   }




   /* ===========================================================
      PrintRecord( ): print the record store in the arrays

      Right now, PrintRecord() only support integer and String
      data types
      =========================================================== */
   public static void PrintRecord( )
   {
      // TODO: add support to print double data
      // done; new lines added below

      for ( int i = 0; i < n_fields; i++ )
      {
         if ( dataDes[i].fieldType.equals("I") )
         {
            /* --------------------------------------------------------
               Field i is an integer, use i_buf[i] to store the value
               -------------------------------------------------------- */
            System.out.print( i_buf[i] + " ");
         }
         else if (dataDes[i].fieldType.equals("C"))
         {
            /* --------------------------------------------------------
               Field i is an String, use c_buf[i] to store the value
               -------------------------------------------------------- */
            System.out.print( c_buf[i] + " ");
         } else {
            // field i is a double, use f_buf[i] to store the value 
            System.out.print( f_buf[i] + " ");
         }
      }

      System.out.println( );    // Print newline to separate records
   }

}

