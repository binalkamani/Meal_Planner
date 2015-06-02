

public class location_map {

	 static double gn_matrix[][] = {
         {0.0,2.2,3.9,2.1,3.9,4.9,3.5,5.7,6.9},
         {2.2,0.0,1.7,4.3,1.7,2.7,5.7,3.5,4.7},
         {3.9,1.7,0.0,5.1,2.5,1.5,6.5,4.3,3.5},
         {2.1,4.3,5.1,0.0,2.6,3.6,1.4,4.3,5.5},
         {3.9,1.7,2.5,2.6,0.0,1.0,4.0,1.8,3.0},
         {4.9,2.7,1.5,3.6,1.0,0.0,5.0,2.8,2.0},
         {3.5,5.7,6.5,1.4,4.0,5.0,0.0,2.9,4.1},
         {5.7,3.5,4.3,4.3,1.8,2.8,2.9,0.0,1.2},
         {6.9,4.7,3.5,5.5,3.0,2.0,4.1,1.2,0.0}
         };

	static double hn_matrix[][] = {
		 {0.0,1.7,3.1,1.7,3.1,3.9,2.8,4.6,5.5},
		 {1.7,0.0,1.3,3.4,1.3,2.2,4.6,2.8,3.8},
		 {3.1,1.3,0.0,4.1,2.0,1.2,5.2,3.4,2.8},
		 {1.7,3.4,4.1,0.0,2.1,2.9,1.1,3.4,4.4},
		 {3.1,1.3,2.0,2.1,0.0,0.8,3.2,1.4,2.4},
		 {3.9,2.2,1.2,2.9,0.8,0.0,4.0,2.2,1.6},
		 {2.8,4.6,5.2,1.1,3.2,4.0,0.0,2.3,3.2},
		 {4.6,2.8,3.4,3.4,1.4,2.2,2.3,0.0,1.0},
		 {5.5,3.8,2.8,4.4,2.4,1.6,3.2,1.0,0.0}
		 };

	
	static double inadm_hn_matrix[][] = {
        {0.0,4.4,7.8,4.8,7.8,9.8,7.0,11.4,13.8},
        {4.4,0.0,1.7,4.3,1.7,2.7,5.7,3.5,4.7},
        {7.8,1.7,0.0,5.1,2.5,1.5,6.5,4.3,3.5},
        {4.2,4.3,5.1,0.0,2.6,3.6,1.4,4.3,5.5},
        {7.8,1.7,2.5,2.6,0.0,1.0,4.0,1.8,3.0},
        {9.8,2.7,1.5,3.6,1.0,0.0,5.0,2.8,2.0},
        {7.0,5.7,6.5,1.4,4.0,5.0,0.0,2.9,4.1},
        {11.4,3.5,4.3,4.3,1.8,2.8,2.9,0.0,1.2},
        {13.8,4.7,3.5,5.5,3.0,2.0,4.1,1.2,0.0}
        };
	
	static int adj_loc[][] = {
        {0,1,0,1,0,0,0,0,0},
        {1,0,1,0,1,0,0,0,0},
        {0,1,0,0,0,1,0,0,0},
        {1,0,0,0,1,0,1,0,0},
        {0,1,0,1,0,1,0,1,0},
        {0,0,1,0,1,0,0,0,1},
        {0,0,0,1,0,0,0,1,0},
        {0,0,0,0,1,0,1,0,1},
        {0,0,0,0,0,1,0,1,0}
        };
	
	public static String find_inadmissible_path(int source,int goal)
    {
		String path="";
		
        int head=source;
        double min=65535; 

        Node start_node = null, int_node1 = null, int_node2=null, current_node = null;
        Node new_node = null;

        new_node = new Node(head,hn_matrix[head][goal]);
        
        start_node = new_node;
        int_node1 = new_node;
        current_node = new_node;
        
        int n = 0;
        int dist = 0;
        
        while(start_node != null)
        {
            for(n=0;n<9;n++)
            {
                if(adj_loc[head][n]==1)
                {
                    if(current_node != start_node)
                    {
	                    int_node2 = current_node;
	                    while(int_node2 != start_node)
	                    {
                            dist+=gn_matrix[int_node2.idx][int_node2.parent.idx];
                            int_node2 = int_node2.parent;
	                    }
                    }
                    int_node1 = new_node;
	                new_node = new Node(n,dist+ gn_matrix[head][n] + 10*hn_matrix[head][n]);
	                new_node.parent = current_node;
	                int_node1.next = new_node;
	                current_node.visited = 1;
	
	                if(new_node.idx == goal)
	                	{
	                		break;
	                	}
	                }
                }

                if(n<9){
                	break;
                }
                	

                current_node = null;

                int_node1 = start_node;
                
                while(int_node1 != null)
                {
                    if(int_node1.func < min && int_node1.visited == 0)
                    {
                            min = int_node1.func;
                            current_node = int_node1;
                            head = int_node1.idx;
                       }
                    int_node1 = int_node1.next;
                   }
                   min = 65535;
                   dist=0;
           }
        int_node1=new_node;
        
       while(int_node1!=null)
       {
               path = path + Integer.toString(int_node1.idx);             
               int_node1 = int_node1.parent;
       }
       return path;

    }


public static String find_path(int source,int goal)
{
	String path="";
	
    int index=source;
    double min=65535; 

    Node start_node = null, int_node1 = null, int_node2=null, current_node = null;
    Node new_node = null;

    new_node = new Node(index,hn_matrix[index][goal]);
    
    start_node = new_node;
    int_node1 = new_node;
    current_node = new_node;
    
    int n = 0;
    int dist = 0;
    
    while(start_node != null)
    {
        for(n=0;n<9;n++)
        {
            if(adj_loc[index][n]==1)
            {
                if(current_node != start_node)
                {
                    int_node2 = current_node;
                    while(int_node2 != start_node)
                    {
                        dist+=gn_matrix[int_node2.idx][int_node2.parent.idx];
                        int_node2 = int_node2.parent;
                    }
                }
                int_node1 = new_node;
                new_node = new Node(n,dist+gn_matrix[index][n] + hn_matrix[n][goal]);
                new_node.parent = current_node;
                int_node1.next = new_node;
                current_node.visited = 1;

                if(new_node.idx == goal)
                	{
                		break;
                	}
                }
            }

            if(n<9){
            	break;
            }
            	

            current_node = null;

            int_node1 = start_node;
            
            while(int_node1 != null)
            {
                if(int_node1.func < min && int_node1.visited == 0)
                {
                        min = int_node1.func;
                        current_node = int_node1;
                        index = int_node1.idx;
                   }
                int_node1 = int_node1.next;
               }
               min = 65535;
               //path = path + Integer.toString(current.index);
               dist=0;
       }
    int_node1=new_node;
    
   while(int_node1!=null)
   {
           path = path + Integer.toString(int_node1.idx);             
           int_node1 = int_node1.parent;
   }
   return path;

}
}

class Node
{
        int idx;
        double func;
        Node next;
        Node parent;
        int visited;

        public Node(int ind,double eval)
        {
                idx = ind;
                func = eval;
                next = null;
                parent = null;
                visited = 0;
        }
}