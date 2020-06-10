FROM openjdk:11-oracle

EXPOSE 6082

ARG JAR_FILE=dbwebservice.jar
ADD ./target/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "dbwebservice.jar"]
