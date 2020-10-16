import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { AppAuthGuard } from './app.authguard';
import { AppComponent } from './app.component';
import { GenererNamPageComponent, InformationNamPageComponent, ValiderNamPageComponent } from './components/pages';
import { DateFormatPipe } from './lib/data-format-pipe';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  let router  = {
    navigate: jasmine.createSpy('navigate')
  };
  
  let appAuthGuard = {
    canActivate : jasmine.createSpy('canActivate').and.returnValue(true)
  }
  

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        //RouterTestingModule.withRoutes(routes),
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        KeycloakAngularModule
      ],
      declarations: [
        AppComponent,
        ValiderNamPageComponent,
        GenererNamPageComponent,
        InformationNamPageComponent
      ],
      providers: [
        DateFormatPipe,
        {
          provide :AppAuthGuard,
          useValue : appAuthGuard
        }
        ,
        {
          provide: KeycloakService,
          useValue: jasmine.createSpyObj('KeycloakService', ['isLoggedIn','login','getUserRoles'])
        },
        { provide: Router, useValue: router }
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }
  );

  it('devrait être créé', () => {
    expect(component).toBeTruthy();
  });

  it('devrait avoir un titre Utilitaire NAM Application', () => {
    expect(component.NomApplication).toEqual('Utilitaire NAM Application');
  });

  it('devrait rediriger vers /valider quand on navigue vers ""', fakeAsync(() => {
    router.navigate(['/valider']);
    tick();
    expect(appAuthGuard.canActivate(<any>{}, <any>{})).toBe(true);
    expect(router.navigate).toHaveBeenCalledWith(['/valider']);
  }));

  it('devrait rediriger vers /valider quand on navigue vers "**"', fakeAsync(() => {
    router.navigate(['/valider']);
    tick();
    expect(appAuthGuard.canActivate(<any>{}, <any>{})).toBe(true);
    expect(router.navigate).toHaveBeenCalledWith(['/valider']);
  }));

  it('devrait rediriger vers /generer quand on navigue vers "generer"', fakeAsync(() => {
    router.navigate(['/generer']);
    tick();
    expect(appAuthGuard.canActivate(<any>{}, <any>{})).toBe(true);
    expect(router.navigate).toHaveBeenCalledWith(['/generer']);
  }));

  it('devrait rediriger vers /information quand on navigue vers "information"', fakeAsync(() => {
    router.navigate(['/information']);
    tick();
    expect(appAuthGuard.canActivate(<any>{}, <any>{})).toBe(true);
    expect(router.navigate).toHaveBeenCalledWith(['/information']);
  }));
});
