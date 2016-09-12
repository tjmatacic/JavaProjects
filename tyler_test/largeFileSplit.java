package tyler_test;
import java.io.*;

public class largeFileSplit 
{
	public static void main(String[] args) throws Exception
    {
        RandomAccessFile raf = new RandomAccessFile("/home/tjmatacic/Desktop/column-store-tbat-2016/10MB_tyler_tbat.txt", "r");
        long numSplits = 10; //how many file splits we want
        long sourceSize = raf.length();
        
        long computerMemory = 1073741824; //<- one GB
        long fileSlice = sourceSize / computerMemory;
        
        long bytesPerSplit = sourceSize/numSplits;
        long remainingBytes = fileSlice % numSplits;
         
        int maxReadBufferSize = 8 * 1024; //8KB
        
        
      
        for(int i=1; i <= numSplits; i++) 
        {
        	//Peek to see if we are at end of file
        	long currentPosition = raf.getFilePointer();
        	
        	if(raf.read() == -1) //we have reached the end of file
        	{
        	  break;
        	}
        	raf.seek(currentPosition); //Go back to where we were before we did the read
            
        	BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+i));
            
            if (bytesPerSplit > fileSlice)
            {
            	long numReads = bytesPerSplit / maxReadBufferSize;
            	
                for(int j=0; j<numReads; j++) 
                {
                    readWrite(raf, bw, maxReadBufferSize);
                    
                }
                
            }
            
            if(bytesPerSplit < fileSlice) 
            {
                long numReads = bytesPerSplit/maxReadBufferSize;
                long numRemainingRead = bytesPerSplit % maxReadBufferSize;
                for(int j=0; j<numReads; j++) 
                {
                    readWrite(raf, bw, maxReadBufferSize);
                }
                
                                
                if(numRemainingRead > 0) 
                {
                    readWrite(raf, bw, numRemainingRead);
                }
                
            }
            
              
            else 
            {
                readWrite(raf, bw, bytesPerSplit);
            }
            bw.close();
        }
        
        
        if(remainingBytes > 0) 
        {
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+(numSplits+1)));
            readWrite(raf, bw, remainingBytes);
            bw.close();
        }
            raf.close();
    }
    
    static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) throws IOException 
    {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if(val != -1) 
        {
            bw.write(buf);
        }
    }
}