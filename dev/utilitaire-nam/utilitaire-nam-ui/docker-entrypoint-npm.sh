#!/bin/bash

sed -i -e 's#UTILITAIRE_NAM_URL#'"$UTILITAIRE_NAM_URL"'#g' /usr/src/app/src/environments/environment.prod.ts

cp /usr/src/app/src/environments/environment.prod.ts /usr/src/app/src/environments/environment.ts

ng build --prod

#zipper le contenu du répertoire dist
cd /usr/src/app/dist/
tar -zcvf /dist/untilitaire-nam-ui.tar.gz guichet-ui

exit $?
