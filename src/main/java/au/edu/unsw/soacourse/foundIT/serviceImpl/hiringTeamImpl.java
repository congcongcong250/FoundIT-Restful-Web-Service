package au.edu.unsw.soacourse.foundIT.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.edu.unsw.soacourse.foundIT.DAO.applicationDAO;
import au.edu.unsw.soacourse.foundIT.DAO.hiringTeamDAO;
import au.edu.unsw.soacourse.foundIT.DAO.reviewDAO;
import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;
import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewerBean;
import au.edu.unsw.soacourse.foundIT.serviceApi.hiringTeamService;

public class hiringTeamImpl implements hiringTeamService {

	public String createTeam(hiringTeamBean team) {
		hiringTeamDAO teamDAO = new hiringTeamDAO();
		SecureRandom random = new SecureRandom();
		int newTeamID = random.nextInt(10000);
		team.settId("t" + Integer.toString(newTeamID));
		team.setSize("0");
		teamDAO.createTeam(team);
		return Integer.toString(newTeamID);
	}
	
	public String addReviewer(String tId, reviewerBean rvBean) {
		hiringTeamDAO teamDAO = new hiringTeamDAO();
		SecureRandom random = new SecureRandom();
		int newReviewerId = random.nextInt(10000);
		rvBean.setrId("r" + Integer.toString(newReviewerId));
		teamDAO.addReviewer(tId, rvBean);
		
		return Integer.toString(newReviewerId);
	}

	public List<hiringTeamBean> getAllHiringTeams() {
		hiringTeamDAO teamDAO = new hiringTeamDAO();
		List<hiringTeamBean> tempList = teamDAO.getAllHiringTeams();
		return tempList;
	}
	///////////////////////////////////////////////////////////////////////
	public boolean updateReviewer(reviewerBean rvBean) {
		String rId=rvBean.getrId();
		boolean flag=false;
		hiringTeamDAO rvDAO=new hiringTeamDAO();
		List<hiringTeamBean> tempList=rvDAO.getAllHiringTeams();
		List<reviewerBean> reviewerList = new ArrayList<reviewerBean>();
		for (int i = 0; i < tempList.size(); i++) {
			for (int j = 0; j < tempList.get(i).getReviewers().size(); j++) {
				reviewerList.add(tempList.get(i).getReviewers().get(j));
			}
		}
		//check the id whether exists
		for(int i=0;i<reviewerList.size();i++)
		{
			if(reviewerList.get(i).getrId().equals(rId))
				flag=true;
		}
		if(flag){
		rvDAO.updateReviewer(rvBean);
		return true;
		}
		
		return false;
	}
	///////////////////////////////////////////////////////////////////
	@Override
	public boolean deleteReviewer(String rId) {
		hiringTeamDAO teamDAO = new hiringTeamDAO();
		//System.out.println(rId);
		boolean flag = false;
		List<hiringTeamBean> tempList = teamDAO.getAllHiringTeams();
		List<reviewerBean> reviewerList = new ArrayList<reviewerBean>();
		for (int i = 0; i < tempList.size(); i++) {
			for (int j = 0; j < tempList.get(i).getReviewers().size(); j++) {
				reviewerList.add(tempList.get(i).getReviewers().get(j));
			}
		}
		/*for(int x = 0; x < reviewerList.size();x++){
		System.out.println(reviewerList.get(x).getPsw());
		// check the id whether exists
		}*/
		for (int i = 0; i < reviewerList.size(); i++) {
			if (reviewerList.get(i).getrId().equals(rId))
				flag = true;
		}
		if (flag) {
			teamDAO.deleteReviewer(rId);
			return true;
		}

		return false;
	}
	//////////////////////////////////////////////////////////////////
	@Override
	public boolean deleteHiringTeam(String id) {
		hiringTeamDAO teamDAO = new hiringTeamDAO();

		boolean flag = false;
		List<hiringTeamBean> tempList = teamDAO.getAllHiringTeams();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).gettId().equals(id))
				flag = true;
		}
		if (flag) {
			teamDAO.deleteTeam(id);
			return true;
		}

		return false;
	}

	///////////////////////////////////////////////////////////////////////////
	public reviewerBean getSpecificReviewer(String rId) {
		boolean flag = false;
		hiringTeamDAO htDAO = new hiringTeamDAO();
		List<hiringTeamBean> tempList = htDAO.getAllHiringTeams();
		List<reviewerBean> reviewerList = new ArrayList<reviewerBean>();
		reviewerBean resultBean = new reviewerBean();
		for (int i = 0; i < tempList.size(); i++) {
			for (int j = 0; j < tempList.get(i).getReviewers().size(); j++) {
				reviewerList.add(tempList.get(i).getReviewers().get(j));
			}
		}
		for (int i = 0; i < reviewerList.size(); i++) {
			if (reviewerList.get(i).getrId().equals(rId) && reviewerList.get(i).getrId() != null)
				flag = true;
		}
		System.out.println(reviewerList.size());
		if (flag) {

			Element element = (Element) htDAO.selectSingleReviewer(rId);
			// System.out.println(element.getElementsByTagName("rId").item(0).getTextContent());
			resultBean.setrId(element.getElementsByTagName("rId").item(0).getTextContent());
			resultBean.setPsw(element.getElementsByTagName("psw").item(0).getTextContent());
			resultBean.setSkill(element.getElementsByTagName("skill").item(0).getTextContent());
			return resultBean;
		}
		else
			return null;
	}

	/////////////////////////////////////////////////////////////////////
	public hiringTeamBean getSpecificHiringTeam(String tId) {
		boolean flag = false;
		hiringTeamDAO htDAO = new hiringTeamDAO();
		List<hiringTeamBean> tempList = htDAO.getAllHiringTeams();
		List<reviewerBean> reviewerList = new ArrayList<reviewerBean>();
		hiringTeamBean resultBean = new hiringTeamBean();

		// check the id whether exists
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).gettId().equals(tId) && tempList.get(i).gettId() != null)
				flag = true;
		}
		if (flag) {
			Element element = (Element) htDAO.selectSingleNode(tId);
			resultBean.settId(element.getElementsByTagName("tId").item(0).getTextContent());
			resultBean.setSize(element.getElementsByTagName("size").item(0).getTextContent());
			System.out.println(element.getElementsByTagName("reviewer").getLength());
			for (int i = 0; i < element.getElementsByTagName("reviewer").getLength(); i++) {
				reviewerBean rvBean = new reviewerBean();
				rvBean.setrId(element.getElementsByTagName("rId").item(i).getTextContent());
				rvBean.setPsw(element.getElementsByTagName("psw").item(i).getTextContent());
				rvBean.setSkill(element.getElementsByTagName("skill").item(i).getTextContent());
				reviewerList.add(rvBean);
			}
			resultBean.setReviewers(reviewerList);
			return resultBean;
		} else {
			return null;
		}

	}

}
