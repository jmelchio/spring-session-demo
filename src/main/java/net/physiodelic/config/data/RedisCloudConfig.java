package net.physiodelic.config.data;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by joris on 17/04/17.
 * Cloud configuration for Redis
 */
@Configuration
@Profile("redis-cloud")
public class RedisCloudConfig extends AbstractCloudConfig {

  @Bean
  public RedisConnectionFactory redisConnection() {
    return connectionFactory().redisConnectionFactory();
  }
}

// That's All Folks !!
