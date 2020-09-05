package pe.edu.vallegrande.congnitiveface.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import pe.edu.vallegrande.congnitiveface.config.FaceEnv;
import pe.edu.vallegrande.congnitiveface.model.FaceModel;

@RestController
public class FaceController {

	@Autowired
	private FaceEnv faceEnv;

	@Value("${sample.url}")
	private String sampleUrl;
	@Value("${face.attributes}")
	private String faceAttributes;

	@RequestMapping("/detect")
	public String check(@ModelAttribute FaceModel model) {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Form values take precedence
		String key = model.getKey() != null ? model.getKey() : faceEnv.getKey();
		String url = model.getUrl() != null ? model.getUrl() : sampleUrl;
		String attributes = model.getAttributes() != null ? model.getAttributes() : faceAttributes;

		headers.set("Ocp-Apim-Subscription-Key", key);

		String body = "{ \"url\": \"" + url + "\" }";

		HttpEntity<String> entity = new HttpEntity<String>(body, headers);

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https")
				.host("eastus.api.cognitive.microsoft.com").path("/face/v1.0/detect")
				.query("returnFaceAttributes={keyword}").buildAndExpand(attributes);

		ResponseEntity<String> result = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, entity,
				String.class);

		return result.getBody();

	}

}

