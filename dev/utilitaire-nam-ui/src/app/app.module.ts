import { LOCALE_ID, NgModule } from '@angular/core';
import  localeFr  from '@angular/common/locales/fr';
import { registerLocaleData } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GenererNamPageComponent } from './components/pages/generer-nam-page/generer-nam-page.component';
import { InformationNamPageComponent } from './components/pages/information-nam-page/information-nam-page.component';
import { ValiderNamPageComponent } from './components/pages/valider-nam-page/valider-nam-page.component';
import { TopMenuComponent } from './lib/top-menu/top-menu.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbDatepickerI18n, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CustomDatepickerI18n, I18n } from './lib/date-picker/datepicker-i18n';
import { NgxMaskModule } from 'ngx-mask'
import { DateFormatPipe } from './lib/data-format-pipe';


registerLocaleData(localeFr, 'fr');

@NgModule({
  declarations: [
    AppComponent,
    ValiderNamPageComponent,
    GenererNamPageComponent,
    InformationNamPageComponent,
    ValiderNamPageComponent,
    TopMenuComponent
    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    NgxMaskModule.forRoot(),
    ReactiveFormsModule
   ],
  providers: [
    DateFormatPipe,
    {provide: LOCALE_ID, useValue: 'fr' },
    I18n,
    {provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
