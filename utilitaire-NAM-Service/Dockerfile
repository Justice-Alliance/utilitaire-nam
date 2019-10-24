FROM nexus3.inspq.qc.ca:5000/inspq/openjdk-11:latest
ENV TZ=America/Toronto

ARG APP_VERSION=0.1.1-SNAPSHOT
ENV APP_VERSION=$APP_VERSION

ENV UNAM_HOME=/opt/unam

RUN mkdir UNAM_HOME
ADD utilitaire-NAM-Service/target/utilitaire-nam-service-${APP_VERSION}.jar $UNAM_HOME/utilitaire-nam-service.jar
ADD utilitaire-NAM-Service/docker-entrypoint.sh $UNAM_HOME/docker-entrypoint.sh

RUN chmod a+x $UNAM_HOME/docker-entrypoint.sh
EXPOSE 8080 28081

ENTRYPOINT $UNAM_HOME/docker-entrypoint.sh