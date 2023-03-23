package net.physiodelic.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by joris on 17/04/17.
 * Local configuration for Redis
 */
@Configuration
@Profile("redis-local")
public class RedisLocalConfig {
  private static final Logger logger = Logger.getLogger(RedisLocalConfig.class.toString());

  @Value("${spring.redis.host}")
  private String redisHost;

  @Bean
  public RedisConnectionFactory redisConnection() {
    RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
    standaloneConfiguration.setHostName(redisHost);

    LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder().build();
    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(standaloneConfiguration,
        clientConfiguration);
    logger.log(Level.INFO, "Connection factory host: " + lettuceConnectionFactory.getHostName());
    return lettuceConnectionFactory;
  }
}

// That's All Folks !!
