package net.physiodelic.config.data;

import com.hazelcast.config.AttributeConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.IndexConfig;
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
    AttributeConfig mapAttributeConfig = new AttributeConfig()
        .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
        .setExtractorClassName(PrincipalNameExtractor.class.getName());

    Config config = new Config();

    config.getMapConfig("spring:session:sessions")
        .addAttributeConfig(mapAttributeConfig)
        .addIndexConfig(new IndexConfig(IndexConfig.DEFAULT_TYPE));
    return Hazelcast.newHazelcastInstance(config);
  }
}

// That's All Folks !!
