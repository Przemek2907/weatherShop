FROM openjdk:11-oracle

EXPOSE 6081

ARG JAR_FILE=nbpwebservice.jar
ADD ./target/${JAR_FILE} ${JAR_FILE}

ENTRYPOINT ["java", "-jar", "nbpwebservice.jar"]
