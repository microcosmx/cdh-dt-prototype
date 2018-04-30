package hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

@RestController
public class HelloController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AsyncRestTemplate asyncRestTemplate;

	@RequestMapping("/hello1")
	public String hello1(@RequestParam(value = "cal", defaultValue = "50") String cal) throws Exception{

		double cal2 = Math.log10(Double.valueOf(cal)) * 50;
		log.info(String.valueOf(cal2));

		double rand = Math.random();
		Value value = new Value();

		ListenableFuture<ResponseEntity<String>> future = asyncRestTemplate
				.getForEntity("http://rest-service-end:16602/test?cal=" + cal2, String.class);
				
		// waits for the result
		ResponseEntity<String> entity = future.get();
		// prints body source code for the given URL
		System.out.println(entity.getBody());

		return entity.getBody();
	}
}
