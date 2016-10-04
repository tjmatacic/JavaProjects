package tyler_test;

import java.io.*;



import basic.DataCreator;
import basic.btree.*;

import java.util.Scanner;

public class AppendixSplitSearch 
{
	public static void main(String[] args) throws Exception
	{
		
		
		long start=System.currentTimeMillis();
		String data_dir="/home/tjmatacic/Desktop/column-store-tbat-2016/";
		String tbat_file_name="small_tyler_tbat.txt";
		
		int num_lines;
		int num_lines_1m= 10000; //47600000;//make 1.5GB file, 3350000 100mb file, 335000 10mb file
		num_lines=num_lines_1m;

		
		if(!(args.length==0 || args[0].isEmpty()))
		{
			num_lines=Integer.parseInt(args[0].trim());
		}
		
		try
		{
		DataCreator.prepareTBAT(num_lines, data_dir+tbat_file_name);
		System.out.println("TBAT of "+num_lines+" lines Created in "+data_dir);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		long end=System.currentTimeMillis();
		double elapsedTime=(end-start)/1000.0;
		System.out.println("Elapsed Time:"+elapsedTime+"s");
		
		RandomAccessFile raf = new RandomAccessFile ("/home/tjmatacic/Desktop/column-store-tbat-2016/small_tyler_tbat.txt", "r");
		long numSplits = 3; //from user input, extract it from args
	    long sourceSize = raf.length();
	    long bytesPerSplit = sourceSize/numSplits ;
	    long remainingBytes = sourceSize % numSplits;

	    int maxReadBufferSize = 8 * 1024; //8KB
	    
	    for (int j =1; j<= numSplits; j++)
	    {
	    	BufferedOutputStream bw = new BufferedOutputStream (new FileOutputStream("split." +j));
	    	
	    	if (bytesPerSplit > maxReadBufferSize)
	    	{
	    		long numReads = bytesPerSplit/maxReadBufferSize;
	    		long numRemainingRead = bytesPerSplit % maxReadBufferSize;
	    		
	    		for (int i =0; i< numReads; i++)
	    		{
	    			readWrite (raf, bw, maxReadBufferSize);
	    		}
	    		
	    		if (numRemainingRead >0)
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
	    	if (remainingBytes > 0)
		    {
		    	BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream("split."+(numSplits+1)));
		        readWrite(raf, bw, remainingBytes);
		        bw.close();
		    }
		    raf.close();
		}
	
	
	/*
	public static OBTree loadAppendixIntoOBTree(String update_file_name) throws IOException{
		OBTree<Integer, Integer> appendixBTree = new OBTree<Integer, Integer>();
		int OFF=1;
		String a;
		int b;
		int valueOfA;
		String timestamp;
		
		Scanner reads = new Scanner(new File("/home/tjmatacic/Desktop/column-store-tbat-2016/small_tyler_tbat.txt"));
		while (reads.hasNext()) {
			timestamp = reads.next();
			a = reads.next(); // read OID
			b = reads.nextInt(); // read VALUE
			a = a.substring(0, a.length() - 1); // removing the comma that was auto-generated
			valueOfA = Integer.parseInt(a); // placing that number into a variable
			if (appendixBTree.get(valueOfA) != null) {
				appendixBTree.findReplace(valueOfA, OFF);
			} else {
				appendixBTree.put(valueOfA, OFF);
			}// end of if-else
			OFF++;
		}
		reads.close();
		return appendixBTree;
	}
	    	
	*/
	
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



