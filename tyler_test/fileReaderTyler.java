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
          lr = new LineNumberReader(new FileReader("/home/tjmatacic/Desktop/column-store-tbat-2016/1000line_test_tbat.txt"));
          pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test.txt"), true));
         
          //File filename = new File("/home/tjmatacic/Desktop/File_Write_Test2.txt");

          int counter=0;
          int fileLength = 0;
          String line = lr.readLine();
          counter ++;
          
          while (line != null && lr.getLineNumber() != 0 )
          {
              pw.println(line);
              line= lr.readLine();
          }
                                            
      }
         
  
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