package net.physiodelic.config.data;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.AttributeConfig;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import org.json.JSONObject;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;

/**
 * Created by joris on 22/04/17.
 * Cloud config for Hazelcast
 *
 * Todo: make this stuff actually work. Have not figured out how to work with
 * external Hazelcast properly.
 */
@Configuration
@Profile("hazelcast-cloud")
public class HazelcastCloudConfig extends AbstractCloudConfig {
  private String sysEnv = System.getenv("VCAP_SERVICES");
  private String url = parseAndGetUrl(sysEnv);

  @Bean
  public HazelcastInstance hazelcastInstance() {
    AttributeConfig mapAttributeConfig = new AttributeConfig()
        .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
        .setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());

    ClientConfig clientConfig = new ClientConfig();
    ClientNetworkConfig clientNetworkConfig = clientConfig.getNetworkConfig();
    clientNetworkConfig.addAddress(url).setSmartRouting(true);
    clientConfig.setNetworkConfig(clientNetworkConfig);

    HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
    hazelcastInstance.getConfig().getMapConfig("spring:session:sessions")
        .addAttributeConfig(mapAttributeConfig)
        .addIndexConfig(new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

    return hazelcastInstance;
  }

  private String parseAndGetUrl(String envVariable) {
    JSONObject jsonObject = new JSONObject(envVariable);
    return jsonObject.getString("host") + ":" + jsonObject.getString("port");
  }
}

// That's All Folks !!
