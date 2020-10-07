FROM nexus3.inspq.qc.ca:5000/inspq/openjdk-11:latest-ubi8
ARG BASE_IMAGE=nexus3.inspq.qc.ca:5000/inspq/openjdk-11:latest-ubi8
ENV BASE_IMAGE=${BASE_IMAGE}

ENV TZ=America/Toronto

ARG APP_VERSION=1.0.0
ENV APP_VERSION=$APP_VERSION
ARG JAR_FILE=app.jar
ENV JAR_FILE=${JAR_FILE}
ARG APP_HOME=/opt/app
ENV APP_HOME=${APP_HOME}
ARG SERVER_PORT=8080
ENV SERVER_PORT=${SERVER_PORT}
ARG MANAGEMENT_SERVER_PORT=28081
ENV MANAGEMENT_SERVER_PORT=${MANAGEMENT_SERVER_PORT}

RUN mkdir ${APP_HOME}
ADD target/${JAR_FILE} ${APP_HOME}/${JAR_FILE}
ADD docker-entrypoint.sh ${APP_HOME}/docker-entrypoint.sh

RUN chmod a+x ${APP_HOME}/docker-entrypoint.sh
EXPOSE ${SERVER_PORT} ${MANAGEMENT_SERVER_PORT}

HEALTHCHECK --interval=5s --timeout=3s CMD curl --silent http://localhost:${MANAGEMENT_SERVER_PORT}/actuator/health | grep '{"status":"UP"}'

ENTRYPOINT ${APP_HOME}/docker-entrypoint.sh