package net.physiodelic.config;

import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.RedisServiceInfo;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by joris on 17/04/17.
 * Overriding the initialization of the Spring Context.
 */
public class SpringApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final Logger logger = Logger.getLogger(SpringApplicationContextInitializer.class.toString());

  private static final Map<Class<? extends ServiceInfo>, String> serviceTypeToProfileName = new HashMap<>();
  private static final List<String> validLocalProfiles = Arrays.asList("redis", "hazelcast");

  static {
    serviceTypeToProfileName.put(RedisServiceInfo.class, "redis");
  }

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    Cloud cloud = getCloud();

    ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();

    String[] persistenceProfiles = getCloudProfile(cloud);
    if (persistenceProfiles == null) {
      persistenceProfiles = Optional.ofNullable(getActiveProfile(configurableEnvironment))
          .orElse(new String[0]);
    }


    Arrays.stream(persistenceProfiles).forEach(configurableEnvironment::addActiveProfile);
  }

  private String[] getCloudProfile(Cloud cloud) {
    if (cloud == null) {
      logger.log(Level.INFO, "No cloud environment found.");
      return null;
    }

    List<String> profiles = new ArrayList<>();

    List<ServiceInfo> serviceInfos = cloud.getServiceInfos();

    logger.log(Level.INFO, "Found serviceInfos: " + StringUtils.collectionToCommaDelimitedString(serviceInfos));

    serviceInfos.stream().filter(serviceInfo -> serviceTypeToProfileName.containsKey(serviceInfo.getClass()))
        .forEach(serviceInfo -> profiles.add(serviceTypeToProfileName.get(serviceInfo.getClass())));

    if (profiles.size() > 1) {
      throw new IllegalStateException(
          "Only one service of the following types may be bound to this application: " +
              serviceTypeToProfileName.values().toString() + ". " +
              "These services are bound to the application: [" +
              StringUtils.collectionToCommaDelimitedString(profiles) + "]");
    }

    if (profiles.size() > 0) {
      return createProfileNames(profiles.get(0), "cloud");
    }

    return null;
  }

  private Cloud getCloud() {
    try {
      CloudFactory cloudFactory = new CloudFactory();
      return cloudFactory.getCloud();
    } catch (CloudException ce) {
      return null;
    }
  }

  private String[] getActiveProfile(ConfigurableEnvironment appEnvironment) {
    List<String> serviceProfiles = new ArrayList<>();

    Arrays.stream(appEnvironment.getActiveProfiles()).filter(validLocalProfiles::contains)
        .forEach(serviceProfiles::add);

    if (serviceProfiles.size() > 1) {
      throw new IllegalStateException("Only one active Spring profile may be set among the following: " +
          validLocalProfiles.toString() + ". " +
          "These profiles are active: [" +
          StringUtils.collectionToCommaDelimitedString(serviceProfiles) + "]");
    }

    if (serviceProfiles.size() > 0) {
      return createProfileNames(serviceProfiles.get(0), "local");
    }

    return null;
  }

  private String[] createProfileNames(String baseName, String suffix) {
    String[] profileNames = {baseName, baseName + "-" + suffix};
    logger.info("Setting profile names: " + StringUtils.arrayToCommaDelimitedString(profileNames));
    return profileNames;
  }
}

// That's All Folks !!
