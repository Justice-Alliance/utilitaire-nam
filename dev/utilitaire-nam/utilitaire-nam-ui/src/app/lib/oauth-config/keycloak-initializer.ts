import { KeycloakService } from 'keycloak-angular';
import { environment } from 'src/environments/environment';


export function keycloakInitializer(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
            config: {
                url: environment.keycloakrooturl + '/auth',
                realm: environment.keycloakrealm,
                clientId: environment.keycloakclientid,
                credentials: {
                    secret: environment.keycloakclientsecret
                }
            }/*,//On le comment parce que il y a de composant qui n'ont pas besoin d"être securisé
            initOptions: {
                onLoad: 'login-required'
            }*/
        });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
  };
}


