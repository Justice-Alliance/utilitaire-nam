import { browser, by, element } from 'protractor';

export class ValiderNamPage {

  aller() {
    return browser.get('/valider');
  }

  getNAMForm() {
    return element(by.name('fieldNam'));
  }

  getBoutonValider() {
    return element(by.name('validerNamBtn'));
  }

  getAlerteValidationSuccess() {
    return element(by.id('alerteSuccess'));
  }

  getAlerteValidationErreur() {
    return element(by.id('alerteErreur'));
  }

}
