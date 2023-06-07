FROM openjdk:20
MAINTAINER patrykkucharz.com
COPY target/SantanderCRUD-0.0.1-SNAPSHOT.jar SantanderCRUD-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SantanderCRUD-0.0.1-SNAPSHOT.jar"]