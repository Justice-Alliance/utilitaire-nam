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
        console.log('is not authentificated');
        const kcIdpHint = route.queryParamMap.get('kc_idp_hint');
        if (kcIdpHint !== undefined && kcIdpHint !== null) {
            console.log('kcidphint login');
          this.keycloakAngular.login({idpHint: kcIdpHint});
        } else {
            console.log('login');
          this.keycloakAngular.login();
        }
        //resolve(false);
        return;
      }
      console.log('is authentificated');
      resolve(true);
    });
  }
}
