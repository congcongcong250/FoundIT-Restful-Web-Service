package au.edu.unsw.soacourse.foundIT.serviceApi;

import java.util.List;

import org.w3c.dom.Node;

import au.edu.unsw.soacourse.foundIT.modeler.jobPostingBean;

public interface jobPostingService {
	
	String createPosting(jobPostingBean jobPost);
	
	boolean deletePosting(String id);
	
	List<jobPostingBean> getAllPostings();
	
	boolean updatePosting(jobPostingBean jobPost);
	
	jobPostingBean getSpecificPosting(String id);
	
	public List<jobPostingBean> searchJobByKeyWord(String param_salaryRate,
			String param_positionType, String param_location, String param_status, String param_keyword);


}