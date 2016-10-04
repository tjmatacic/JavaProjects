/*
package tyler_test;
import java.io.*;

//only the last offset of an updated OID is recorded in an OBTree
public class SearchOBTree 

{
	public static void main (String[] args)
	{
		
		public static int search  
		{
			int OID;
			if (OID == null)
				return false;
			else
				
						
		}
		
	}

}
*/
package tyler_test;  

//only the last offset of an updated OID is recorded in the OBTree   
public class SearchOBTree 
{  
  
   
 public static class TreeNode  
 {  
  int OID;  
  TreeNode left;  
  TreeNode right;  
  TreeNode(int offset)  
  {  
   this.OID=offset;  
  }  
 }  
   
        // Recursive Solution  
 public void inOrder(TreeNode root) 
 {  
  if(root !=  null) 
  {  
   inOrder(root.left);  
   //Visit the node by Printing the node data    
   System.out.printf("%d ",root.OID);  
   inOrder(root.right);  
  }  
 }  
   
  
}  


