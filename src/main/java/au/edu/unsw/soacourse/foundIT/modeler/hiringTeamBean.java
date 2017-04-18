package au.edu.unsw.soacourse.foundIT.modeler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "team")
@XmlType(propOrder = { "tId", "size","reviewers"})
public class hiringTeamBean {

	String tId;
	String size;
	List<reviewerBean> reviewers = new ArrayList<reviewerBean>();
	
	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	/*public String getrId() {
		return reviewers.get(0).getrId();
	}
	
	public void setrId(String rId) {
		this.reviewers.get(0).rId = rId;
	}
	
	public String getPsw() {
		return reviewers.get(1).getPsw();
	}
	
	public void setPsw(String psw) {
		this.reviewers.get(1).psw = psw;
	}
	
	public String getSkill() {
		return reviewers.get(2).getSkill();
	}
	
	public void setSkill(String skill) {
		this.reviewers.get(2).skill = skill;
	}*/
	@XmlElementWrapper(name = "reviewers")
	@XmlElement(name = "reviewer")
	public void setReviewers (List<reviewerBean> reviewers){
		this.reviewers = reviewers;
	}
	
	public List<reviewerBean> getReviewers(){
		return reviewers;
	}
}
