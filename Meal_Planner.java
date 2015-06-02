import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.InputStream;
import java.util.Scanner;

	/**
	 *
	 * @author binalkamani
	 */
	public class Meal_Planner{

	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String[] args) {
	        
	        // create an empty Model
	        Model model = ModelFactory.createDefaultModel();
	        Scanner input = new Scanner(System.in);
	        Scanner input1 = new Scanner(System.in);
	        
	        int i = 0, m = 0;
	        String[] ingredient = new String[10];
	        StringBuilder sqquery = new StringBuilder();
	        // use the FileManager to find the input file
	        
	        InputStream in = FileManager.get().open( "/home/binal/Desktop/rdf/cooking.owl" );
	        if (in == null) {
	            throw new IllegalArgumentException(
	                                         "File: " + "./cooking.owl" + " not found");
	        }

	        // read the RDF/XML file
	        model.read(in, null);
	        
	        // Get cuisine
	        System.out.println("Enter type of Cuisine from the options: (Thai, Italian)");
	        String cuisine_type = input.nextLine();
	        
	        //Get number of ingredient request
	        System.out.println("Enter number of ingredients you want to enter (between 1 to 5): ");
	        int ing_no = input.nextInt();
	        
	        //Get all ingredients user want
	        while (i < ing_no){
	        	System.out.println("Please enter the ingredient " + i + " name: ");
	        	ingredient[i] = input1.nextLine();
	        	System.out.println(ingredient[i]);
	        	i++;
	        }
	                      
	        //Sparql Query PREFIX
	        sqquery.append("PREFIX abc: <http://www.semanticweb.org/ontologies/2015/3/Cooking_Ontology.owl#>\n" + 
	        		"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n");
	        		
	        //Based on number of ingredients run sparql query
	        switch(ing_no){
	        	case 5:
	        		sqquery.append("SELECT ?Ingredient ?Ingredient1 ?Ingredient2 ?Ingredient3 ?Ingredient4 ?Recipe\n" +
	    	        		"WHERE {?Recipe rdf:type abc:" + cuisine_type + ".\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient.\n" + 
	    	        		"?Recipe abc:hasIngredient ?Ingredient1.\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient2.\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient3.\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient4.\n" +
	    	        		"FILTER(REGEX(STR(?Ingredient), ('" + ingredient[0] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient1), ('" + ingredient[1] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient2), ('" + ingredient[2] + "'), 'i'))\n" +
	    	        		"FILTER(REGEX(STR(?Ingredient3), ('" + ingredient[3] + "'), 'i'))\n" +
	    	        		"FILTER(REGEX(STR(?Ingredient4), ('" + ingredient[4] + "'), 'i'))}");
	        		break;
	        	case 4:
	        		sqquery.append("SELECT ?Ingredient ?Ingredient1 ?Ingredient2 ?Ingredient3 ?Recipe\n" +
	    	        		"WHERE {?Recipe rdf:type abc:" + cuisine_type + ".\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient.\n" + 
	    	        		"?Recipe abc:hasIngredient ?Ingredient1.\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient2.\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient3.\n" +
	    	        		"FILTER(REGEX(STR(?Ingredient), ('" + ingredient[0] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient1), ('" + ingredient[1] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient2), ('" + ingredient[2] + "'), 'i'))\n" +
	    	        		"FILTER(REGEX(STR(?Ingredient3), ('" + ingredient[3] + "'), 'i'))}");
	        		break;
	        	case 3:
	        		sqquery.append("SELECT ?Ingredient ?Ingredient1 ?Ingredient2 ?Recipe\n" +
	    	        		"WHERE {?Recipe rdf:type abc:" + cuisine_type + ".\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient.\n" + 
	    	        		"?Recipe abc:hasIngredient ?Ingredient1.\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient2.\n" +
	    	        		"FILTER(REGEX(STR(?Ingredient), ('" + ingredient[0] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient1), ('" + ingredient[1] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient2), ('" + ingredient[2] + "'), 'i'))}");
	        		break;
	        	case 2:
	        		sqquery.append("SELECT ?Ingredient ?Ingredient1 ?Recipe\n" +
	    	        		"WHERE {?Recipe rdf:type abc:" + cuisine_type + ".\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient.\n" + 
	    	        		"?Recipe abc:hasIngredient ?Ingredient1.\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient), ('" + ingredient[0] + "'), 'i'))\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient1), ('" + ingredient[1] + "'), 'i'))}");		
	        		break;
	        	case 1:
	        		sqquery.append("SELECT ?Ingredient ?Recipe\n" +
	    	        		"WHERE {?Recipe rdf:type abc:" + cuisine_type + ".\n" +
	    	        		"?Recipe abc:hasIngredient ?Ingredient.\n" + 
	    	        		"FILTER(REGEX(STR(?Ingredient), ('" + ingredient[0] + "'), 'i'))}");
	        		break;
	        }
	        
	        //Parse sparql query
	        String parse_query = sqquery.toString(); 
	        
	        //Execute Query
	        QueryExecution qe = QueryExecutionFactory.create(parse_query, model);
	        ResultSet rs = qe.execSelect();
	        int recp = 1;
	        
	        //Show preferred recipe result
	        System.out.println("\nYou Should eat:");
	        while (rs.hasNext()) { 	
	            QuerySolution row=rs.nextSolution();
	            System.out.println(recp + ". " + row.getResource("Recipe").getLocalName());
	            recp++;
	          }   
	        
	        //An array for restaurant comparision
	        String restaurant[][] = new String[4][3];
	        
            restaurant[0][0]="Abocas";
            restaurant[0][1]="Italian";
            restaurant[0][2]="AB";
            restaurant[1][0]="Bambu";
            restaurant[1][1]="Thai";
            restaurant[1][2]="AE";
            restaurant[2][0]="Olive_Garden";
            restaurant[2][1]="Italian";
            restaurant[2][2]="CE";
            restaurant[3][0]="ChowThai";
            restaurant[3][1]="Thai";
            restaurant[3][2]="CB";

            int restaurant_loc[] = {0,6,7,1};

            //Show the restaurants which offer requested recipes
            System.out.println("\nRestaurants offering this recipes are: ");
            
            for(int par=0;par<4;par++)
            {
                if(restaurant[par][1].equalsIgnoreCase(cuisine_type))
                        System.out.println(">>" + restaurant[par][0]);
            }

            //Get user's location
            System.out.println("\nEnter your location : ");
            String yourLocation = input1.nextLine();
  
            String location[] = {"Coit@Campbell","Coit@Arapahoe","Coit@BeltLine","Custer@Campbell","Custer@Arapahoe","Jupiter@Campbell","Jupiter@BeltLine"
            		,"Shiloh@Campbell","Shiloh@Arapahoe"};
            
            for(int loc=0;loc<9;loc++)
            {
            	//Ignore case while comparing locations and get the proper index
                if(yourLocation.equalsIgnoreCase(location[loc]))
                {
                    m = loc;
                }
            }
            
            for(int par=0;par<4;par++)
            {
            	
                if(restaurant[par][1].equalsIgnoreCase(cuisine_type))
                {
                	
            	System.out.println("***********************************************************");
                
                String path_inadm = location_map.find_inadmissible_path(m,restaurant_loc[par]);
                
                System.out.println("Inadmissible Heuristic Calculation");
                
                System.out.print("\nTo reach Restaurant " + restaurant[par][0] + " is : \n \t ");
                for(int len=path_inadm.length()-1;len>=0;len--)
                {
                    String str_inadm=Character.toString(path_inadm.charAt(len));
                    int n1_inadm = Integer.parseInt(str_inadm);
                    if(len>0)
                            System.out.print(location[n1_inadm] + "--->");
                    else
                            System.out.print(location[n1_inadm]);
                }

                double distance1=0;
                for(int len=path_inadm.length()-1;len>0;len--)
                {
                    String str1_inadm=Character.toString(path_inadm.charAt(len));
                    int n1_inadm = Integer.parseInt(str1_inadm);
                    String str2_inadm=Character.toString(path_inadm.charAt(len-1));
                    int n2_inadm = Integer.parseInt(str2_inadm);
                    distance1 = distance1 + location_map.gn_matrix[n1_inadm][n2_inadm];
                }
                System.out.println("\nTotal distance between current location and restaurant " + restaurant[par][0] + " is : " + distance1 + " miles \n");
            	
                System.out.println("Admissible Heuristic Calculation");

                	//Call method for A Star search
                    String path = location_map.find_path(m,restaurant_loc[par]);
                                        
                    //Show the path from user's location to the desired restaurant
                    System.out.print("\nTo reach to Restaurant " + restaurant[par][0] + " is : \n \t ");
                    for(int len=path.length()-1;len>=0;len--)
                    {
                        String str=Character.toString(path.charAt(len));
                        int n1 = Integer.parseInt(str);
                        if(len>0)
                                System.out.print(location[n1] + "--->");
                        else
                                System.out.print(location[n1]);
                    }

                    double distance=0;
                    for(int len=path.length()-1;len>0;len--)
                    {
                        String str1=Character.toString(path.charAt(len));
                        int n1 = Integer.parseInt(str1);
                        String str2=Character.toString(path.charAt(len-1));
                        int n2 = Integer.parseInt(str2);
                        distance = distance + location_map.gn_matrix[n1][n2];
                    }
                    System.out.println("\nTotal distance between current location and restaurant " + restaurant[par][0] + " is : " + distance + " miles \n");                                    
                
                }
            }        
	    }
}


