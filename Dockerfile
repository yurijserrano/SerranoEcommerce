FROM openjdk:8-jdk-alpine
LABEL maintainer="yurijserrano@gmail.com"
RUN addgroup -g 1001 -S serrano && adduser -u 1001 -S serrano -G serrano
RUN chown -R serrano:serrano /home
RUN cd /home && mkdir yuri
RUN chown -R serrano:serrano /home/yuri
USER serrano
VOLUME /tmp
ARG JAR_FILE=target/serrano-ecommerce.jar
COPY ${JAR_FILE} /home/yuri/serrano-ecommerce.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /home/yuri/serrano-ecommerce.jar ${0} ${@}"]