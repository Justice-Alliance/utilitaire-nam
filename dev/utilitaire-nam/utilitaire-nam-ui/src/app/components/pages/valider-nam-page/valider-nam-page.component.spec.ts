import { HttpClient, HttpHandler } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { AppAuthGuard } from 'src/app/app.authguard';
import { UtilitaireNamApiService } from 'src/app/services/utilitaire-nam-api.service';
import { ValiderNamPageComponent } from './valider-nam-page.component';

describe('ValiderNamPageComponent', () => {
  let component: ValiderNamPageComponent;
  let fixture: ComponentFixture<ValiderNamPageComponent>;
  let httpSpy: jasmine.SpyObj<UtilitaireNamApiService>;

  let validerEl: DebugElement;
  let alerteErreurEl: DebugElement;
  let alerteSuccessEl: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ValiderNamPageComponent],
      imports: [
        AppRoutingModule,
        HttpClientTestingModule
      ],
      providers: [
        HttpClient,
        HttpHandler,
        {
          provide: UtilitaireNamApiService,
          useValue: jasmine.createSpyObj('UtilitaireNamApiService', ['validerNAMGET'])
        },
        AppAuthGuard
      ],
    })
      .compileComponents();

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ValiderNamPageComponent);
    component = fixture.componentInstance;
    httpSpy = TestBed.get(UtilitaireNamApiService);
    fixture.detectChanges();
    validerEl = fixture.debugElement.query(By.css('button'));
    alerteErreurEl = fixture.debugElement.query(By.css('#alerteErreur'));
    alerteSuccessEl = fixture.debugElement.query(By.css('#alerteSuccess'));
  });

  it('devrait être créé', () => {
    expect(component).toBeTruthy();
  });

  it('devrait activer le bouton valider si le NAM est non null', () => {
    component.nam = 'abcDEFGH1234';
    fixture.detectChanges();
    expect(validerEl.nativeElement.disabled).toBeFalsy();
  });

  it('devrait desactiver le bouton valider si le NAM est null ou vide', () => {
    component.nam = '';
    fixture.detectChanges();
    expect(validerEl.nativeElement.disabled).toBeTruthy();
  });

  it('devrait afficher des elements dans la liste déroulante', () => {
    const select: HTMLSelectElement = fixture.debugElement.query(By.css('select')).nativeElement;
    expect(select.options.length).toBeGreaterThan(0);
  });


  it('devrait afficher aucune alerte si on n\'a pas encore valider', () => {
    component.nam = 'abcDEFGH1234';
    expect(alerteErreurEl).toBeNull();
    expect(alerteSuccessEl).toBeNull();
  });

  it('devrait afficher une alerte succés si le nam est valide', () => {
    component.nam = 'TREF86040313';

    httpSpy.validerNAMGET.and.returnValue(of(true));

    validerEl.triggerEventHandler('click', {});
    validerEl.nativeElement.click();
    fixture.detectChanges();

    expect(httpSpy.validerNAMGET).toHaveBeenCalled();
    expect(component.action).toBe('valider');
    expect(component.resultatValider).toBeTruthy();
  });

  it('devrait afficher une alerte erreur si le nam est invalide', () => {
    component.nam = 'AAABBBCCC123';
    httpSpy.validerNAMGET.and.returnValue(of(false));
 
    validerEl.triggerEventHandler('click', {});
    validerEl.nativeElement.click();
    fixture.detectChanges();

    expect(httpSpy.validerNAMGET).toHaveBeenCalled();
    expect(component.action).toBe('valider');
    expect(component.resultatValider).toBe(false);
  });
});
