package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;
import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewerBean;

public interface hiringTeamService {
	
	String createTeam(hiringTeamBean htBean);
	
	String addReviewer(String tId, reviewerBean rvBean);
	
	boolean deleteReviewer(String id);
	
	List<hiringTeamBean> getAllHiringTeams();
	
	boolean deleteHiringTeam(String tId);
	
	boolean updateReviewer(reviewerBean rvBean);
	
	hiringTeamBean getSpecificHiringTeam(String tId);
	
	reviewerBean getSpecificReviewer(String rId);
	

}