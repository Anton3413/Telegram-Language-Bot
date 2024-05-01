#Hello!
#This is my first Dockerfile
ARG alpine_version=latest
FROM alpine:${alpine_version}

WORKDIR /

RUN touch test.txt && echo "Hello world" >> test.txt

RUN apk add openjdk17

RUN mkdir tomcat && cd tomcat && wget https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.23/bin/apache-tomcat-10.1.23.tar.gz \
    && tar -xvzf apache-tomcat-10.1.23.tar.gz \
    && rm apache-tomcat-10.1.23.tar.gz

COPY db test_dir

EXPOSE 8080
ENTRYPOINT ["/tomcat/apache-tomcat-10.1.23/bin/catalina.sh"]
CMD ["run"]
