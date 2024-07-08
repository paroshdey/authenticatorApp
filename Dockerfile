FROM openjdk:21
COPY target/authenticatorApp-0.0.1-SNAPSHOT.jar authenticatorApp-0.0.1.jar
ENTRYPOINT ["java","-jar","/authenticatorApp-0.0.1.jar"]