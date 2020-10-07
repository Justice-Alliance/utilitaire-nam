import { browser, by, element } from 'protractor';

export class GenererNamPage {

  aller() {
    return browser.get('/generer');
  }

  getNomForm() {
    return element(by.name('fieldNom'));
  }

  getPrenomForm() {
    return element(by.name('fieldPrenom'));
  }

  getDateNaissanceFormCalendrier() {
    return element(by.id('dateDeNaissance'));
  }

  getSexeTypeFormListe() {
    return element.all(by.tagName('option'));
  }

  getBoutonGenererNAM() {
    return element(by.name('genererNamBtn'));
  }

  getAlerteValidationSuccess() {
    return element(by.id('alerteSuccess'));
  }

  getAlerteValidationErreur() {
    return element(by.id('alerteErreur'));
  }
}
