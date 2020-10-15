FROM nexus3.inspq.qc.ca:5000/inspq/buildandtestnodejs-13:latest
RUN mkdir -p /usr/src/app /dist

ARG APP_VERSION=0.0.0
ENV APP_VERSION=${APP_VERSION}
ARG APP_NAME=utilitaire-nam-ui
ENV APP_NAME=${APP_NAME}

# Definir les variables d'environnement
ENV UTILITAIRE_NAM_URL "http://localhost:8080"
# Create app directory

WORKDIR /usr/src/app
USER root
# Install app dependencies
# A wildcard is used to ensure both package.json AND package-lock.json are copied
# where available (npm@5+)
COPY ./ /usr/src/app/


# Bundle app source

COPY ./docker-entrypoint-npm.sh /usr/src/app/docker-entrypoint-npm.sh
RUN chmod a+x /usr/src/app/docker-entrypoint-npm.sh
RUN mkdir -p /usr/src/app/reports/karma /usr/src/app/dist /dist /usr/src/app/node_modules
RUN chown -R node:node /usr/src/app /dist

USER node

RUN npm install --no-optional

# If you are building your code for production
# RUN npm install --only=production

#Run a security audit
RUN npm audit fix

# Écrire le numéro de version de FA-IW dans le package.json
RUN npm version ${APP_VERSION} --allow-same-version

EXPOSE 4200
ENTRYPOINT "/usr/src/app/docker-entrypoint-npm.sh"
#CMD [ "npm run", "start-exposed" ]
