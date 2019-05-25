package net.physiodelic.config.data;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;

/**
 * Created by joris on 25/04/17.
 * Simple class for local Hazelcast connection
 */
@Configuration
@Profile({"hazelcast-local"})
public class HazelcastLocalConfig {

  @Bean
  public HazelcastInstance hazelcastInstance() {
    MapAttributeConfig mapAttributeConfig = new MapAttributeConfig()
        .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
        .setExtractor(PrincipalNameExtractor.class.getName());

    Config config = new Config();

    config.getMapConfig("spring:session:sessions")
        .addMapAttributeConfig(mapAttributeConfig)
        .addMapIndexConfig(new MapIndexConfig(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
    return Hazelcast.newHazelcastInstance(config);
  }
}

// That's All Folks !!
