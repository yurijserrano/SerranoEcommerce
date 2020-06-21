FROM openjdk:8-jdk-alpine
LABEL maintainer="yurijserrano@gmail.com"
RUN addgroup -S serrano && adduser -S serrano -G serrano
USER serrano:serrano
VOLUME /tmp
ARG JAR_FILE=target/serrano-ecommerce.jar
COPY ${JAR_FILE} serrano-ecommerce.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /serrano-ecommerce.jar ${0} ${@}"]