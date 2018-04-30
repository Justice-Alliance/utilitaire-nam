FROM nexus3.inspq.qc.ca:5000/inspq/keycloaktomcatvalve:latest
ENV TZ=America/Toronto

ARG CATALINA_OPTS="-server -XX:MaxPermSize=256m -Xmx1024m"

ARG APP_VERSION=0.1.1-SNAPSHOT
RUN rm -rf $CATALINA_HOME/webapps/*
ADD target/utilitaire-nam-service-${APP_VERSION}.war $CATALINA_HOME/webapps/utilitaire-nam-service.war

RUN mkdir $CATALINA_HOME/webapps/ROOT \
    && cd $CATALINA_HOME/webapps/ROOT \
    && jar -xvf ../utilitaire-nam-service.war \
	&& rm ../utilitaire-nam-service.war

#EXPOSE 8080 8009

#CMD ["catalina.sh", "run"]