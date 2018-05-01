ARG NEXUS3_URL=https://nexus3.inspq.qc.ca:8443
RUN wget --no-check-certificate $NEXUS3_URL/repository/utilitaires-infrastructure/ojdbc8.jar -O $CATALINA_HOME/lib/ojdbc8.jar

RUN mkdir $U_NAME_HOME
ADD utilitaire-NAM-Service/target/utilitaire-nam-service-${APP_VERSION}.jar $U_NAME_HOME/utilitaire-nam-service.jar
ADD utilitaire-NAM-Service/docker-entrypoint.sh $U_NAME_HOME/docker-entrypoint.sh
RUN chmod a+x $U_NAME_HOME/docker-entrypoint.sh

EXPOSE 8080 8009 28081

ENTRYPOINT "$U_NAME_API_HOME/docker-entrypoint.sh"