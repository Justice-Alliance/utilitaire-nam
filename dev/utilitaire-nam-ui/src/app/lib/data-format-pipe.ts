import { Pipe, PipeTransform, Injectable } from '@angular/core';
import { DatePipe } from '@angular/common';
import { DateFormat } from '../app.component';
import { AbstractControl } from '@angular/forms';

@Pipe({
  pure: true,
  name: 'date'
})
@Injectable()
export class DateFormatPipe extends DatePipe implements PipeTransform {

  constructor() {
    super('en-US');
  }


  public static obtenirDatePourAfficher(date: string): string {
    if (date) {
      return date.substr(0, 10);
    }
    return undefined;
  }

  /**
   * Obtenir la date du jour en format AAAA-MM-JJ
   */
  public static obtenirDateDuJour(): string {
    const aujourdhui = new Date();
    let jour = '';
    let mois = 0;
    let moisAffichage = '';

    if (aujourdhui.getDate() < 10) {
      jour = '0' + aujourdhui.getDate();
    } else {
      jour = aujourdhui.getDate().toString();
    }

    mois = aujourdhui.getMonth() + 1;
    if (mois < 10) {
      moisAffichage = '0' + mois;
    } else {
      moisAffichage = mois.toString();
    }

    let dateDuJour = '';
    dateDuJour = aujourdhui.getFullYear() + '-' + moisAffichage + '-' + jour;

    return dateDuJour;

  }

  transform(value: any, args?: any): any {
    return super.transform(value, 'yyyy-MM-dd');
  }

  dateToObject(value: any): any {
    const date: Date = this.parseDate(value);
    return {
      day: date.getDate(),
      month: date.getMonth() + 1,
      year: date.getFullYear()
    };
  }

  public parseDate(input: any): Date {
    const parts = input.match(/(\d+)/g);
    return new Date(parts[0], parts[1] - 1, parts[2]);
  }

  parseInt(s: string): number {
    return parseInt(s, 10);
  }

  getDateValide(date: any) {
    let dateFormat: DateFormat;
    dateFormat = date as DateFormat;
    if (dateFormat !== undefined && dateFormat != null) {
      const day: number = this.parseInt(dateFormat.day);
      const month = this.parseInt(dateFormat.month) - 1;
      const year = this.parseInt(dateFormat.year);
      const dateAuComplet = new Date(year, month, day);

      if (dateAuComplet.toString() === 'Invalid Date') {
        return undefined;
      } else {
        const datee = this.transform(dateAuComplet);
        return datee;
      }
    }
  }

  /**
   * Prend une date dans le format YYYYMMDD et la tranform dans le format YYYY-MM-DD
   * @param datePlain date dans plain format
   */
  mettreDateBonFormat(datePlain: string): string {
    let dateForme = '';
    for (const ch of datePlain) {
      if (!isNaN(this.parseInt(ch))) {
        dateForme += ch;
      }
    }
    if (dateForme && dateForme.length > 0) {
      if (dateForme.length === 8) {
        const annee = dateForme.substr(0, 4);
        const mois = dateForme.substr(4, 2);
        const jour = dateForme.substr(6, 2);
        return annee + '-' + mois + '-' + jour;
      }
    }
    return datePlain;
  }


  public dateFormateeEstValide(dateBonneValeur: string): boolean {
    try {
      if (this.parseDate(dateBonneValeur).toString() !== 'Invalid Date' && dateBonneValeur.length === 10) {
        const dateFinale = this.parseDate(dateBonneValeur);
        const jourSaisi = Number(dateBonneValeur.substr(8, 2));
        const moisSaisi = Number(dateBonneValeur.substr(5, 2));

        if (isNaN(jourSaisi)) {
          return false;
        } else {
          if (jourSaisi === dateFinale.getUTCDate() &&
            moisSaisi === (dateFinale.getUTCMonth() + 1)) {
            return true;
          } else {
            return false;
          }
        }
      }
      return false;

    } catch (error) {
      return false;
    }
  }


  public formaterDateFormControl(e: any, controle: AbstractControl) {
    if (e.target.value !== '') {
      const dateBonneValeur = this.mettreDateBonFormat(e.target.value);

      if (this.dateFormateeEstValide(dateBonneValeur)) {
        controle.setValue(this.dateToObject(dateBonneValeur));
      } else {
        e.target.value = dateBonneValeur;

        if (controle.valid) {
          controle.setErrors({"ngbDate":{"invalid":dateBonneValeur}});
        }
      }
      this.isDateInvalide(controle);

    } else {
      controle.setValue('');
    }
  }



  public isDateInvalide(controle: AbstractControl): boolean {
    if (controle !== undefined && controle.value === '') {
      return false;
    } else {
      if (controle === undefined || controle === null || controle.invalid) {
        return true;
      }
    }
    return false;
  }



}
