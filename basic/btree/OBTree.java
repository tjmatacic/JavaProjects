package basic.btree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import basic.btree.*;

@SuppressWarnings("unchecked")
public class OBTree <Key extends Comparable<Key>, Value> extends BTree <Key, Value>{
	public int total_inserts=0;
	public int bulkLoadUpdateFile(String update_file_name) throws FileNotFoundException{
		Scanner reads = new Scanner(new File(update_file_name));// reader for update file
		Integer offset = 1; // offset starts from 1
		String line; // buffer for reach each line
		String[] line_vector; // tokenized line
		Integer oid; // each oid
		while (reads.hasNext()) {
			line = reads.nextLine();
			line_vector = line.split(",");
			oid = Integer.parseInt(line_vector[1].trim());
			if (findReplace((Key)oid, (Value) offset) != null) {
//				System.out.println("\nKey " + oid + " already exists. Update offset to " + offset + ".");
			} else {
//				System.out.println("A new key: " + oid + " \t\tInserting at offset: " + offset + ".");
				put((Key) oid, (Value) offset);
			}
			offset++;
		}
		reads.close();
		this.total_inserts=offset.intValue()-1;
		return total_inserts;
	}
	

	
	
	
	/**
	 * load update the appendix of an updated file into a new BTree
	 */
	public static OBTree loadAppendixIntoOBTree(String update_file_name) throws IOException{
		OBTree<Integer, Integer> appendixBTree = new OBTree<Integer, Integer>();
		int OFF=1;
		String a;
		int b;
		int valueOfA;
		Scanner reads = new Scanner(new File(update_file_name));
		while (reads.hasNext()) {
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
	
	public static OBTree loadAppendixIntoOBTree2(String update_file_name) throws IOException{//if file read had timestamp use this
		OBTree<Integer, Integer> appendixBTree = new OBTree<Integer, Integer>();
		int OFF=1;
		String timestamp;
		String a;
		int b;
		int valueOfA;
		
		Scanner reads = new Scanner(new File(update_file_name));
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

	
	
	
}
