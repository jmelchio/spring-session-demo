package net.physiodelic.config;

import org.springframework.context.annotation.Profile;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

/**
 * Created by joris on 01/05/17.
 * RedisInitializer to ensure proper session configuration is loaded
 */
@Component
@Profile({ "hazelcast-cloud" })
public class HazelcastInitializer extends AbstractHttpSessionApplicationInitializer {
  public HazelcastInitializer() {
    super(HttpHazelcastSessionConfig.class);
  }

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // nothing to do here for now

  }
}
