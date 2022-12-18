package net.physiodelic.config.data;

import com.hazelcast.config.AttributeConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.session.hazelcast.Hazelcast4PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

/**
 * Created by joris on 25/04/17.
 * Simple class for local Hazelcast connection
 */
@Configuration
@EnableHazelcastHttpSession
@Profile({ "hazelcast-local" })
public class HazelcastLocalConfig {
  private final String SESSIONS_MAP_NAME = "spring-session-map-name";

  @Bean
  @SpringSessionHazelcastInstance
  public HazelcastInstance hazelcastInstance() {
    AttributeConfig mapAttributeConfig = new AttributeConfig()
        .setName(Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
        .setExtractorClassName(Hazelcast4PrincipalNameExtractor.class.getName());

    Config config = new Config();
    config.setClusterName("spring-session-cluster");

    config.getMapConfig(SESSIONS_MAP_NAME)
        .addAttributeConfig(mapAttributeConfig)
        .addIndexConfig(new IndexConfig(IndexType.HASH, Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_ATTRIBUTE));

    return Hazelcast.newHazelcastInstance(config);
  }
}

// That's All Folks !!
