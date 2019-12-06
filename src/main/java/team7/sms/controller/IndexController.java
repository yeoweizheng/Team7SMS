package team7.sms.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;

@Controller
public class IndexController {
	@RequestMapping("/")
	public String index() {
		return "redirect:/Home/";
	}
	
	
	@RequestMapping(value = "/logo", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
		HttpHeaders headers = new HttpHeaders(); 
		ServletContext context = getServletContext(); 
		InputStream in = context.getResourceAsStream("/images/logo_v1.jpg"); 
	    byte[] media = IOUtils.toByteArray(in);
	    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
	  
	    ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK); 
	    return responseEntity; 
	}
  
    private ServletContext getServletContext() { // TODO Auto-generated method
    	return null; 
    }
 
	
}
