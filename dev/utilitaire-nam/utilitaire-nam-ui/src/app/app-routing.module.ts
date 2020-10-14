import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AppAuthGuard } from './app.authguard';
import { GenererNamPageComponent, InformationNamPageComponent, ValiderNamPageComponent } from './components/pages';

export const routes: Routes = [{
  path: '',
  pathMatch: 'full',
  redirectTo: 'valider'
},
{
  path: 'valider',
  component: ValiderNamPageComponent
},
{
  path: 'generer',
  component: GenererNamPageComponent,
  canActivate: [AppAuthGuard], // le composant est sécurisé par keycloak
  data: { roles: [environment.keycloakrole] }
},
{
  path: 'information',
  component: InformationNamPageComponent
}, 
{
  path: '**',
  redirectTo: 'valider'
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    AppAuthGuard
  ]
})
export class AppRoutingModule { }