FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
EXPOSE 8443:8443
# mark it with a label, so we can remove dangling images
LABEL cicd="fishingweb"
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/fishingbackend.jar
COPY /fishing.jks /app
COPY /fishing.jks .

ENTRYPOINT ["java","-jar","/app/fishingbackend.jar"]

#FROM ubuntu:latest
#RUN apt-get -y update
#RUN groupadd -r user && useradd -r -g user user
#USER user

# syntax=docker/dockerfile:1
#FROM ubuntu:18.04
#RUN useradd -ms /bin/bash docker
#USER root
#WORKDIR /home/docker
#RUN apt-get update; exit 0 && apt-get install net-tools && apt-get install nano && apt-get install mc
