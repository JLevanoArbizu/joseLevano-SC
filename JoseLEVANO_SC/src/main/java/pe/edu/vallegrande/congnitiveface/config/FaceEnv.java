package pe.edu.vallegrande.congnitiveface.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.pivotal.labs.cfenv.CloudFoundryEnvironment;
import io.pivotal.labs.cfenv.CloudFoundryEnvironmentException;
import io.pivotal.labs.cfenv.CloudFoundryService;

@Configuration
public class FaceEnv {

	@Value("${subscription.key}")
	private String subscriptionKey;

	public String getKey() {
		return getKeyCF() != null ? getKeyCF() : subscriptionKey;
	}

	@Profile("cloud")
	private String getKeyCF() {

		CloudFoundryEnvironment environment;
		try {
			environment = new CloudFoundryEnvironment(System::getenv);
		} catch (CloudFoundryEnvironmentException e) {
			return null;
		}
		// CF UPSI needs to be named as "cognitive"
		CloudFoundryService service = environment.getService("cognitive");

		return (String) service.getCredentials().get("key");
	}
}
