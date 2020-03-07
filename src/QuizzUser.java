


/**
 * A class used by the server program to keep
 * details of connected clients ordered
 * @author Daragh Walshe 	B00064428
 * RMI Assignment 2		 	April 2015
 *
 */
public class QuizzUser {

	public String name;
	public QuizzClientInterface client;
	
	//constructor
	public QuizzUser(String name, QuizzClientInterface client){
		this.name = name;
		this.client = client;
	}

	
	//getters and setters
	public String getName(){
		return name;
	}
	public QuizzClientInterface getClient(){
		return client;
	}
	
	
}
