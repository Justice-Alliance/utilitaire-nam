import { browser, by, element } from 'protractor';

export class InfoNamPage {

  aller() {
    return browser.get('/information');
  }

  getZoneTexteNAM() {
    return element(by.name('fieldNam'));
  }

  getBoutonObtenirInfoNAM() {
    return element(by.name('infoNamBtn'));
  }

  getAlerteValidationSuccess() {
    return element(by.id('alerteSuccess'));
  }

  getAlerteValidationErreur() {
    return element(by.id('alerteErreur'));
  }

}
