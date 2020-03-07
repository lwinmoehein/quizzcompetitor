import java.io.Serializable;

public class Question implements Serializable {
  String id,question;
  boolean ans;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
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
public Question(String id, String question, boolean ans) {
	super();
	this.id = id;
	this.question = question;
	this.ans = ans;
}
}
