package au.edu.unsw.soacourse.foundIT.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.jws.WebParam.Mode;
import javax.print.attribute.standard.RequestingUserName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;
import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewerBean;

public class hiringTeamDAO {

	// String filepath=this.getClass().getResource("userProfile").getPath();
	private static Document xmldoc;
	public static String path = "F:/java/RestfulFoundITService/RestfulFoundITService/src/main/java/au/edu/unsw/soacourse/foundIT/DAO/hiringTeam.xml";

	public hiringTeamDAO() {
		// this.getClass().getResource("userProfile.xml").getPath();
		// System.out.println(path);
		// path = this.getClass().getResource("userProfile.xml").getPath();
		// System.out.println(this.getClass().getResource("userProfile.xml").getPath());
		initDom(path);

	}

	public void initDom(String path) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			// new FileInputStream(new File(xmlFileName))
			xmldoc = db.parse(new FileInputStream(new File(path)));

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void createTeam(hiringTeamBean HTbean) {

		try {
			Element root = xmldoc.getDocumentElement();

			Element team = xmldoc.createElement("team");
			// user.setAttribute("id", "003");
			Element tId = xmldoc.createElement("tId");
			tId.setTextContent(HTbean.gettId());
			team.appendChild(tId);

			Element size = xmldoc.createElement("size");
			size.setTextContent(HTbean.getSize());
			team.appendChild(size);
			
			Element reviewers = xmldoc.createElement("reviewers");
			reviewers.setTextContent(null);
			team.appendChild(reviewers);

			root.appendChild(team);
			// ±£´æ
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addReviewer(String tId, reviewerBean rvBean) {
		Node result = null; //
		hiringTeamBean result2 = new hiringTeamBean();
		Element root = xmldoc.getDocumentElement();
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String express = "//teams/team[tId='" + tId + "']/reviewers";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
			// System.out.println(result.getFirstChild().getNextSibling().getNodeValue());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		try {
			Element reviewers = (Element) result;
			Node team = selectSingleNode(tId);
			Node nd = team.getFirstChild();
			while(!nd.getNodeName().equals("size"))
				nd = nd.getNextSibling();
			
			
			nd.getFirstChild().setTextContent(Integer.toString(Integer.parseInt(nd.getFirstChild().getTextContent()) + 1));
			
			Element reviewer = xmldoc.createElement("reviewer");
			// user.setAttribute("id", "003");
			Element rId = xmldoc.createElement("rId");
			rId.setTextContent(rvBean.getrId());
			reviewer.appendChild(rId);

			Element psw = xmldoc.createElement("psw");
			psw.setTextContent(rvBean.getPsw());
			reviewer.appendChild(psw);
			
			Element skill = xmldoc.createElement("skill");
			skill.setTextContent(rvBean.getSkill());
			reviewer.appendChild(skill);

			reviewers.appendChild(reviewer);
			// ±£´æ
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void updateReviewer(reviewerBean rvBean) {
		// initDom(path);
		try {

			// Element root = xmldoc.getDocumentElement();
			String rId = rvBean.getrId();
			// System.out.println(id);

			Element per = (Element) selectSingleReviewer(rId);
			// if(userPro.getDetail()!=nul)
			per.getElementsByTagName("rId").item(0).setTextContent(rvBean.getrId());

			per.getElementsByTagName("psw").item(0).setTextContent(rvBean.getPsw());

			per.getElementsByTagName("skill").item(0).setTextContent(rvBean.getSkill());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deleteTeam(String id) {

		Element team = (Element) selectSingleNode(id);
		xmldoc.getDocumentElement().removeChild(team);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	public void deleteReviewer(String rId) {

		Node reviewer = selectSingleReviewer(rId);
		reviewer.getParentNode().removeChild(reviewer);

		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public Node selectSingleReviewer(String rId) {
		Node result = null; //
		hiringTeamBean result2 = new hiringTeamBean();
		Element root = xmldoc.getDocumentElement();
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		// System.out.println(rId);
		String express = "//teams/team/reviewers/reviewer[rId='" + rId + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}
/////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public Node selectSingleNode(String id) {
		Node result = null; //
		hiringTeamBean result2 = new hiringTeamBean();
		Element root = xmldoc.getDocumentElement();
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String express = "//teams/team[tId='" + id + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
			// System.out.println(result.getFirstChild().getNextSibling().getNodeValue());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public List<hiringTeamBean> getAllHiringTeams() {
		NodeList sonList = null;
		List<hiringTeamBean> resultList = new ArrayList<hiringTeamBean>();
		// List<hiringTeamBean> resultList = new ArrayList<hiringTeamBean>();
		try {
			sonList = ((org.w3c.dom.Document) xmldoc).getElementsByTagName("team");
			// System.out.println(sonList.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j = 0; j < sonList.getLength(); j++) {
			// System.out.println(sonList.getLength());
			List<reviewerBean> reviewerList = new ArrayList<reviewerBean>();
			// NodeList grandsonList = selectReviewers();
			NodeList grandsonList = null;

			Element son = (Element) sonList.item(j);
			Node reviewer = son.getFirstChild();
			while (reviewer != null && reviewer.getNodeName() != "reviewers")
				reviewer = reviewer.getNextSibling();

			if (reviewer != null) {
				reviewer = reviewer.getFirstChild();
				//System.out.println(reviewer.getNodeName());
				while (reviewer != null) {
					if (reviewer.getNodeName() != "reviewer") {
						reviewer = reviewer.getNextSibling();
						continue;
					}
					Element grandson = (Element) reviewer;
					reviewerBean rvBean = new reviewerBean();

					for (Node node2 = grandson.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
						if (node2.getNodeType() == Node.ELEMENT_NODE) {

							String name2 = node2.getNodeName();
							// System.out.println(name2);
							if (name2 != null) {
								if (name2.equals("rId")) {
									rvBean.setrId(node2.getFirstChild().getNodeValue());
								}
								if (name2.equals("psw")) {
									if (node2.getFirstChild() != null) {
										rvBean.setPsw(node2.getFirstChild().getNodeValue());
									} else {
										rvBean.setPsw(".....incomplete");
									}
								}
								if (name2.equals("skill")) {
									if (node2.getFirstChild() != null) {
										rvBean.setSkill(node2.getFirstChild().getNodeValue());
									} else {
										rvBean.setSkill(".....incomplete");
									}
									reviewerList.add(rvBean);
								}

							}
						}
					}
					reviewer = reviewer.getNextSibling();
				}
			}

			hiringTeamBean htBean = new hiringTeamBean();
			for (Node node = son.getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String name = node.getNodeName();
					if (name != null) {
						if (name.equals("tId")) {
							htBean.settId(node.getFirstChild().getNodeValue());
						}
						if (name.equals("size")) {
							htBean.setSize(node.getFirstChild().getNodeValue());
						}
						// System.out.println(reviewerList.size());
						if (name.equals("reviewers")) {
							htBean.setReviewers(reviewerList);
						}
						// else
						// resultList.add(htBean);
					}
				}
			}
			resultList.add(htBean);
		}
		return resultList;
		// return null;
	}
}
