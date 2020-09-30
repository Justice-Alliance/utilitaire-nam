import { Location } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbDatepicker, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { routes } from './app-routing.module';
import { AppComponent } from './app.component';
import { GenererNamPageComponent, InformationNamPageComponent, ValiderNamPageComponent } from './components/pages';
import { DateFormatPipe } from './lib/data-format-pipe';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  let location: Location;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule
      ],
      declarations: [
        AppComponent,
        ValiderNamPageComponent,
        GenererNamPageComponent,
        InformationNamPageComponent
      ],
      providers: [
        DateFormatPipe
      ],
    }).compileComponents();

    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    router.initialNavigation();
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

  it('devrait avoir un titre \'utilitaire-nam-ui\'', () => {
    expect(component.title).toEqual('utilitaire-nam-ui');
  });

  it('devrait rediriger vers /valider quand on navigue vers ""', fakeAsync(() => {
    router.navigate(['']);
    tick();
    expect(location.path()).toBe('/valider');
  }));

  it('devrait rediriger vers /valider quand on navigue vers "**"', fakeAsync(() => {
    router.navigate(['anywhere']);
    tick();
    expect(location.path()).toBe('/valider');
  }));

  it('devrait rediriger vers /generer quand on navigue vers "generer"', fakeAsync(() => {
    router.navigate(['generer']);
    tick();
    expect(location.path()).toBe('/generer');
  }));

  it('devrait rediriger vers /information quand on navigue vers "information"', fakeAsync(() => {
    router.navigate(['information']);
    tick();
    expect(location.path()).toBe('/information');
  }));
});
