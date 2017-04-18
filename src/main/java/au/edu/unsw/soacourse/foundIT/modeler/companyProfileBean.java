package au.edu.unsw.soacourse.foundIT.modeler;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "profileID", "detail", "webSite", "industryType"
		, "address", "headQuarterAdd", "phone"})
@XmlRootElement(name = "company")
public class companyProfileBean {
	// _profileId, company details

	String profileID;
	String detail;
    String webSite;
    String industryType;
    String address;
    String headQuarterAdd;
    String phone;
	public String getProfileID() {
		return profileID;
	}
	public void setProfileID(String profileID) {
		this.profileID = profileID;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadQuarterAdd() {
		return headQuarterAdd;
	}
	public void setHeadQuarterAdd(String headQuarterAdd) {
		this.headQuarterAdd = headQuarterAdd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
