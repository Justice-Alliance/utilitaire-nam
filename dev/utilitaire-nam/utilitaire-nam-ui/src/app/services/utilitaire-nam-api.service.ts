import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { InformationDTO } from '../model/information-dto';

@Injectable({
  providedIn: 'root'
})
export class UtilitaireNamApiService {

  /** Url de l'api REST NAM (.../nam/)*/
  urlApiNAM: string = environment.urlUtilitaireNAM + "/nam";;

  ctxValider = '/valider';
  ctxGenerer = '/generer';
  ctxInformation = '/info';

  constructor(private http: HttpClient) { }

  validerNAMGET(nam: string, provinceCode: string): Observable<any> {
    return this.http.get<boolean>(this.urlApiNAM + this.ctxValider + '?nam=' + nam + '&province=' + provinceCode).pipe(
      catchError(err => {
        return this.gestionErreur(err);
      }
      ));
  }


  genererNAMGET(nom: string, prenom: string, ddn: string, sexe: string): Observable<any> {
    return this.http.get<string[]>(this.urlApiNAM + this.ctxGenerer + '?nom=' + nom + '&prenom=' + prenom + '&datenaissance=' + ddn + '&sexe=' + sexe).pipe(
      catchError(err => {
        return this.gestionErreur(err);
      }
      ));
  }


  obtenirInformationNAMGET(nam:string): Observable<any> {
    return this.http.get<InformationDTO>(this.urlApiNAM + this.ctxInformation + '?nam='+ nam).pipe(
      catchError(err =>{
        return this.gestionErreur(err);
      }));
  }

  gestionErreur(error: HttpErrorResponse) {
    if (error !== undefined) {
      if (error.status === 400) {
        return throwError(" Erreur Utilitaire NAM: incapable de traiter la requete.");
      } else if (error.status === 500) {
        return throwError(" Erreur Utilitaire NAM: une erreur inconnue s'est produite.");
      }
    }
  }
}
