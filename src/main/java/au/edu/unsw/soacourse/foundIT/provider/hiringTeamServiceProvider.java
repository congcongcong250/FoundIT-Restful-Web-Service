package au.edu.unsw.soacourse.foundIT.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.w3c.dom.Element;

import au.edu.unsw.soacourse.foundIT.DAO.applicationDAO;
import au.edu.unsw.soacourse.foundIT.modeler.applicationBean;
import au.edu.unsw.soacourse.foundIT.modeler.hiringTeamBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewBean;
import au.edu.unsw.soacourse.foundIT.modeler.reviewerBean;
import au.edu.unsw.soacourse.foundIT.serviceImpl.hiringTeamImpl;
import au.edu.unsw.soacourse.foundIT.serviceImpl.reviewImpl;

;

@Path("hiringTeams")
public class hiringTeamServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;
	public hiringTeamImpl teamImpl = new hiringTeamImpl();

	@DELETE
	@Path("deleteTeam/{tId}")
	public void delete(@PathParam("tId") String tId) {
		// System.out.println("222123123");
		boolean flag = teamImpl.deleteHiringTeam(tId);
		if (!flag)
			throw new RuntimeException("DELETE: team with " + tId
					+ " not found");

	}

	@DELETE
	@Path("deleteReviewer/{rId}")
	public void deleteReviewer(@PathParam("rId") String rId) {
		// System.out.println("222123123");
		boolean flag = teamImpl.deleteReviewer(rId);
		if (!flag)
			throw new RuntimeException("DELETE: reviewer with " + rId
					+ " not found");

	}

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<hiringTeamBean> getAllHiringTeams() {
		// System.out.println("11111111");
		List<hiringTeamBean> bs = new ArrayList<hiringTeamBean>();
		bs = teamImpl.getAllHiringTeams();
		// System.out.println(bs.get(0).getId());
		return bs;

	}

	@GET
	@Path("hiringTeam/{tId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public hiringTeamBean getHiringTeam(@PathParam("tId") String tId) {
		hiringTeamBean b = teamImpl.getSpecificHiringTeam(tId);
		if (b == null) {
			throw new RuntimeException("GET: hiringteam with " + tId
					+ " not found");
		}
		return b;
	}

	@GET
	@Path("reviewer/{rId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public reviewerBean getReviewer(@PathParam("rId") String rId) {
		reviewerBean b = teamImpl.getSpecificReviewer(rId);
		if (b == null) {
			return b;
			// throw new RuntimeException("GET: application with " + rId +
			// " not found");
		}
		return b;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newTeam() throws IOException {

		hiringTeamBean hiringTeamBean = new hiringTeamBean();
		return teamImpl.createTeam(hiringTeamBean);
	}

	@POST
	@Path("addReviewer/{tId}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createReviewer(@PathParam("tId") String tId,
			@FormParam("psw") String psw, @FormParam("skill") String skill)
			throws IOException {

		reviewerBean rvBean = new reviewerBean();
		if (psw != null) {
			rvBean.setPsw(psw);
		}
		if (skill != null) {
			rvBean.setSkill(skill);

		}
		// System.out.println(rvBean.getSkill());
		return teamImpl.addReviewer(tId, rvBean);
	}

	@PUT
	@Path("update")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createReviewer(reviewerBean rvBean) throws Exception {

		return putAndGetResponse(rvBean);

	}

	private Response putAndGetResponse(reviewerBean rvBean) {
		javax.ws.rs.core.Response res;
		boolean flag = teamImpl.updateReviewer(rvBean);
		if (flag)
			res = javax.ws.rs.core.Response.created(uri.getAbsolutePath())
					.build();
		else {
			res = javax.ws.rs.core.Response.noContent().build();
		}
		return res;

	}

}
