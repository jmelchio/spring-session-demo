package net.physiodelic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Created by joris on 01/05/17.
 * Simple class to create springSessionRepositoryFilter for Redis
 */
@Configuration
@Profile({"hazelcast-cloud", "hazelcast-local"})
@EnableHazelcastHttpSession
public class HttpHazelcastSessionConfig {

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}
