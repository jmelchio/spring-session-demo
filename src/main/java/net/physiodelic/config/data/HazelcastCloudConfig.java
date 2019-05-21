package net.physiodelic.config.data;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.HazelcastInstance;
import org.json.JSONObject;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;

/**
 * Created by joris on 22/04/17.
 * Cloud config for Hazelcast
 *
 * Todo: make this stuff actually work. Have not figured out how to work with external Hazelcast properly.
 */
@Configuration
@Profile("hazelcast-cloud")
public class HazelcastCloudConfig extends AbstractCloudConfig {
    private String sysEnv = System.getenv("VCAP_SERVICES");
    private String url = parseAndGetUrl(sysEnv);

    @Bean
    public HazelcastInstance hazelcastInstance() {
        MapAttributeConfig mapAttributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());

        ClientConfig clientConfig = new ClientConfig();
        ClientNetworkConfig clientNetworkConfig = clientConfig.getNetworkConfig();
        clientNetworkConfig.addAddress(url).setSmartRouting(true);
        clientConfig.setNetworkConfig(clientNetworkConfig);

        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        hazelcastInstance.getConfig().getMapConfig("spring:session:sessions")
                .addMapAttributeConfig(mapAttributeConfig)
                .addMapIndexConfig(new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
        return hazelcastInstance;
    }

    private String parseAndGetUrl(String envVariable) {
        JSONObject jsonObject = new JSONObject(envVariable);
        return jsonObject.getString("host") + ":" + jsonObject.getString("port");
    }
}

// That's All Folks !!
