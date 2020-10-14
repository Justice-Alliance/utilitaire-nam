//import { AuthConfig } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';

export const authConfig: any = {
    issuer: environment.keycloakrooturl + environment.keycloakrealm.replace('{REALM}', environment.keycloakrealm),
    redirectUri: environment.angularProjectUrl + "generer",
    clientId: environment.keycloakclientid,
    scope: 'openid profile email',
    responseType: 'code',
    tokenEndpoint: environment.keycloakrooturl + environment.keycloaktokenurl.replace('{REALM}', environment.keycloakrealm),
    loginUrl: environment.keycloakrooturl + environment.keycloakauthurl.replace('{REALM}', environment.keycloakrealm),
    disablePKCE: true,
    // at_hash is not present in JWT token
    disableAtHashCheck: true,
    showDebugInformation: true
}