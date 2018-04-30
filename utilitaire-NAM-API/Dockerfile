FROM nexus3.inspq.qc.ca:5000/inspq/oraclejdk:latest
#FROM nexus3.inspq.qc.ca:5000/tomcat:8.5.20

ENV TZ=America/Toronto
ENV U_NAME_API_HOME=/opt/utilitaire-NAM-API
#ARG CATALINA_OPTS="-server -XX:MaxPermSize=256m -Xmx1024m"

ARG NEXUS3_URL=https://nexus3.inspq.qc.ca:8443
RUN wget --no-check-certificate $NEXUS3_URL/repository/utilitaires-infrastructure/ojdbc8.jar -O $CATALINA_HOME/lib/ojdbc8.jar

ARG APP_VERSION=0.1.1-SNAPSHOT

RUN mkdir $U_NAME_API_HOME
ADD target/utilitaire-nam-api-${APP_VERSION}.jar $U_NAME_API_HOME/utilitaire-nam-api.jar
ADD docker-entrypoint.sh $U_NAME_API_HOME/docker-entrypoint.sh
RUN chmod a+x $U_NAME_API_HOME/docker-entrypoint.sh

EXPOSE 8080 8009 28081

ENTRYPOINT "$U_NAME_API_HOME/docker-entrypoint.sh"
