FROM bellsoft/liberica-openjdk-alpine:18.0.2.1-1
WORKDIR /app
COPY target/factory-0.0.1-SNAPSHOT.jar /app/cw-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "cw-0.0.1-SNAPSHOT.jar"]