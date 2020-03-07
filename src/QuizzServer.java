

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;



/**
 * 
 * @author Daragh Walshe 	B00064428
 * RMI Assignment 2		 	April 2015
 *
 */
public class QuizzServer extends UnicastRemoteObject implements QuizzServerInterface {
	String line = "---------------------------------------------\n";
	private Vector<QuizzUser> chatters;
	private static final long serialVersionUID = 1L;
	private List<Quizz> quizzes =new ArrayList<>();
	private List<Quizz> quizone=new ArrayList<Quizz>();
	private List<Quizz> quiztwo=new ArrayList<Quizz>();
	
	HashMap<String, Integer> matches=new HashMap<String, Integer>();

	//Constructor
	public QuizzServer() throws RemoteException {
		super();
		chatters = new Vector<QuizzUser>(10, 1);
		quizzes.add(new Quizz("myanmar's president is aung sun sukyi", false));
		quizzes.add(new Quizz("chicken has four legs", false));
		quizzes.add(new Quizz("birds stay on water", true));
		quizzes.add(new Quizz("spring is cold", false));
		quizzes.add(new Quizz("winter is cold", false));
		quizzes.add(new Quizz("Cu is good", true));
		quizzes.add(new Quizz("USA's president is Obama", false));
		quizzes.add(new Quizz("lwin moe hein is god", false));
		
		quizone.add(quizzes.get(0));
		quizone.add(quizzes.get(2));
		quizone.add(quizzes.get(1));
		quizone.add(quizzes.get(3));
		
		quiztwo.add(quizzes.get(4));
		quiztwo.add(quizzes.get(5));
		quiztwo.add(quizzes.get(6));
		quiztwo.add(quizzes.get(7));


		
		
	

	


	}
	
	//-----------------------------------------------------------
	/**
	 * LOCAL METHODS
	 */	
	public static void main(String[] args) {
		startRMIRegistry();	
		String hostName = "localhost";
		String serviceName = "QuizzService";
		
		if(args.length == 2){
			hostName = args[0];
			serviceName = args[1];
		}
		
		try{
			QuizzServerInterface hello = new QuizzServer();
			Naming.rebind("rmi://" + hostName + "/" + serviceName, hello);
			System.out.println("Group Chat RMI Server is running...");
		}
		catch(Exception e){
			System.out.println("Server had problems starting");
		}	
	}

	
	/**
	 * Start the RMI Registry
	 */
	public static void startRMIRegistry() {
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI Server ready");
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
	}
		
	
	//-----------------------------------------------------------
	/*
	 *   REMOTE METHODS
	 */
	
	/**
	 * Return a message to client
	 */
	public String sayHello(String ClientName) throws RemoteException {
		System.out.println(ClientName + " sent a message");
		return "Hello " + ClientName + " from group chat server";
	}
	

	/**
	 * Send a string ( the latest post, mostly ) 
	 * to all connected clients
	 */
	public void updateChat(String name, String nextPost) throws RemoteException {
		String message =  name + " : " + nextPost + "\n";
		sendToAll(message);
	}
	
