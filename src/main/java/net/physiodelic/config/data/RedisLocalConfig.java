package net.physiodelic.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

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
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        logger.log(Level.INFO, "Connection factory host before: " + jedisConnectionFactory.getHostName());
        jedisConnectionFactory.setHostName(redisHost);
        logger.log(Level.INFO, "Connection factory host after: " + jedisConnectionFactory.getHostName());
        return jedisConnectionFactory;
    }
}

// That's All Folks !!
