package net.physiodelic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * Created by joris on 25/03/17.
 * Simple class to create springSessionRepositoryFilter for Redis
 */
@Configuration
@Profile({"redis-cloud", "redis-local"})
@EnableRedisHttpSession
public class HttpRedisSessionConfig {

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}
