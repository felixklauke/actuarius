FROM maven:3.5.0-jdk-9

EXPOSE 53
EXPOSE 853

ADD server/target/actuarius-server-1.0-SNAPSHOT.jar actuarius-server.jar

ENTRYPOINT ["java", "-jar", "actuarius-server.jar"]
