package net.physiodelic.config;

import javax.servlet.ServletContext;
import org.springframework.context.annotation.Profile;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.stereotype.Component;

/**
 * Created by joris on 27/03/17.
 * RedisInitializer to ensure proper session configuration is loaded
 */
@Component
@Profile({"redis-cloud", "redis-local"})
public class RedisInitializer extends AbstractHttpSessionApplicationInitializer {
  public RedisInitializer() {
    super(HttpRedisSessionConfig.class);
  }

  @Override
  public void onStartup(ServletContext servletContext) {
    // empty for now
  }
}

// That's All Folks !!
