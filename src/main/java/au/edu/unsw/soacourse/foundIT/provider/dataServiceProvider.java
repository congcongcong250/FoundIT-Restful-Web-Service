package au.edu.unsw.soacourse.foundIT.provider;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.sf.saxon.xqj.SaxonXQDataSource;

@Path("jobalerts")
public class dataServiceProvider {

	@Context
	Request request;
	@Context
	UriInfo uri;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getJobsbykeyword(@QueryParam("keyword") String keyword,
			@QueryParam("sort") String sort,
			@HeaderParam("shortKey") String shortKey,
			@HeaderParam("securityKey") String securityKey) {

		System.out.print(shortKey+"   "+securityKey);
		if (!shortKey.contains("candidate")
				|| !securityKey.contains("I am found IT")) {
			return "Wrong Authorization";
		}
		String head = "file:///";
		String path = "F:/java/RestfulFoundITService/RestfulFoundITService/src/main/java/au/edu/unsw/soacourse/foundIT/DAO/jobfeed.xml";
		String feedpath = head + path;
		String res = "<?xml version=\"1.0\" encoding=\"utf-8\"?><rss version=\"2.0\">";
		res = "";
		String query = "xquery version \"1.0\";   for $job in doc(\""
				+ feedpath
				+ "\")//job where contains(lower-case($job/title/text()),lower-case(\"";
		query += keyword;
		query += "\")) or contains(lower-case($job/description/text()),lower-case(\""
				+ keyword + "\"))";
		if (sort != null) {
			query += " order by $job/" + sort + "/text() ";
		}
		query += "  return <item> <title>{ $job//title/text() } </title> <description>{$job//description/text()} </description> <link>{ $job//link/text()} </link> </item> ";

		try {
			XQDataSource ds = new SaxonXQDataSource();
			XQConnection conn = ds.getConnection();
			XQPreparedExpression exp = conn.prepareExpression(query);
			XQResultSequence result = exp.executeQuery();
			String next = "";
			while (result.next()) {
				next = result.getItemAsString(null);

				res += next;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;

	}

}
