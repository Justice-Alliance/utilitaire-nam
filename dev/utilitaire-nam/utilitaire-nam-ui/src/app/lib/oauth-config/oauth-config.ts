import { AuthConfig } from 'angular-oauth2-oidc';
import { environment } from 'src/environments/environment';

export const authConfig: AuthConfig = {
    issuer: environment.keycloakrooturlwithrealm,
    redirectUri: environment.keycloakredirecturi,
    clientId: environment.keycloakclientid,
    scope: 'openid profile email offline_access api',
    responseType: 'code',
    tokenEndpoint: environment.keycloaktokenurl,
    loginUrl: environment.keycloakauthurl,
    disablePKCE:true,
    // at_hash is not present in JWT token
    disableAtHashCheck: true,
    showDebugInformation: true
}