	/**
	 * Receive a new client remote reference
	 */
	@Override
	public void passIDentity(RemoteRef ref) throws RemoteException {	
		//System.out.println("\n" + ref.remoteToString() + "\n");
		try{
			System.out.println(line + ref.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}//end passIDentity

	
	/**
	 * Receive a new client and display details to the console
	 * send on to register method
	 */
	@Override
	public void registerListener(String[] details) throws RemoteException {	
		System.out.println(new Date(System.currentTimeMillis()));
		System.out.println(details[0] + " has joined the quizz session");
		System.out.println(details[0] + "'s hostname : " + details[1]);
		System.out.println(details[0] + "'sRMI service : " + details[2]);
		registerChatter(details);
	}

	
	/**
	 * register the clients interface and store it in a reference for 
	 * future messages to be sent to, ie other members messages of the chat session.
	 * send a test message for confirmation / test connection
	 * @param details
	 */
	private void registerChatter(String[] details){		
		try{
			QuizzClientInterface nextClient = ( QuizzClientInterface )Naming.lookup("rmi://" + details[1] + "/" + details[2]);
			
			chatters.addElement(new QuizzUser(details[0], nextClient));
			
			nextClient.messageFromServer("[Server] : Hello " + details[0] + " you are now free to do quizzes.\n");
			
			sendToAll("[Server] : " + details[0] + " has joined the group.\n");
			
			updateUserList();		
		}
		catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Update all clients by remotely invoking their
	 * updateUserList RMI method
	 */
	private void updateUserList() {
		String[] currentUsers = getUserList();	
		for(QuizzUser c : chatters){
			try {
				c.getClient().updateUserList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	

	/**
	 * generate a String array of current users
	 * @return
	 */
	private String[] getUserList(){
		// generate an array of current users
		String[] allUsers = new String[chatters.size()];
		for(int i = 0; i< allUsers.length; i++){
			allUsers[i] = chatters.elementAt(i).getName();
		}
		return allUsers;
	}
	

	/**
	 * Send a message to all users
	 * @param newMessage
	 */
	public void sendToAll(String newMessage){	
		for(QuizzUser c : chatters){
			try {
				c.getClient().messageFromServer(newMessage);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}

	
	/**
	 * remove a client from the list, notify everyone
	 */
	@Override
	public void leaveChat(String userName) throws RemoteException{
		
		for(QuizzUser c : chatters){
			if(c.getName().equals(userName)){
				System.out.println(line + userName + " left the chat session");
				System.out.println(new Date(System.currentTimeMillis()));
				chatters.remove(c);
				break;
			}
		}		
		if(!chatters.isEmpty()){
			updateUserList();
		}			
	}
	

	/**
	 * A method to send a private message to selected clients
	 * The integer array holds the indexes (from the chatters vector) 
	 * of the clients to send the message to
	 */
	@Override
	public void sendPM(int[] privateGroup, String privateMessage) throws RemoteException{
		QuizzUser pc;
		for(int i : privateGroup){
			pc= chatters.elementAt(i);
			pc.getClient().messageFromServer(privateMessage);
		}
	}

	@Override
	public void sendAnswers(List<Answer> answers,String username,String opponent,List<Integer> receivers) throws RemoteException {
		int mark=0;
		for(Answer ans:answers) {
		for(Quizz q:quizzes) {
	            if(ans.id.equals(q.question)) {
	            	if(ans.ans==q.ans) {
	            		mark+=1;
	            	}
	            }
		}
		}
		matches.put(username, mark);

		if(matches.containsKey(opponent)) {
			System.out.println(username+":"+matches.get(username)+"  "+opponent+":"+matches.get(opponent));
			AnswerResponse ans=new AnswerResponse(username, opponent, matches.get(username), matches.get(opponent));
			QuizzUser pc;
			System.out.println(receivers.size());
			for(int i : receivers){
				pc= chatters.elementAt(i);
				pc.getClient().answerFromServer(ans);
			}
		}
	}

	@Override
	public void createCompetition(List<Integer> receivers,String challengername,String name) throws RemoteException {
		// TODO Auto-generated method stub

		QuizzUser pc;
		for(int i : receivers){
			pc= chatters.elementAt(i);
			System.out.println(receivers.size()+" "+receivers.get(0)+" "+receivers.get(1));
			pc.getClient().quizzesFromServer(challengername,name, getQuiz(),receivers);	
		}
		//chatters.elementAt(0).client.quizzesFromServer(challengername, createQuizzes());
	}
	public List<Quizz> getQuiz(){
		int quirandom=(int)(Math.random() * 1) + 0;
		return quiztwo;
//        if(quirandom==0) {
//        	return quizone;
//        }else {
//        	return quiztwo;
//        }

        
	}
}//END CLASS



