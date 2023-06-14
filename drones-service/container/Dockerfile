FROM amazoncorretto:17.0.0-alpine
MAINTAINER tst
COPY target/drones-container.jar drones-container.jar
COPY target/classes/application.yaml application.yml
ENTRYPOINT ["java","-jar","drones-container.jar"]