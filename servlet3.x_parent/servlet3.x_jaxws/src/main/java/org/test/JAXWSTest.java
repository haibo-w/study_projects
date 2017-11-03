package org.test;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class JAXWSTest implements IJaxWsTest {
	
	/* (non-Javadoc)
	 * @see org.test.IJaxWsTest#sayHello(java.lang.String)
	 */
	@WebMethod
	@Override
	public String sayHello(String hello){
		//System.out.println(hello + " world !");
		return "Hello " + hello;
	}

}
