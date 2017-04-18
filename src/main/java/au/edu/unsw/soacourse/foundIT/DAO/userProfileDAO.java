package au.edu.unsw.soacourse.foundIT.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
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


import au.edu.unsw.soacourse.foundIT.modeler.userProfileBean;

public class userProfileDAO {

	// String filepath=this.getClass().getResource("userProfile").getPath();
	private static Document xmldoc;
	public static String path = "F:/java/RestfulFoundITService/RestfulFoundITService/src/main/java/au/edu/unsw/soacourse/foundIT/DAO/userProfile.xml";

	public userProfileDAO() {
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

	public void deleteUser(String id) {

		Element user = (Element) selectSingleNode(id);

		xmldoc.getDocumentElement().removeChild(user);
		TransformerFactory factory = TransformerFactory.newInstance();
		try {
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(
					path)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void createUser(userProfileBean userPro) {

		// System.out.println(path);
		try {

			Element root = xmldoc.getDocumentElement();

			Element user = xmldoc.createElement("user");
			// user.setAttribute("id", "003");
			Element id = xmldoc.createElement("id");
			id.setTextContent(userPro.getId());
			user.appendChild(id);

			
			Element fullname = xmldoc.createElement("fullname");
			fullname.setTextContent(userPro.getFullname());
			user.appendChild(fullname);
			
			Element curPosition = xmldoc.createElement("curPosition");
			curPosition.setTextContent(userPro.getCurPosition());
			user.appendChild(curPosition);
			
			Element education = xmldoc.createElement("education");
			education.setTextContent(userPro.getEducation());
			user.appendChild(education);

			Element skill = xmldoc.createElement("skill");
			skill.setTextContent(userPro.getSkill());
			user.appendChild(skill);

			Element exp = xmldoc.createElement("exp");
			exp.setTextContent(userPro.getExperience());
			user.appendChild(exp);

			root.appendChild(user);
			// xmldoc.setTextContent("dddddd");
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			DOMSource source = new DOMSource(xmldoc);
			PrintWriter pw = new PrintWriter(new FileOutputStream(path));
			StreamResult result = new StreamResult(pw);
			former.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Node selectSingleNode(String id) {
		Node result = null;
		// userProfileBean result2=new userProfileBean();
		Element root = xmldoc.getDocumentElement();
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String express = "//users/user[id='" + id + "']";
		try {
			result = (Node) xpath.evaluate(express, root, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public void updateUser(userProfileBean userPro) {
		// initDom(path);
		try {

			// Element root = xmldoc.getDocumentElement();
			String id = userPro.getId();
			System.out.println(id);

			Element per = (Element) selectSingleNode(id);
			// if(userPro.getDetail()!=nul)
			per.getElementsByTagName("exp").item(0)
					.setTextContent(userPro.getExperience());

			per.getElementsByTagName("skill").item(0)
					.setTextContent(userPro.getSkill());

			per.getElementsByTagName("fullname").item(0)
					.setTextContent(userPro.getFullname());
			
			per.getElementsByTagName("curPosition").item(0)
			.setTextContent(userPro.getCurPosition());
			
			per.getElementsByTagName("education").item(0)
			.setTextContent(userPro.getEducation());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(
					path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public List<userProfileBean> getAllUsers() {
		// initDom(path);
		NodeList sonlist = null;
		List<userProfileBean> resultList = new ArrayList<userProfileBean>();
		// String path=this.getClass().getResource("Users.xml").getPath();

		try {
			sonlist = ((org.w3c.dom.Document) xmldoc)
					.getElementsByTagName("user");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < sonlist.getLength(); i++) {
			Element son = (Element) sonlist.item(i);
			userProfileBean bean = new userProfileBean();
			for (Node node = son.getFirstChild(); node != null; node = node
					.getNextSibling())
				// int counter=0;
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String name = node.getNodeName();
					System.out.println(name);
					if (name != null) {
						if (name.equals("id")) {
							bean.setId(node.getFirstChild().getNodeValue());
						}
						if (name.equals("fullname")) {
							if (node.getFirstChild() != null) {
								bean.setFullname(node.getFirstChild()
										.getNodeValue());
							} else {
								bean.setFullname(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("curPosition")) {
							if (node.getFirstChild() != null) {
								bean.setCurPosition(node.getFirstChild()
										.getNodeValue());
							} else {
								bean.setCurPosition(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("education")) {
							if (node.getFirstChild() != null) {
								bean.setEducation(node.getFirstChild()
										.getNodeValue());
							} else {
								bean.setEducation(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("skill")) {
							if (node.getFirstChild() != null) {
								bean.setSkill(node.getFirstChild()
										.getNodeValue());
							} else {

								bean.setSkill(".....incomplete");
							}
							// counter++;
						}
						if (name.equals("exp")) {
							if (node.getFirstChild() != null) {
								bean.setExperience(node.getFirstChild()
										.getNodeValue());
							} else {
								bean.setExperience(".....incomplete");
							}

						}
					}
				}
			resultList.add(bean);
		}

		return resultList;
	}

	public Node queryUser(String id) {
		Node user = null;
		try {

			// Element root = xmldoc.getDocumentElement();

			user = selectSingleNode(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Node node = user.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String name = node.getNodeName();
				String value = node.getFirstChild().getNodeValue();
				System.out.println(name + " : " + value);
			}
		}

		return user;
	}

}
