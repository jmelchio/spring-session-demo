package net.physiodelic;

import net.physiodelic.config.SpringApplicationContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).
        initializers(new SpringApplicationContextInitializer())
        .application()
        .run(args);
  }
}
