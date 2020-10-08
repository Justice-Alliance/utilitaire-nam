import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
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
  component: GenererNamPageComponent
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
  exports: [RouterModule]
})
export class AppRoutingModule { }