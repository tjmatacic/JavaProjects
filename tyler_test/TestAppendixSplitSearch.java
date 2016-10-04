package tyler_test;
import java.io.*;
import java.util.ArrayList;

public class TestAppendixSplitSearch 
{
	
	public static void creaetTBATAppendix2(String update_file_name, String appendix_file_prefix, 
			int appendix_num) throws IOException{
		if(appendix_num==0){
			throw new IOException("appendix_num is zero!");
		}
		
		BufferedReader update_file_in =new BufferedReader(new FileReader(update_file_name));	
		ArrayList<String> update_lines=new ArrayList<String>();//buffer of update file
		
		
		//read update file to buffer
		String line="";
		ArrayList<String> split_buffer=new ArrayList<String>();//buffer of to split the update file buffer
		int appendix_file_index=1;
		int split_buffer_count=0;
		String timestampstr="";
		long current_time_mills=System.currentTimeMillis();
		
		while((line = update_file_in.readLine()) != null){
			split_buffer.add(line);
			if(++split_buffer_count % appendix_num == 0){
				timestampstr=String.format("%d", current_time_mills++);
				timestampstr=timestampstr.substring(timestampstr.length()-8,timestampstr.length());
				saveStringBufferToFile(appendix_file_prefix+"_"+(appendix_file_index++)+".txt",split_buffer,timestampstr);
				split_buffer.clear();
			}
		}
		
		//dump the rest of update file
		if(!split_buffer.isEmpty()){
			timestampstr=String.format("%d", current_time_mills++);
			timestampstr=timestampstr.substring(timestampstr.length()-8,timestampstr.length());
			saveStringBufferToFile(appendix_file_prefix+"_"+(appendix_file_index++)+".txt",split_buffer,timestampstr);
		}
		update_file_in.close();
	}
	
	public static void saveStringBufferToFile(String output_file_name, ArrayList<String> buffer, String timestampstr) throws IOException{
		PrintWriter output_file=new PrintWriter(new BufferedWriter(new FileWriter(output_file_name)));
		for(String line:buffer){
			output_file.println(timestampstr+","+line);
		}
		output_file.close();
	}
}
