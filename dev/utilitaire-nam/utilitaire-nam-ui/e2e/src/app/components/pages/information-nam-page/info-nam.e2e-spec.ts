import { InfoNamPage } from './info-nam.po';

describe('Page Obtenir Information du NAM', () => {
  let pageInformationNAM: InfoNamPage;

  beforeEach(() => {
    pageInformationNAM = new InfoNamPage();
  });

  it('devrait activer le button "obtenir information" si on a saisie le bon texte dans le champs NAM', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('GAUA97612726');
    expect(pageInformationNAM.getBoutonObtenirInfoNAM().isEnabled()).toBeTruthy();
  });

  it('devrait désactiver le button  "obtenir information" si le champs NAM est vide', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('');
    expect(pageInformationNAM.getBoutonObtenirInfoNAM().isEnabled()).toBeFalsy();
  });

  it('devrait afficher une alerte de type succés avec les informations sur le NAm si on a sasie un bon NAM', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('GAUA97612726');
    pageInformationNAM.getBoutonObtenirInfoNAM().click();

    expect(pageInformationNAM.getAlerteValidationSuccess().isDisplayed()).toBeTruthy();
  });

  it('devrait afficher une alerte de type erreur si on a sasie un mauvais NAM', () => {
    pageInformationNAM.aller();
    pageInformationNAM.getZoneTexteNAM().sendKeys('AAAAAaAa');
    pageInformationNAM.getBoutonObtenirInfoNAM().click();

    expect(pageInformationNAM.getAlerteValidationErreur().isDisplayed()).toBeTruthy();
  });
});
