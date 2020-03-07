
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * 
 * @author Daragh Walshe 	B00064428
 * RMI Assignment 2		 	April 2015
 *
 */
public class QuizzClient  extends UnicastRemoteObject implements QuizzClientInterface {
	/**
	 * 
	 */
	public int userone,usertwo;
	private static final long serialVersionUID = 7468891722773409712L;
	QuizzClientGUI quizzGui;
	private String hostName = "localhost";
	private String serviceName = "QuizzService";
	private String clientServiceName;
	private String name;
	protected QuizzServerInterface serverIF;
	protected boolean connectionProblem = false;
	PlayGUI play;
	
	/**
	 * class constructor,
	 * note may also use an overloaded constructor with 
	 * a port no passed in argument to super
	 * @throws RemoteException
	 */
	public QuizzClient(QuizzClientGUI aChatGUI, String userName) throws RemoteException {
		super();
		this.quizzGui = aChatGUI;
		this.name = userName;
		this.clientServiceName = "ClientListenService_" + userName;
	}

	
	/**
	 * Register our own listening service/interface
	 * lookup the server RMI interface, then send our details
	 * @throws RemoteException
	 */
	public void startClient() throws RemoteException {		
		String[] details = {name, hostName, clientServiceName};	

		try {
			Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
			serverIF = ( QuizzServerInterface )Naming.lookup("rmi://" + hostName + "/" + serviceName);	
		} 
		catch (ConnectException  e) {
			JOptionPane.showMessageDialog(
					quizzGui.frame, "The server seems to be unavailable\nPlease try later",
					"Connection problem", JOptionPane.ERROR_MESSAGE);
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			connectionProblem = true;
			me.printStackTrace();
		}
		if(!connectionProblem){
			registerWithServer(details);
		}	
		System.out.println("Client Listen RMI Server is running...\n");
	}


	/**
	 * pass our username, hostname and RMI service name to
	 * the server to register out interest in joining the chat
	 * @param details
	 */
	public void registerWithServer(String[] details) {		
		try{
			serverIF.passIDentity(this.ref);//now redundant ??
			serverIF.registerListener(details);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//=====================================================================
	/**
	 * Receive a string from the chat server
	 * this is the clients RMI method, which will be used by the server 
	 * to send messages to us
	 */
	@Override
	public void messageFromServer(String message) throws RemoteException {
		System.out.println( message );
		//quizzGui.textArea.append("mes from ser"+ message );
		//make the gui display the last appended text, ie scroll to bottom
		
	}

	/**
	 * A method to update the display of users 
	 * currently connected to the server
	 */
	@Override
	public void updateUserList(String[] currentUsers) throws RemoteException {

		if(currentUsers.length < 2){
			quizzGui.playButton.setEnabled(false);
		}
		quizzGui.userPanel.remove(quizzGui.clientPanel);
		quizzGui.setClientPanel(currentUsers);
		quizzGui.clientPanel.repaint();
		quizzGui.clientPanel.revalidate();
	}


	@Override
	public void quizzesFromServer(String challenger,String name,List<Quizz> quizzes,List<Integer> receivers) throws RemoteException {
		System.out.println(quizzes.size());
		if(this.name.equals(name)) {
			play=new PlayGUI(name,challenger,quizzes,serverIF,receivers);
		}else {
			play=new PlayGUI(challenger,name,quizzes,serverIF,receivers);

		}
	}


	public void answersFromServer() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void answerFromServer(AnswerResponse answerresponse) throws RemoteException {
		String str="";
		str+=answerresponse.userone+":"+answerresponse.useronemark;
		str+="\n"+answerresponse.usertwo+":"+answerresponse.usertwomark;

		
		JFrame jf=new JFrame();
		jf.setTitle("test");
		jf.add(new JLabel(str));
		jf.setVisible(true);


	}

}//end class













