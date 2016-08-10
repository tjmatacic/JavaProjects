package tyler_test;

import java.io.IOException;
import java.io.*;
import basic.*;

public class equalDivision {
	public static void main(String[] args){
		long start=System.currentTimeMillis();
		String data_dir="/home/tjmatacic/Desktop/column-store-tbat-2016/";
		/*String bat_file_name="bat.txt";*/
		String tbat_file_name="10mb_tyler_test_tbat.txt";
		
		int num_lines;
		int num_lines_1m=335000;
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

/*
//found code on Stack Overflow trying to implement

class FileSplit {
    public static void splitFile(File f) throws IOException {
        int partCounter = 0;//I like to name parts from 001, 002, 003, ...
                            //you can change it to 0 if you want 000, 001, ...

        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();

            int tmp = 0;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f.getParent(), name + "."
                        + String.format("%03d", partCounter++));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);//tmp is chunk size
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        splitFile(new File("10mb_tyler_test_tbat.txt"));
    }
}
*/