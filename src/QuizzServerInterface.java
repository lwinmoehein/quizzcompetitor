

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.util.List;

/**
 * Server RMI interface
 * 
 * @author Daragh Walshe 	B00064428
 * RMI Assignment 2		 	April 2015
 *
 */
public interface QuizzServerInterface extends Remote {
		
	public void updateChat(String userName, String chatMessage)throws RemoteException;
	
	public void passIDentity(RemoteRef ref)throws RemoteException;
	
	public void registerListener(String[] details)throws RemoteException;
	
	public void leaveChat(String userName)throws RemoteException;
	
	public void sendPM(int[] privateGroup, String privateMessage)throws RemoteException;
	
	public void sendAnswers(List<Answer> answers,String username,String opponent,List<Integer> receivers)throws RemoteException;
	
	public void createCompetition(List<Integer> receivers,String challengername,String name) throws RemoteException;
}


