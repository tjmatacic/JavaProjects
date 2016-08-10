package tyler_test;
import java.io.*;

public class fileReaderTyler 
{

  public static void main(String[] args) 
  {   
	  
	  LineNumberReader lr = null;
	  PrintWriter pw = null;
	  try
	  {
	      lr = new LineNumberReader(new FileReader("/home/tjmatacic/Desktop/column-store-tbat-2016/10mb_tyler_test_tbat.txt"));
	      pw = new PrintWriter(new FileWriter(new File("/home/tjmatacic/Desktop/File_Write_Test.txt"), true));
	   
	      String line = lr.readLine();
	       
	      while (line != null && lr.getLineNumber() <= 33500 ) 
	      { 
	          pw.println(line);
	          line = lr.readLine();
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
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  

	/*  
	StringBuffer buffer = new StringBuffer();
    BufferedWriter bw = null;
    
    try {
      
      BufferedReader fileReader = new BufferedReader(new FileReader("/home/tjmatacic/Desktop/column-store-tbat-2016/10mb_tyler_test_tbat.txt"));
      for (int i = 0; i < 33500; i++) {
        String line = fileReader.readLine();
        buffer.append(line);
                
    	bw.write("fileReader");
    	bw.flush();
    
      }

     // System.out.println(buffer); 
    }
    catch (IOException e) {
      
    }

  }

}
*/