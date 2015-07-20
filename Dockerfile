FROM tomcat:7-jre7

COPY target/fmi-bouchon*.war /usr/local/tomcat/webapps/
