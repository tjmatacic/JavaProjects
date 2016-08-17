package tyler_test;
import java.io.*;

public class fileReaderTyler
{

  public static void main(String[] args)
  {  
     
      LineNumberReader lr = null;
      PrintWriter pw = null;
      //String line= "";
      try
      {
          lr = new LineNumberReader(new FileReader("/home/tjmatacic/Desktop/column-store-tbat-2016/10mb_tyler_test_tbat.txt"));
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test.txt"), true));
         
          File filename = new File("/home/tjmatacic/Desktop/File_Write_Test2.txt");

          int counter=1;
          String line = lr.readLine();
         
          while (lr.getLineNumber() != 0 )
          {
              filename =new File("/home/tjmatacic/Desktop/File_Write_Test2.txt" + counter + ".sp");
              pw.println(line);
              line= lr.readLine();
          }
          counter++;
         
                         
      }
         
         
         
         
         
         
          /*
          while (line != null && lr.getLineNumber() <= 33500 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
          */
         
          /*
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_2.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 67000 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_3.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 100500 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_4.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 134000 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_5.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 167500 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_6.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 201000 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_7.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 234500 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_8.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 268000 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_9.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 301500 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
         
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test_10.txt"), true));
         
          while (line != null && lr.getLineNumber() <= 335000 ) {
              line = lr.readLine();
              pw.println(line);
             
          }
                
          lr.close();
          lr = null;
          pw.flush();           
          pw.close();
          pw = null;
         
          */
          
          //trying to implement a counter variable
         
     
 
      catch(Exception e)
      {
          System.out.println("Exception caught: "+e.getMessage());
      }
     
      finally
      {    
          close(lr);
          close(pw);
      }
  }

private static void close(PrintWriter pw) {}

private static void close(LineNumberReader lr) {}     

}