package basic.btree;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 * B-Tree implementation largely based on the implementation made in Algorithms
 * book by Robert Sedgewick. Original code can be found in <a
 * href="http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/BTree.java.html"> the book web
 * site<a/>.
 * 
 * @author cgavidia
 * 
 * @param <Key>
 *            Type of the Search Key
 * @param <Value>
 *            Type of the Value Stored
 */
public class BTree<Key extends Comparable<Key>, Value> {

	/**
	 * Tree parameter. Every node must have at most M - 1 key-link pairs
	 */
//	public static final int M = 4;
//	public static final int M = 128;
	public static final int M = 32767;


	protected Node<Key, Value> root;
	/**
	 * Height of B-Tree
	 */
	private int height;
	/**
	 * Number of key-value pairs in B-Tree
	 */
	private int size;

	public BTree() {
		root = new Node<Key, Value>(0);
	}


	public int getSize() {
		return size;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Search for given key
	 * 
	 * @param key
	 *            Key to search
	 * @return Associated value; return null if no such key
	 */
	public Value get(Key key) {
		return search(root, key, height);
	}
	
	/**
	 * @param treeHeight current subtree height
	 */
	private Value search(Node<Key, Value> node, Key key, int treeHeight) {
		Entry<Key, Value>[] children = node.getChildrenArray();

		// external node
		if (treeHeight == 0) {
			for (int j = 0; j < node.getNumberOfChildren(); j++) {
				if (equal(key, children[j].getKey())) {
					return (Value) children[j].getValue();
				}
			}
		}
		// internal node
		else {
			for (int j = 0; j < node.getNumberOfChildren(); j++) {
				if (j== node.getNumberOfChildren() - 1 || less(key, children[j + 1].getKey()))
					return search(children[j].getNext(), key, treeHeight - 1);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void put(Key key) {
		put(key, (Value) key);
	}

	public String toString() {
		return toString(root, height, "") + "\n";
	}

	private boolean less(Key k1, Key k2) {
		return k1.compareTo(k2) < 0;
	}

	private boolean equal(Key k1, Key k2) {
		return k1.compareTo(k2) == 0;
	}

	//================ Eric Jones - Begins =================================
	
	public Value findReplace(Key key, Value val){
		return searchReplace(root, key, val, height);
	}
	
	private Value searchReplace(Node<Key, Value> node, Key key, Value val, int treeHeight) {
		Entry<Key, Value>[] children = node.getChildrenArray();

		// external node
		if (treeHeight == 0) {
			for (int j = 0; j < node.getNumberOfChildren(); j++) {
				if (equal(key, children[j].getKey())) {
					children[j].setValue(val);
					return  val;
				}
			}
		}

		// internal node
		else {
			for (int j = 0; j < node.getNumberOfChildren(); j++) {
				if (j == node.getNumberOfChildren() - 1 || less(key, children[j+1].getKey()))
					return searchReplace(children[j].getNext(), key, val, treeHeight - 1);
			}
		}
		return null;
	}
	
	//================ Eric Jones - Ends =================================
	
	/**
	 * search for the minimal node
	 * fyu
	 */
	public Node<Key, Value> getMinNode(){
		return searchMinNode(root, height);
	}
	private Node<Key, Value> searchMinNode(Node<Key, Value> node, int treeHeight) {
		// external node
		if (treeHeight == 0) {
			return node;
		}
		// internal node
		else {
			return searchMinNode(node.getChildrenArray()[0].getNext(),treeHeight - 1);
		}
	}
	
	/**
	 * Inserts a Key-Value pair
	 * 
	 * @param key
	 *            Key to insert
	 * @param value
	 *            Value to insert
	 */
	@SuppressWarnings("unchecked")
	public void put(Key key, Value value) {
		// TODO (cgavidia): Rename u and t variable names
		Node<Key, Value> nodeFromSplit = insert(root, key, value, height);
		size++;
		if (nodeFromSplit == null) {
			return;
		}

		Node<Key, Value> newRoot = new Node<Key, Value>(2);
		newRoot.getChildrenArray()[0] = new Entry<Key, Value>(root.getChildrenArray()[0].getKey(), null, root);
		newRoot.getChildrenArray()[1] = new Entry<Key, Value>(nodeFromSplit.getChildrenArray()[0].getKey(), null,
				nodeFromSplit);
		root = newRoot;
		height++;
	}
	
	/**
	 * insert <key, value> into subtree
	 * @param node current subtree root
	 * @param key
	 * @param value
	 * @param treeHeight current subtree height
	 * @return
	 */
	private Node<Key, Value> insert(Node<Key, Value> node, Key key, Value value, int treeHeight) {
		int newEntryPosition;
		Entry<Key, Value> entryToInsert = new Entry<Key, Value>(key, value, null);
		// external node
		if (treeHeight == 0) {
			for (newEntryPosition = 0; newEntryPosition < node
					.getNumberOfChildren(); newEntryPosition++) {
				if (less(key, node.getChildrenArray()[newEntryPosition].getKey())) {
					break;
				}
			}
		}
		// internal node
		else {
			for (newEntryPosition = 0; newEntryPosition < node.getNumberOfChildren(); newEntryPosition++) {
				if ((newEntryPosition == node.getNumberOfChildren()-1) || less(key,node.getChildrenArray()[newEntryPosition + 1].getKey())){
					Node<Key, Value> nodeFromSplit = 
							insert(node.getChildrenArray()[newEntryPosition++].getNext(),key, value, treeHeight - 1);
					if (nodeFromSplit == null) {
						return null;
					}
					entryToInsert.setKey(nodeFromSplit.getChildrenArray()[0].getKey());
					entryToInsert.setNext(nodeFromSplit);
					break;
				}
			}
		}
		//set ONLY leaf node[M-1] to point to its next sibling node
		Node<Key, Value> nextTemp=null;//temp pointer
		if(treeHeight==0 && node.getChildrenArray()[M-1]!=null){
			nextTemp=node.getChildrenArray()[M-1].getNext();//if current node is full and next is pointing to a sibling node
		}
		for (int i = node.getNumberOfChildren(); i > newEntryPosition; i--) {
			//move entry one step backward
			//if the current node is full and pointing to a sibling node, this will clean the next pointer
			node.getChildrenArray()[i] = node.getChildrenArray()[i - 1];
		}
		node.getChildrenArray()[newEntryPosition] = entryToInsert;//if the current node is full and pointing to a sibling node, this will clean the next pointer
		node.setNumberOfChildren(node.getNumberOfChildren() + 1);
		if(treeHeight==0 && node.getChildrenArray()[M-1]!=null && nextTemp!=null){
			node.getChildrenArray()[M-1].setNext(nextTemp);//restore the last pointer to the sibling node
		}
		if (node.getNumberOfChildren() < M) {
			return null;
		} else {
			if(treeHeight!=0){
				return splitInternal(node);
			}
			else{
				return splitLeaf(node);
			}

		}
	}
	
	/**
	 * Splits node in half
	 * internal nodes set the next pointer to the sibling node 
	 * 
	 * @param oldNode
	 *            The Node to Split
	 */
	private Node<Key, Value> splitLeaf(Node<Key, Value> oldNode) {
		Node<Key, Value> newNode = new Node<Key, Value>(M / 2);
		oldNode.setNumberOfChildren(M / 2);
		for (int j = 0; j < M / 2; j++) {
			newNode.getChildrenArray()[j] = oldNode.getChildrenArray()[M/ 2 + j];
		}
		newNode.getChildrenArray()[M-1]=new Entry<Key, Value>(null,null,oldNode.getChildrenArray()[M-1].getNext());
		
		//clean unused space
		for (int j = 0; j < M / 2-1; j++) {
			oldNode.getChildrenArray()[M/ 2 + j]=null;
		}
		oldNode.getChildrenArray()[M-1]=new Entry<Key, Value>(null,null,newNode);		
		return newNode;
	}
	
	/**
	 * split root is different from internal
	 */
	private Node<Key, Value> splitInternal(Node<Key, Value> oldNode) {
		Node<Key, Value> newNode = new Node<Key, Value>(M / 2);
		oldNode.setNumberOfChildren(M / 2);
		for (int j = 0; j < M / 2; j++) {
			newNode.getChildrenArray()[j] = oldNode.getChildrenArray()[M
					/ 2 + j];
		}
		return newNode;
	}

	
	/**
	 * output all leaf entries
	 * @return
	 */
	public ArrayList<Entry<Key, Value>> getLeafEntryList() {
		ArrayList<Entry<Key, Value>> entry_list=new ArrayList<Entry<Key, Value>>(1024);
		Node<Key, Value> current_node=this.getMinNode();
		Entry<Key, Value>[] children_array = current_node.getChildrenArray();
		while(true){
			for(int j=0; j<current_node.getNumberOfChildren(); j++){
				entry_list.add(children_array[j]);
			}
			if(children_array[M-1]==null){
				break;
			}
			current_node=children_array[M-1].getNext();
			if(current_node==null){
				break;
			}
			children_array=current_node.getChildrenArray();
		}
		return entry_list;
	}
	
	private String toString(Node<Key, Value> currentNode, int ht, String indent) {
		String outputString = "";
		if(currentNode==null || currentNode.getChildrenArray()==null){
			return "";
		}
		Entry<Key, Value>[] childrenArray = currentNode.getChildrenArray();
		
		if (ht == 0) {
			for (int j = 0; j < currentNode.getNumberOfChildren(); j++) {
				outputString += indent + childrenArray[j].getKey() + " "
						+ childrenArray[j].getValue() + "\n";
			}
		} else {
			int num_children=currentNode.getNumberOfChildren();
			outputString += toString(childrenArray[0].getNext(), ht - 1, indent + "     ");
			outputString += indent + "[" + childrenArray[0].getKey() + "\n";
			if(num_children>=2){
				for (int j = 1; j < num_children - 1; j++) {
					outputString += indent + "(" + childrenArray[j].getKey() + ")\n";
					outputString += toString(childrenArray[j].getNext(), ht - 1, indent + "     ");
				}
			}
			outputString += indent + "(" + childrenArray[num_children-1].getKey() + ")]\n";
			outputString += toString(childrenArray[num_children-1].getNext(), ht - 1, indent + "     ");
		}
		return outputString;
	}



	private int toByte(Node<Key, Value> currentNode, int treeHeight){
		int space=0;
		if(currentNode==null || currentNode.getChildrenArray()==null){
			return 0;
		}
		Entry<Key, Value>[] childrenArray = currentNode.getChildrenArray();
		if(treeHeight==0){
			for (int j = 0; j < currentNode.getNumberOfChildren(); j++) {
				space+=Entry.key_size+Entry.value_size;//space for key and value
			}
			if(currentNode.getChildrenArray()[M-1]!=null && currentNode.getChildrenArray()[M-1].getNext()!=null){
				space+=Entry.pointer_size;//pointer to sibling
			}			
		}else{
			int num_children=currentNode.getNumberOfChildren();
			space+=Entry.pointer_size;//first pointer to left child
			space+=toByte(childrenArray[0].getNext(),treeHeight-1);
			if(num_children>=2){
				for (int j=1; j<num_children; j++){
					space+=Entry.key_size+Entry.pointer_size;//space for key and pointer to next
					space+=toByte(childrenArray[j].getNext(),treeHeight-1);
				}
			}
		}
		return space;
	}
	
	public int toByte(){
		return toByte(root, height);
	}
	
	public double toKB(){
		return toByte()*1.0/1024.0;
	}
}


/**
 * B+-tree deletion
 * test code in test1.test_btree_4_deletion
 */



/*
// Eric Jones added
public Value findDelete(Key key, Value val){
return searchRemove(root, key, val, height);
}

private Value searchRemove(Node<Key, Value> node, Key key, Value val, int treeHeight) {
Entry<Key, Value>[] children = node.getChildrenArray();

// external node
if (treeHeight == 0) {
	for (int j = 0; j < node.getNumberOfChildren(); j++) {
		if (equal(key, children[j].getKey())) {
			children[j].setKey(null);
			children[j].setValue(null);
			
			return  val; //perhaps this should be return null, PERHAPS!!!!!!
		}
	}
}

// internal node
else {
	for (int j = 0; j < node.getNumberOfChildren(); j++) {
		if (node.getNumberOfChildren() == j + 1
				|| less(key, children[j + 1].getKey()))
			return searchRemove(children[j].getNext(), key, val, treeHeight - 1);
	}
}
return null;
}

public Value findReplace(Key key, Value val){
return searchReplace(root, key, val, height);
}

private Value searchReplace(Node<Key, Value> node, Key key, Value val, int treeHeight) {
Entry<Key, Value>[] children = node.getChildrenArray();

// external node
if (treeHeight == 0) {
	for (int j = 0; j < node.getNumberOfChildren(); j++) {
		if (equal(key, children[j].getKey())) {
			children[j].setValue(val);
			return  val;
		}
	}
}

// internal node
else {
	for (int j = 0; j < node.getNumberOfChildren(); j++) {
		if (node.getNumberOfChildren() == j + 1
				|| less(key, children[j + 1].getKey()))
			return searchReplace(children[j].getNext(), key, val, treeHeight - 1);
	}
}
return null;
}

*/