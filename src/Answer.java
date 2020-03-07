import java.io.Serializable;

public class Answer implements Serializable {
    String id;
    boolean ans;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Answer(String id, boolean ans) {
		super();
		this.id = id;
		this.ans = ans;
	}
	public boolean isAns() {
		return ans;
	}
	public void setAns(boolean ans) {
		this.ans = ans;
	}
}
