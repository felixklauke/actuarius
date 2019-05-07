##################
### Base image ###
##################
FROM maven:latest as build

##################
### Maintainer ###
##################
MAINTAINER Felix KLauke <info@felix-klauke.de>

#########################
### Working directory ###
#########################
WORKDIR /workspace/app

##################
### Copy files ###
##################
COPY . .

#############
### Build ###
#############
RUN mvn clean package

###############
### Runtime ###
###############
FROM openjdk:11-jdk-alpine AS runtime

####################
### Dependencies ###
####################
ARG DEPENDENCY=/opt/actuarius

#########################
### Copy dependencies ###
#########################
COPY --from=build server/target/actuarius-server.jar .

#############
### Ports ###
#############
EXPOSE 53
EXPOSE 853

###############
### Run app ###
###############
ENTRYPOINT ["java", "-jar", "actuarius-server.jar" ]

