// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  urlUtilitaireNAM: 'http://localhost:8080/', //"https://unam-dev.santepublique.rtss.qc.ca/",
  angularProjectUrl: 'http://localhost:4200/',
  keycloakrooturl :'https://gia-dev.santepublique.rtss.qc.ca',
  keycloaktokenurl: '/auth/realms/{REALM}/protocol/openid-connect/token',
  keycloakauthurl:'/auth/realms/{REALM}/protocol/openid-connect/auth',
  keycloakrealm: 'SPSantePublique', 
  keycloakclientid: 'sx5duuidev',
  keycloakclientsecret: '62d7c7b3-c4be-4ad1-8e7a-d2dde0f03558', 
  keycloakrole:''
};


/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
