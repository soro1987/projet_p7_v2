package fr.soro;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import fr.soro.SecurityClient;
import fr.soro.utils.UserContext;



@Component
public class CustomInterceptor implements ClientHttpRequestInterceptor {
	@Autowired
	private HttpSession session;
	private UserContext userContext;	
	private SecurityClient securityClient;
	
	public CustomInterceptor(SecurityClient securityClient,UserContext userContext,HttpSession session) {
		this.securityClient =securityClient;
		this.userContext = userContext;
		this.session = session;
	}
	
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
//		    String token = this.securityClient.getToken(this.userContext.getUsername());			
			String token =(String) session.getAttribute( "tokenSession" );
		    request.getHeaders().add("Authorization", token);		    
		return execution.execute(request, body);
	}

	public CustomInterceptor() {
	}

	public CustomInterceptor(HttpSession session, UserContext userContext, SecurityClient securityClient) {
		this.session = session;
		this.userContext = userContext;
		this.securityClient = securityClient;
	}
}
