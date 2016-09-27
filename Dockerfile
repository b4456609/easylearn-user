FROM openjdk:8-alpine

COPY ./build/libs/user-0.0.1-SNAPSHOT.jar /opt/app/
COPY ./run.sh /opt/app/
WORKDIR /opt/app/

EXPOSE 8080

CMD ["java", "-jar", "user-0.0.1-SNAPSHOT.jar"]
