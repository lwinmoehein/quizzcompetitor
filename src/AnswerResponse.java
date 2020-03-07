import java.io.Serializable;

public class AnswerResponse implements Serializable{
   String userone,usertwo;
   int useronemark,usertwomark;
public String getUserone() {
	return userone;
}
public void setUserone(String userone) {
	this.userone = userone;
}
public String getUsertwo() {
	return usertwo;
}
public AnswerResponse(String userone, String usertwo, int useronemark, int usertwomark) {
	super();
	this.userone = userone;
	this.usertwo = usertwo;
	this.useronemark = useronemark;
	this.usertwomark = usertwomark;
}
public void setUsertwo(String usertwo) {
	this.usertwo = usertwo;
}
public int getUseronemark() {
	return useronemark;
}
public void setUseronemark(int useronemark) {
	this.useronemark = useronemark;
}
public int getUsertwomark() {
	return usertwomark;
}
public void setUsertwomark(int usertwomark) {
	this.usertwomark = usertwomark;
}
}
