import { Component, OnDestroy, OnInit } from '@angular/core';
import { SexesEnum } from 'src/app/enums/sexes-enum';
import { InputOption, InputOptionCollection } from 'src/app/lib/input-option/input-option';
import { DatePipe } from '@angular/common';
import { Subscription } from 'rxjs';
import { UtilitaireNamApiService } from 'src/app/services/utilitaire-nam-api.service';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { ActionsEnum } from 'src/app/enums/actions-enum';
import { DateFormatPipe } from 'src/app/lib/data-format-pipe';


@Component({
  selector: 'app-generer-nam-page',
  templateUrl: './generer-nam-page.component.html',
  styleUrls: ['./generer-nam-page.component.css'],
  providers: [DatePipe]
})
export class GenererNamPageComponent implements OnInit, OnDestroy {

  //public nom: string;
 // public prenom: string;
  //public ddn: string;
  public codeSexeSelect: string;
  public erreurTraitement: string;
  // liste de provinces 
  listeSexes: InputOption[] = [];
  // Objet pour la liste de valeurs   
  public inputOptionsSexe: InputOptionCollection = {
    name: 'sexes',
    options: []
  };

  public minDate = { year: 1850, month: 1, day: 1 };
  public action: string;

  private abonnement: Subscription;
  public listeNAMResultat: string[] = [];
  public groupeDeControle: FormGroup;

  constructor(public datePipe: DatePipe, private apiNamService: UtilitaireNamApiService, public dateFormatPipe: DateFormatPipe) { }

  ngOnInit(): void {
    this.groupeDeControle = new FormGroup({
      dateDeNaissance: new FormControl('', []),
      prenom: new FormControl('', []),
      nom: new FormControl('', []),
    });
    this.creerListeSexes();
    this.peuplerListeDeroulante();
  }

  ngOnDestroy(): void {
    this.action = null;
    this.listeNAMResultat = [];
    this.erreurTraitement = null;
    if (this.abonnement) {
      this.abonnement.unsubscribe();
    }
  }

  getDateNaissance(): string {
    if (this.groupeDeControle.controls.dateDeNaissance.value) {
      return this.dateFormatPipe.getDateValide(this.groupeDeControle.controls.dateDeNaissance.value);
    }
    return '';
  }

  getPrenom(): string {
    if (this.groupeDeControle.controls.prenom.value) {
      return this.groupeDeControle.controls.prenom.value;
    }
    return '';
  }

  getNom(): string {
    if (this.groupeDeControle.controls.nom.value) {
      return this.groupeDeControle.controls.nom.value;
    }
    return '';
  }

  actionGenerer(): void {
    this.listeNAMResultat = [];
    this.action = ActionsEnum.GEN;

    this.abonnement = this.apiNamService.genererNAMGET(this.getNom(),
      this.getPrenom(),
      this.getDateNaissance(),
      this.codeSexeSelect).subscribe(
        data => {
          this.listeNAMResultat = data;
        }, err => {
          this.erreurTraitement = err;
          console.error(err);
        }
      )
  }

  creerListeSexes(): void {
    this.listeSexes = Object.keys(SexesEnum).filter(k => typeof SexesEnum[k] === 'string')
      .map(value => ({ value, label: SexesEnum[value] }));
  }

  peuplerListeDeroulante(): void {
    this.inputOptionsSexe.options = this.listeSexes;
  }

  public formaterDate(e: any, controle: AbstractControl): void {
    this.dateFormatPipe.formaterDateFormControl(e, controle);
  }

  public formaterPrenom():void {
    this.groupeDeControle.controls.prenom.setValue(this.groupeDeControle.controls.prenom.value.trim());    
  }

  public formaterNom():void {
    this.groupeDeControle.controls.nom.setValue(this.groupeDeControle.controls.nom.value.trim());
  }

  public selectionnerDateDuJour(event: any) {
    this.groupeDeControle.controls.dateDeNaissance.setValue(this.dateFormatPipe.dateToObject(DateFormatPipe.obtenirDateDuJour()));
  }

  isDateDebutApplicationInvalide() {
    return this.dateFormatPipe.isDateInvalide(this.groupeDeControle.controls.dateDeNaissance);
  }

  isFormulaireValide(): Boolean {
    if (this.getNom() && this.getPrenom() && this.getDateNaissance() && this.codeSexeSelect){
      return true;
    } else {
      return false;
    }    
  }
}
