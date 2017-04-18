package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import au.edu.unsw.soacourse.foundIT.DAO.jobPostingDAO;
import au.edu.unsw.soacourse.foundIT.modeler.jobPostingBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.jobPostingService;

public class jobPostingImpl implements jobPostingService {

	@Override
	public String createPosting(jobPostingBean jobPost) {
		jobPostingDAO jobPosting = new jobPostingDAO();
		SecureRandom random = new SecureRandom();
		int newPostingID = random.nextInt(10000);
		jobPost.setId(Integer.toString(newPostingID));
		jobPosting.createPosting(jobPost);
		return Integer.toString(newPostingID);
	}

	@Override
	public boolean deletePosting(String id) {
		jobPostingDAO jobPostingDAO = new jobPostingDAO();

		boolean flag = false;
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(id))
				flag = true;
		}
		if (flag) {
			jobPostingDAO.deletePosting(id);
			return true;
		}

		return false;

	}

	@Override
	public List<jobPostingBean> getAllPostings() {
		jobPostingDAO jobPostingDAO = new jobPostingDAO();
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();
		return tempList;
	}

	public boolean updatePosting(jobPostingBean jobPost) {

		String id = jobPost.getId();
		boolean flag = false;
		jobPostingDAO jobPostingDAO = new jobPostingDAO();
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(id))
				flag = true;
		}
		if (flag) {
			jobPostingDAO.updatePosting(jobPost);
			return true;
		}

		return false;
	}

	@Override
	public jobPostingBean getSpecificPosting(String id) {
		boolean flag = false;
		jobPostingDAO jobPostingDAO = new jobPostingDAO();
		List<jobPostingBean> tempList = jobPostingDAO.getAllPostings();
		jobPostingBean resultBean = new jobPostingBean();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getId().equals(id)
					&& tempList.get(i).getId() != null)
				flag = true;
		}
		if (flag) {
			Element element = (Element) jobPostingDAO.selectSingleNode(id);
			resultBean.setId(element.getElementsByTagName("id").item(0)
					.getTextContent());
			resultBean.setComLink(element.getElementsByTagName("comLink")
					.item(0).getTextContent());
			resultBean.setSalaryRate(element.getElementsByTagName("salaryRate")
					.item(0).getTextContent());
			resultBean.setPositionType(element
					.getElementsByTagName("positionType").item(0)
					.getTextContent());
			resultBean.setLocation(element.getElementsByTagName("location")
					.item(0).getTextContent());
			resultBean.setDescription(element
					.getElementsByTagName("description").item(0)
					.getTextContent());
			resultBean.setStatus(element.getElementsByTagName("status").item(0)
					.getTextContent());
			return resultBean;
		} else {
			return null;
		}

	}

	public List<jobPostingBean> searchJobByCompany(String company) {
		List<jobPostingBean> resuListBean = new ArrayList<jobPostingBean>();
		jobPostingDAO jobPosting = new jobPostingDAO();
		List<jobPostingBean> allJobList = jobPosting.getAllPostings();

		for (int i = 0; i < allJobList.size(); i++) {

			jobPostingBean JB = allJobList.get(i);
			String comlink = JB.getComLink();

			if (!comlink.toLowerCase().equals(company))
				continue;

			resuListBean.add(JB);
		}
		return resuListBean;

	}

	public List<jobPostingBean> searchJobByKeyWord(String param_salaryRate,
			String param_positionType, String param_location,
			String param_status, String param_keyword) {
		List<jobPostingBean> resuListBean = new ArrayList<jobPostingBean>();
		jobPostingDAO jobPosting = new jobPostingDAO();
		List<jobPostingBean> allJobList = jobPosting.getAllPostings();

		for (int i = 0; i < allJobList.size(); i++) {
			System.out.println("RestPostingSearch");
			jobPostingBean JB = allJobList.get(i);

			String position = JB.getPositionType();

			String status = JB.getStatus();
			String salary = JB.getSalaryRate();
			String location = JB.getLocation();
			String des = JB.getDescription();
			String comlink = JB.getComLink();
			if (!param_salaryRate.isEmpty() && salary != null) {
				if (!salary.equals(param_salaryRate))
					continue;
			}
			if (!param_positionType.isEmpty() && position != null) {
				if (!position.toLowerCase().equals(param_positionType))
					continue;
			}
			if (!param_status.isEmpty() && status != null) {
				if (!status.toLowerCase().equals(param_status))
					continue;
			}
			if (!param_location.isEmpty() && location != null) {
				if (!location.toLowerCase().contains(param_location))
					continue;
			}
			if (!param_keyword.isEmpty()) {
				boolean flag = false;
				if (des != null)
					if (des.toLowerCase().contains(param_keyword))
						flag = true;

				if (location != null)
					if (location.toLowerCase().contains(param_keyword))
						flag = true;

				if (position != null)
					if (position.toLowerCase().contains(param_keyword))
						flag = true;

				if (comlink != null)
					if (comlink.toLowerCase().contains(param_keyword))
						flag = true;

				if (!flag)
					continue;
			}
			resuListBean.add(JB);
		}
		return resuListBean;

	}

}
