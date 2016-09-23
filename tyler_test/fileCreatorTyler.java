package tyler_test;

import java.io.*;
import basic.*;
public class fileCreatorTyler {
	


	
		public static void main(String[] args){
			long start=System.currentTimeMillis();
			String data_dir="/home/tjmatacic/Desktop/column-store-tbat-2016/";
			String bat_file_name="bat.txt";
			String tbat_file_name="small_tyler_tbat.txt";
			
			int num_lines;
			int num_lines_1m= 10000; //47600000;//make 1.5GB file, 3350000 100mb file, 335000 10mb file
			num_lines=num_lines_1m;

			
			if(!(args.length==0 || args[0].isEmpty())){
				num_lines=Integer.parseInt(args[0].trim());
			}
			
			try{
			DataCreator.prepareData(num_lines, data_dir+tbat_file_name, data_dir+bat_file_name);
			System.out.println("TBAT of "+num_lines+" lines Created in "+data_dir);
			}catch(Exception e){
				e.printStackTrace();
			}
			long end=System.currentTimeMillis();
			double elapsedTime=(end-start)/1000.0;
			System.out.println("Elapsed Time:"+elapsedTime+"s");
		}
	}



