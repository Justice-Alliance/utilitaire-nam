import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
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
  urlApiNAM: string = environment.urlUtilitaireNAM + "nam";

  ctxValider = '/valider';
  ctxGenerer = '/generer';
  ctxInformation = '/info';

  constructor(private http: HttpClient) { }

  validerNAMGET(nam: string, provinceCode: string): Observable<any> {
    return this.http.get<boolean>(this.urlApiNAM + this.ctxValider + '?nam=' + nam + '&province=' + provinceCode, { headers: this.obtenirEnTete() }).pipe(
      catchError(err => {
        return this.gestionErreur(err);
      }
      ));
  }


  genererNAMGET(nom: string, prenom: string, ddn: string, sexe: string): Observable<any> {
    return this.http.get<string[]>(this.urlApiNAM + this.ctxGenerer + '?nom=' + nom + '&prenom=' + prenom + '&datenaissance=' + ddn + '&sexe=' + sexe, { headers: this.obtenirEnTete() }).pipe(
      catchError(err => {
        return this.gestionErreur(err);
      }
      ));
  }


  obtenirInformationNAMGET(nam:string): Observable<any> {
    return this.http.get<InformationDTO>(this.urlApiNAM + this.ctxInformation + '?nam='+ nam , { headers: this.obtenirEnTete() }).pipe(
      catchError(err =>{
        return this.gestionErreur(err);
      }));
  }

  gestionErreur(error: HttpErrorResponse) {
    if (error !== undefined) {
       if (error.status === 0) {
        return throwError(' Erreur Utilitaire NAM: Le serveur est présentement non disponible, veuillez réessayer plus tard.');
      } else if (error.status === 400) {
        return throwError(" Erreur Utilitaire NAM: Le serveur est incapable de traiter la demande (400).");
      } else if (error.status === 500) {
        return throwError(" Erreur Utilitaire NAM: une erreur inconnue s'est produite (500).");
      } else if (error.status === 401) {
        return throwError(" Erreur Utilitaire NAM: Accés non autorisé (401).");
      } else if (error.status === 403) {
        return throwError(" Erreur Utilitaire NAM: Vous ne disposez pas des droits pour accéder à la ressource (403).");
      }

    }
  }

  private obtenirEnTete(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': environment.angularProjectUrl
    });
  }
}
