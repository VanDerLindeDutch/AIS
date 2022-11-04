FROM tomcat:9.0
VOLUME /tmp
COPY target/ais.war /usr/local/tomcat/webapps/
EXPOSE 8080
WORKDIR /usr/local/tomcat/bin
CMD ["catalina.sh", "run"]