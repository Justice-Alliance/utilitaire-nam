import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { KeycloakService, KeycloakAuthGuard } from 'keycloak-angular';

@Injectable()
export class AppAuthGuard extends KeycloakAuthGuard {

  constructor(protected router: Router, protected keycloakAngular: KeycloakService) {
    super(router, keycloakAngular);
  }

  isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise((resolve, reject) => {
      if (!this.authenticated) {
        const kcIdpHint = route.queryParamMap.get('kc_idp_hint');
        console.log('-- authentification --');    
        if (kcIdpHint !== undefined && kcIdpHint !== null) {
          
          this.keycloakAngular.login({idpHint: kcIdpHint});
        } else {
          this.keycloakAngular.login();
        }
        return;
      }
      console.log('-- authentifié --');
      resolve(true);
    });
  }
}
