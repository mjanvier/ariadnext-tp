FROM openjdk:11-jre-slim

RUN adduser --home /home/ariadnext --disabled-password ariadnext
USER ariadnext

COPY target/ariadnext-tp.jar /app/service.jar

WORKDIR /app

CMD ["java", "-jar", "service.jar"]

EXPOSE 8080/tcp