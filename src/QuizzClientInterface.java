

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for client classes
 * A method to receive a string
 * A method to update changes to user list
 * 
 * @author Daragh Walshe 	B00064428
 * RMI Assignment 2		 	April 2015
 *
 */
public interface QuizzClientInterface extends Remote{

	public void messageFromServer(String message) throws RemoteException;

	public void updateUserList(String[] currentUsers) throws RemoteException;
	
	public void answerFromServer(AnswerResponse answerresponse) throws RemoteException;
	
	public void quizzesFromServer(String name,String challengername,List<Quizz> quizzes,List<Integer> receivers) throws RemoteException;
}
/**
 * 
 * 
 * 
 *
 */
