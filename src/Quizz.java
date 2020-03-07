import java.io.Serializable;

public class Quizz implements Serializable{
   String question;
   boolean ans;
public Quizz(String question, boolean ans) {
	super();
	this.question = question;
	this.ans = ans;
}
public String getQuestion() {
	return question;
}
public void setQuestion(String question) {
	this.question = question;
}
public boolean isAns() {
	return ans;
}
public void setAns(boolean ans) {
	this.ans = ans;
}
}
