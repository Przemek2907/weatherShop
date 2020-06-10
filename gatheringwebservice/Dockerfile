FROM openjdk:11-oracle

EXPOSE 6083

ARG JAR_FILE=app.jar
ADD ./target/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "app.jar"]
