package tyler_test;

import java.io.IOException;
import java.io.*;
import basic.*;

public class equalDivision {
	public static void main(String[] args){
		long start=System.currentTimeMillis();
		String data_dir="/home/tjmatacic/Desktop/column-store-tbat-2016/";
		/*String bat_file_name="bat.txt";*/
		String tbat_file_name="100mb_tyler_tbat.txt";
		
		int num_lines;
		int num_lines_1m=6350000;
		// int num_lines1m=3350000 this to male a 10mb file
//		num_lines=num_lines_1m;
//		num_lines=10;
//		num_lines=1000;
		num_lines=num_lines_1m;
		
		if(!(args.length==0 || args[0].isEmpty())){
			num_lines=Integer.parseInt(args[0].trim());
		}
		
		try{
		DataCreator.prepareData(num_lines, data_dir+tbat_file_name);
		System.out.println("TBAT of "+num_lines+" lines Created in "+data_dir);
		}catch(Exception e){
			e.printStackTrace();
		}
		long end=System.currentTimeMillis();
		double elapsedTime=(end-start)/1000.0;
		System.out.println("Elapsed Time:"+elapsedTime+"s");
	}
}
