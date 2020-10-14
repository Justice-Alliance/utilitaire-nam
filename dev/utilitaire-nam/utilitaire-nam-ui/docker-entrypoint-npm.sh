#!/bin/bash

sed -i -e 's#UTILITAIRE_NAM_URL#'"$UTILITAIRE_NAM_URL"'#g' /usr/src/app/src/environments/environment.prod.ts
sed -i -e 's#UTILITAIRE_UI_URL#'"$UTILITAIRE_UI_URL"'#g' /usr/src/app/src/environments/environment.prod.ts
sed -i -e 's#KEYCLOAK_URL#'"$KEYCLOAK_URL"'#g' /usr/src/app/src/environments/environment.prod.ts
sed -i -e 's#KEYCLOAK_REALM#'"$KEYCLOAK_REALM"'#g' /usr/src/app/src/environments/environment.prod.ts
sed -i -e 's#KEYCLOAK_REDIRECT_URI#'"$KEYCLOAK_REDIRECT_URI"'#g' /usr/src/app/src/environments/environment.prod.ts
sed -i -e 's#KEYCLOAK_CLIENT_ID#'"$KEYCLOAK_CLIENT_ID"'#g' /usr/src/app/src/environments/environment.prod.ts
sed -i -e 's#KEYCLOAK_CLIENT_SECRET#'"$KEYCLOAK_CLIENT_SECRET"'#g' /usr/src/app/src/environments/environment.prod.ts

cp /usr/src/app/src/environments/environment.prod.ts /usr/src/app/src/environments/environment.ts

ng build --prod

#zipper le contenu du répertoire dist
cd /usr/src/app/dist/${APP_NAME}
tar -zcvf /dist/${APP_NAME}.tar.gz .

exit $?
