import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class PlayGUI extends JFrame {

	private JPanel contentPane;
	String playerone,playertwo;
	List<Integer> receivers;
	public JLabel quizone,quiztwo,quizthree,quizfour;
	public JRadioButton radio1false,radio1true,radio2false,radio2true,radio3false,radio3true,radio4false,radio4true;
    public List<Quizz> quizzes=new ArrayList<Quizz>();
    QuizzServerInterface serverIF;
	JButton btnSubmit;
	public PlayGUI(String playerone,String playertwo,List<Quizz> quizzess,QuizzServerInterface serverIf,List<Integer> receivers) {
		this.quizzes=quizzess;
		this.serverIF=serverIf;
		this.receivers=receivers;
		this.setVisible(true);
		this.playertwo=playertwo;
		this.playerone=playerone;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel abovepanel = new JPanel();
		contentPane.add(abovepanel, BorderLayout.NORTH);
		abovepanel.setLayout(new GridLayout(1, 3));
		
		JLabel lblYouAnd = new JLabel(this.playerone+"  ");
		lblYouAnd.setFont(new Font("Gargi", Font.BOLD, 20));
		lblYouAnd.setForeground(Color.GREEN);
		abovepanel.add(lblYouAnd);
		
		
		JLabel lblAnd = new JLabel("And");
		lblAnd.setFont(new Font("Gargi", Font.BOLD, 20));
		lblAnd.setForeground(Color.RED);
		abovepanel.add(lblAnd);
		
		JLabel lblNewLabel = new JLabel(this.playertwo);
		lblNewLabel.setFont(new Font("Gargi", Font.BOLD, 20));
		lblNewLabel.setForeground(Color.GREEN);
		abovepanel.add(lblNewLabel);
		
		
		contentPane.add(createBody(), BorderLayout.CENTER);
	
		
		
		
		JPanel footer = new JPanel();
		contentPane.add(footer, BorderLayout.SOUTH);
		
	    btnSubmit = new JButton("Submit Answers");
		footer.add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					serverIF.sendAnswers(getAnswers(),playerone,playertwo,receivers);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	public JPanel createBody() {
		JPanel body = new JPanel();
		
		ButtonGroup g1,g2,g3,g4;
		g1=new ButtonGroup();
		g2=new ButtonGroup();
		g3=new ButtonGroup();
		g4=new ButtonGroup();
		
		
		quizone=new JLabel("Question one "+quizzes.get(0).question);
		quiztwo=new JLabel("Question two "+quizzes.get(1).question);
		quizthree=new JLabel("Question three "+quizzes.get(2).question);
		quizfour=new JLabel("Question four "+quizzes.get(3).question);

		body.setLayout(new GridLayout(4, 3, 0, 0));
		//question one
		body.add(quizone);
		radio1false = new JRadioButton("false");
		g1.add(radio1false);
		body.add(radio1false);
						
		radio1true = new JRadioButton("true");
		body.add(radio1true);
		g1.add(radio1true);
				
		//question two
		body.add(quiztwo);
	    radio2false = new JRadioButton("false");
		g2.add(radio2false);
		body.add(radio2false);
				
		radio2true = new JRadioButton("true");
		body.add(radio2true);
		g2.add(radio2true);
		
		//question three
		body.add(quizthree);
	    radio3false = new JRadioButton("false");
		g3.add(radio3false);
		body.add(radio3false);
				
		radio3true = new JRadioButton("true");
		body.add(radio3true);
		g3.add(radio3true);
		
		//question four
		body.add(quizfour);
		radio4false = new JRadioButton("false");
		g4.add(radio4false);
		body.add(radio4false);
		
	    radio4true = new JRadioButton("true");
		body.add(radio4true);
		g4.add(radio4true);
		return body;
	
	}
	
	public List<Answer> getAnswers() {
		List<Answer> answers=new ArrayList<Answer>();
		
		boolean ans1=false,ans2=false,ans3=false,ans4=false;
		if(radio1true.isSelected()) {
			ans1=true;
		}
		if(radio2true.isSelected()) {
			ans2=true;
		}
		if(radio3true.isSelected()) {
			ans3=true;
		}
		if(radio4true.isSelected()) {
			ans4=true;
		}
		answers.add(new Answer(quizzes.get(0).question, ans1));
		answers.add(new Answer(quizzes.get(1).question, ans2));
		answers.add(new Answer(quizzes.get(2).question, ans3));
		answers.add(new Answer(quizzes.get(3).question, ans4));

		
		return answers;
		
	}

}
