# Attempt to create a docker image with the session application

FROM openjdk:latest
MAINTAINER joris.melchior@gmail.com

COPY spring-session-demo-1.0-RELEASE.jar /opt/java_app/

EXPOSE 8080
WORKDIR /opt/java_app/
ENTRYPOINT ["java", "-jar", "spring-session-demo-1.0-RELEASE.jar"]

# default host, override through command line when starting container
CMD ["--spring.redis.host=localhost"]

# That's All Folks !!
