import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActionsEnum } from 'src/app/enums/actions-enum';
import { ProvincesEnum } from 'src/app/enums/provinces-enum';
import { InputOption, InputOptionCollection } from 'src/app/lib/input-option/input-option';
import { UtilitaireNamApiService } from 'src/app/services/utilitaire-nam-api.service';

@Component({
  selector: 'app-valider-nam-page',
  templateUrl: './valider-nam-page.component.html',
  styleUrls: ['./valider-nam-page.component.css']
})
export class ValiderNamPageComponent implements OnInit, OnDestroy {

  readonly TEXTE_ERREUR_NAM: string = 'Le Numéro d\'assurance maladie est invalide !';
  readonly TEXTE_VALIDE_NAM: string = 'Le Numéro d\'assurance maladie est valide';
  // resultat de la validation du NAM 
  public resultatValider: boolean;
  public erreurMessage:string;
  // action a setter lors de la validation 
  public action: string = null;
  private abonnement: Subscription;
  //valeur NAM
  public nam: string;
  // code de la province selectionne
  public codeProvinceSelect: string;

  public erreurTraitement: string;

  // Objet pour la liste de valeurs   
  public inputOptionsProvince: InputOptionCollection = {
    name: 'provinces',
    options: []
  };

  // liste de provinces 
  listeProvinces: InputOption[] = [];

  constructor(private apiNamService: UtilitaireNamApiService) { }

  ngOnInit(): void {
    this.creerListeDesProviences();
    this.peuplerListeDeroulante();
  }

  ngOnDestroy(): void {
    if (this.abonnement) {
      this.abonnement.unsubscribe();
    }
    this.action = null;
    this.erreurMessage = null;
  }

  actionValider(): void {
    this.action = ActionsEnum.VALID;
    this.erreurMessage = null;
    if (this.codeProvinceSelect === '' || this.codeProvinceSelect === undefined) {
      this.codeProvinceSelect = "QC";
    }
    this.abonnement = this.apiNamService.validerNAMGET(this.nam, this.codeProvinceSelect).subscribe(
      data => {
        this.resultatValider = data;
      },
      err => {
        this.resultatValider = false;
        this.erreurMessage = err;
        console.error(err);
      }
    )
  }

  creerListeDesProviences(): void {
    this.listeProvinces = Object.keys(ProvincesEnum).filter(k => typeof ProvincesEnum[k] === 'string')
      .map(value => ({ value, label: ProvincesEnum[value] }));
  }

  peuplerListeDeroulante(): void {
    this.inputOptionsProvince.options = this.listeProvinces;
  }

}
