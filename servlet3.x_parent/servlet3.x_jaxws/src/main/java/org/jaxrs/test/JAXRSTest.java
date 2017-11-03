package org.jaxrs.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
@Path("rstest")
public class JAXRSTest {
	@GET
	@Produces("text/html")
	public String getHtml(){
		return "<html lang=\"en\"><body><h1>Hello,,,,,,, World __fse_!!!!!!lisef</body></h1></html>";
	}

    public static void main(String [] args){
        System.out.print("a");
    }
}
