package unpsjb.fipm.gisfpp.controladores.integracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestImplementation {
	
	@Autowired 
	RestTemplate restTemplate;

	/**
	 * 
	 * @param url
	 * @param format
	 * @return response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HttpEntity<String> get(String url, String format){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", format);		
		HttpEntity entity = new HttpEntity(headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	}
}
