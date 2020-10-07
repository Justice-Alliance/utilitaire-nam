import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActionsEnum } from 'src/app/enums/actions-enum';
import { InformationDTO } from 'src/app/model/information-dto';
import { UtilitaireNamApiService } from 'src/app/services/utilitaire-nam-api.service';


@Component({
  selector: 'app-information-nam-page',
  templateUrl: './information-nam-page.component.html',
  styleUrls: ['./information-nam-page.component.css']
})
export class InformationNamPageComponent implements OnInit, OnDestroy {

  public nam: string;
  public action: string;
  public resultatInformation: InformationDTO;
  public erreurTraitement: any;
  private abonnement: Subscription;

  constructor(private apiNamService: UtilitaireNamApiService) { }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    if (this.abonnement) {
      this.abonnement.unsubscribe();
    }
    this.resultatInformation = null;
    this.erreurTraitement = null;
    this.action = null;
  }

  actionObtenirInformation(): void {
    this.erreurTraitement = null;
    this.resultatInformation = null;
    this.action = ActionsEnum.INFO;
    this.abonnement = this.apiNamService.obtenirInformationNAMGET(this.nam).subscribe(
      data => {
        this.resultatInformation = data;
      },
      err => {
        this.erreurTraitement = err;
        console.error(err);
      }
    )
  }
}
