FROM paasRegistry:5000/library/tomcat8.5.32:jdk1.7.0_71

MAINTAINER liaohw liaohw@163.com

#set workdir
WORKDIR /usr/local/apache-tomcat-8.5.32/
# set env
ENV APPLOGDIR=/usr/local/apache-tomcat-8.5.32/logs \
    APPCONFDIR=/usr/local/apache-tomcat-8.5.32/webapps/paas/WEB-INF/classes/application.properties

#copy package
COPY paas-web/build/opt/paaswar/paas /usr/local/apache-tomcat-8.5.32/webapps/paas
#set start command
CMD ./bin/catalina.sh jpda run
