package tyler_test;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class largeFileSplit 
{
	public static void main(String[] args) throws Exception
    {
		
		String data =  "/home/tjmatacic/Desktop/column-store-tbat-2016/10mb_tyler_tbat.txt";
        RandomAccessFile raf = new RandomAccessFile("/home/tjmatacic/Desktop/column-store-tbat-2016/10mb_tyler_tbat.txt", "rw");
        int fileSize = (int)raf.length(); //original file size
        int count = 3350;
        FileChannel fc = raf.getChannel();
        
        //MemoryMappedBuffer has a fixed max-size, but the file it's mapped to is elastic
        //mapping file into memory
        MappedByteBuffer mBuff = fc.map(FileChannel.MapMode.READ_WRITE, 0, count);
        
        //seek end of file for appending
        mBuff.position(fileSize);
        
        //appending to memory mapped file
        mBuff.put(data.getBytes());
        int newFileSize = mBuff.position();
        
        unmap(fc,mBuff);
        
        if (newFileSize < count)
        {
        	fc.truncate(newFileSize);        	
        }
        raf.close();
        fc.close();
    }

	private static void unmap(FileChannel fc, MappedByteBuffer mBuff) {
		Class<?> fcClass = fc.getClass();
		try {
			java.lang.reflect.Method unmapMethod = fcClass.getDeclaredMethod("unmap", 
					new Class[] {java.nio.MappedByteBuffer.class});
			
			unmapMethod.setAccessible(true);
			unmapMethod.invoke(null,  new Object[] {mBuff});
			System.out.println("unmap successfully");
		} catch (NoSuchMethodException e){e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (Exception e) {e.printStackTrace();
		}
			
		}
		
	}

       /* 
        long numSplits = 10; //how many file splits we want
        long sourceSize = raf.length();
        long bytesPerSplit = sourceSize/numSplits ;
        long remainingBytes = sourceSize % numSplits;
        
        int maxReadBufferSize = 8 * 1024; //8KB
        for(int i=1; i <= numSplits; i++) 
        {
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+i));
            
                       
            if(bytesPerSplit > maxReadBufferSize) 
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
        */