FROM openjdk:11-jdk
LABEL maintainer="email"
ARG JAR_FILE=build/libs/hacking-spring-boot-ch2-reactive-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-jar","/docker-springboot.jar"]