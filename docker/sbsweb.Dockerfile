FROM openjdk:11.0-jdk

COPY target/sbsweb.jar sbsweb.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/sbsweb.jar"]