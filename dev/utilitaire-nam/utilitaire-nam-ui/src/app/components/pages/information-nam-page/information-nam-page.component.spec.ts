import { HttpClient, HttpHandler } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DebugElement } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { DateFormatPipe } from 'src/app/lib/data-format-pipe';
import { InformationDTO } from 'src/app/model/information-dto';
import { UtilitaireNamApiService } from 'src/app/services/utilitaire-nam-api.service';
import { InformationNamPageComponent } from './information-nam-page.component';


describe('InformationNamPageComponent', () => {
  let component: InformationNamPageComponent;
  let fixture: ComponentFixture<InformationNamPageComponent>;
  let ObtenirInfoEl: DebugElement;
  let httpSpy: jasmine.SpyObj<UtilitaireNamApiService>;

  
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule
      ],
      declarations: [ InformationNamPageComponent ],
      providers:[
        DateFormatPipe,
        HttpClient,
        HttpHandler,
        {
          provide: UtilitaireNamApiService,
          useValue: jasmine.createSpyObj('UtilitaireNamApiService', ['obtenirInformationNAMGET'])
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InformationNamPageComponent);
    component = fixture.componentInstance;
    httpSpy = TestBed.get(UtilitaireNamApiService);
    fixture.detectChanges();
    ObtenirInfoEl = fixture.debugElement.query(By.css('button'));
  });

  it('devrait être créé', () => {
    expect(component).toBeTruthy();
  });

  it('devrait désactiver le bouton valider si le NAM est null ou vide', () => {
    component.nam = '';
    fixture.detectChanges();
    expect(ObtenirInfoEl.nativeElement.disabled).toBeTruthy();
  });

  it('devrait activer le bouton valider si le NAM est non null', () => {
    component.nam = 'GAUA97612726';
    fixture.detectChanges();
    expect(ObtenirInfoEl.nativeElement.disabled).toBeFalse();
  });

  it('devrait afficher une alerte succés si le NAM est valide', () => {
    component.nam = 'GAUA97612726';
    let resultatDTO : InformationDTO = {dateNaissance: '2020-10-10', sexe: 'FEMININ'};
    httpSpy.obtenirInformationNAMGET.and.returnValue(of(resultatDTO));
 
    ObtenirInfoEl.triggerEventHandler('click', {});
    ObtenirInfoEl.nativeElement.click();
    fixture.detectChanges();

    expect(httpSpy.obtenirInformationNAMGET).toHaveBeenCalled();
    expect(component.resultatInformation.dateNaissance).toContain(resultatDTO.dateNaissance);
    expect(component.resultatInformation.sexe).toContain(resultatDTO.sexe);
  });

  it('devrait afficher une alerte erreur si le NAM est Invalide', () => {
    component.nam = 'XXXXX12345';
    let resultatDTO : InformationDTO = {dateNaissance: null, sexe: null};
    httpSpy.obtenirInformationNAMGET.and.returnValue(of(resultatDTO));
 
    ObtenirInfoEl.triggerEventHandler('click', {});
    ObtenirInfoEl.nativeElement.click();
    fixture.detectChanges();

    expect(httpSpy.obtenirInformationNAMGET).toHaveBeenCalled();
    expect(component.resultatInformation.dateNaissance).toBeNull();
    expect(component.resultatInformation.sexe).toBeNull();
  });
});
